// Copyright 2002-2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.core;

import com.google.opengse.util.DispatchQueue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A producer-consumer queue of HttpConnection objects that is fed by the
 * network thread via calls from {@code HttpConnection.readRequest()}.
 *
 * <p>A pool of worker threads is maintained. Free worker threads are
 * continually scheduled to run any HttpConnection objects in the queue. One
 * instance of the {@link DispatchQueue} is instantiated for each
 * {@code HttpServer} server.
 *
 * @author Spencer Kimball
 */
public class DispatchQueueImpl2 implements DispatchQueue {
  private static final Logger LOGGER =
    Logger.getLogger(DispatchQueueImpl2.class.getName());

  /** The maximum number of worker threads */
  private volatile int maxThreads_;

  /** The list of HttpConnection objects */
  private final LinkedList<Runnable> conns_;

  /** The list of all worker threads */
  private final LinkedList<WorkerThread> workers_;

  /** The set of currently working threads */
  private final Collection<Thread> activeWorkers_;

  /** The number of available workers */
  private int availableWorkers_ = 0;

  /**
   * Argument constructor.
   *
   * @param maxThreads is the number of worker threads to start
   */
  public DispatchQueueImpl2(int maxThreads) {
    this.conns_ = new LinkedList<Runnable>();
    this.workers_ = new LinkedList<WorkerThread>();
    this.activeWorkers_ =
      Collections.synchronizedCollection(new HashSet<Thread>());
    setMaxThreads(maxThreads);
  }

  /**
   * Returns dispatch queue size and state as a string
   *
   * @return string description of queue
   */
  @Override
  public String toString() {
    return maxThreads_ + " total workers, " + getAvailableWorkers() +
      " available; " + conns_.size() + " pending requests";
  }

  /**
   * Sets the maximum number of worker threads. The value supplied here must be
   * greater than the current worker thread count. That is, the maximum number
   * of threads can only be increased. This avoids possible problems with
   * removing worker threads which are already processing requests.
   *
   * @param maxThreads
   */
  private void setMaxThreads(int maxThreads) {
    maxThreads = Math.max(this.maxThreads_, maxThreads);
    synchronized (workers_) {
      if (maxThreads > this.maxThreads_) {
        for (int i = this.maxThreads_; i < maxThreads; i++) {
          String name = "GSE worker #" + i;
          WorkerThread wt = new WorkerThread(this, name);
          workers_.add(wt);
          wt.start();
        }
      }
      this.maxThreads_ = maxThreads;
    }
  }

  /**
   * Returns the number of available worker threads
   *
   * @return number of idle worker threads
   */
  private int getAvailableWorkers() {
    return availableWorkers_;
  }

  /**
   * Enqueues the provided connection.
   *
   * @param conn is the connection to enqueue
   */
  public synchronized void addRunnable(Runnable conn) {
    // The call to addLast must be synchronized since get() is called from the
    // many worked threads
    conns_.addLast(conn);
    notify(); // Any worker will do, so only need to wake one.
  }

  /**
   * Returns the number of available worker threads
   *
   * @return a collection of active working threads.  Shared with the
   *         <code>ThreadDiversityCalculator</code>
   */
  Collection<Thread> getActiveWorkers() {
    return activeWorkers_;
  }

  /**
   * Dequeues the first connection. If no connections are on the queue,
   * blocks. This method is called only by the worker threads.
   *
   * @return the first connection available.
   */
  private synchronized Runnable get() {
    availableWorkers_ += 1;
    while (true) {
      try {
        Runnable runnable = conns_.removeFirst();
        availableWorkers_ -= 1;
        return runnable;
      } catch (NoSuchElementException nsee) {
        try {
          wait();
        } catch (InterruptedException ie) { /* ignored */ }
      }
    }
  }

  /**
   * Blocks until the connection queue is empty or until the specified timeout
   * occurs.
   *
   * The queue is first filled up with <code>maxThreads_</code>
   * <code>null</code> values so that all worker threads will exit if no
   * threads take longer than the timeout to exit.
   *
   * @param timeout is the timeout value in milliseconds
   * @return <code>true</code> if connection queue empties
   *         or <code>false</code> if timeout occurred.
   */
  public boolean quit(long timeout) {
    // add nulls into the queue
    for (int i = 0; i < maxThreads_; i++) {
      addRunnable(null);
    }

    // attempt to join the worker threads, up to maximum timeout
    long end = System.currentTimeMillis() + timeout;
    synchronized (workers_) {
      for (WorkerThread wt : workers_) {

        // skip current thread
        if (wt == Thread.currentThread()) {
          continue;
        }

        // wait for thread to end up to the timeout
        long wait = end - System.currentTimeMillis();
        if (wait > 0) {
          try {
            wt.join(wait);
          } catch (InterruptedException e) { /* ignored */ }
        }
        if (wt.isAlive() == true) {
          wt.interrupt();
        }
      }
    }
    return (System.currentTimeMillis() < end);
  }

  /**
   * The worker thread class. Makes a blocking call to the {@link
   * DispatchQueueImpl2#get()} method. Runs the {@link Runnable#run()} method and
   * loops for another connection.
   */
  private class WorkerThread extends Thread {
    private final DispatchQueueImpl2 queue_;

    public WorkerThread(DispatchQueueImpl2 queue, String name) {
      super(name);
      this.queue_ = queue;
    }

    @Override
    public void run() {
      for (;;) {
        Runnable conn;
        conn = queue_.get();

        if (conn == null) {
          return;
        } else {
          try {
            activeWorkers_.add(this);
            conn.run();
          } catch (Exception e) {
            LOGGER.log(Level.WARNING, "caught an exception",  e);
          } catch (Error e) {
            LOGGER.log(Level.SEVERE, "caught an error", e);
          } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "caught a throwable", t);
          } finally {
            // clear the interrupted flag if it's set
            interrupted();
            activeWorkers_.remove(this);
          }
        }
      }
    }
  }
}
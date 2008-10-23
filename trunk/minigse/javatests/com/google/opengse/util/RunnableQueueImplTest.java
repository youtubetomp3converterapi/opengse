// Copyright 2008 Google Inc. All Rights Reserved.
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

package com.google.opengse.util;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Mike Jennings
 */
public class RunnableQueueImplTest extends TestCase {

  @Test
  public void testSizeIsZero() throws Exception {
    RunnableQueueImpl queue = new RunnableQueueImpl();
    assertEquals(0, queue.size());
  }

  @Test
  public void testWaitForEmptyReturnsImmediately() throws InterruptedException {
    RunnableQueueImpl queue = new RunnableQueueImpl();
    queue.waitUntilEmpty();
  }

  @Test
  public void testWaitForEmptyWaits() throws Exception {
    RunnableQueueImpl queue = new RunnableQueueImpl();
    TestTask task = new TestTask();
    queue.add(task);
    queue.add(new DoNothing());
    WorkerThread worker = new WorkerThread(queue, "test thread");
    synchronized (task) {
      worker.start();
      task.wait();
    }
    queue.waitUntilEmpty();
  }

  @Test
  public void testWaitForRemove() throws Exception {
    RunnableQueueImpl queue = new RunnableQueueImpl();
    Runnable expectedNext = new DoNothing();
    QueueAdderThread adder = new QueueAdderThread(queue, expectedNext);
    synchronized (adder) {
      adder.start();
      adder.wait(); // wait until we've started
    }
    // this should now wait until adder has added a task
    Runnable next = queue.remove();
    assertNotNull(next);
    assertSame(expectedNext, next);
  }

  @Test
  public void testInterrupt() throws Exception {
    RunnableQueueImpl queue = new RunnableQueueImpl();
    QueueInterrupterThread interrupter = new QueueInterrupterThread(queue);
    synchronized (interrupter) {
      interrupter.start();
      interrupter.wait(); // wait until we've started
    }
    // this should now wait until interrupter has interrupted
    try {
      queue.remove();
      fail("Expected an InterruptedException");
    } catch (InterruptedException e) { /* expected */ }
  }

  private class QueueInterrupterThread extends Thread {
    private final RunnableQueueImpl queue;

    public QueueInterrupterThread(RunnableQueueImpl queue) {
      this.queue = queue;
    }
    @Override
    public void run() {
      synchronized (this) {
        this.notifyAll();
      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) { /* ignored */ }
      queue.interrupt();
    }
  }


  private class QueueAdderThread extends Thread {
    private final RunnableQueueImpl queue;
    private final Runnable task;

    public QueueAdderThread(RunnableQueueImpl queue, Runnable task) {
      this.queue = queue;
      this.task = task;
    }

    @Override
    public void run() {
      synchronized (this) {
        this.notifyAll();
      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) { /* ignored */ }
      queue.add(task);
    }
  }

  private class TestTask implements Runnable {
    public void run() {
      synchronized (this) {
        this.notifyAll(); // notify that we've started
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) { /* ignored */ }
    }
  }

  private class DoNothing implements Runnable {
    public void run() { /* do nothing */ }
  }
}

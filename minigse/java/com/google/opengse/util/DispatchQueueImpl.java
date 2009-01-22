// Copyright 2007 Google Inc. All Rights Reserved.
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * An implementation of DispatchQueue
 *
 * @author Mike Jennings
 */
public final class DispatchQueueImpl implements DispatchQueue {

  private final int nWorkerThreads;
  private final RunnableQueue queue;

  public DispatchQueueImpl(int nWorkerThreads) throws InterruptedException {
    this.nWorkerThreads = nWorkerThreads;
    queue = new RunnableQueueImpl();
    int i;
    Thread[] workers = new Thread[nWorkerThreads];
    for (i = 0; i < nWorkerThreads; ++i) {
      workers[i] = new WorkerThread(queue, "worker " + i);
    }
    for (Thread worker : workers) {
      worker.start();
    }
  }

  public void addRunnable(Runnable conn) {
    queue.add(conn);
  }

  public boolean quit(long timeout) {
    CountDownLatch latch = new CountDownLatch(nWorkerThreads);
    for (int i = 0; i < nWorkerThreads; ++i) {
      queue.add(new CountdownTask(latch));
    }
    try {
      latch.await(timeout, TimeUnit.MILLISECONDS);
      return true;
    } catch (InterruptedException e) {
      return false;
    }
  }

  private static class CountdownTask implements Runnable {

    private final CountDownLatch latch;

    public CountdownTask(CountDownLatch latch) {
      this.latch = latch;
    }

    public void run() {
      latch.countDown();
    }
  }
}

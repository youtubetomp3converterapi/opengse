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

import java.util.LinkedList;

/**
 * A queue of Runnable objects.
 *
 * @author Mike Jennings
 */
public class RunnableQueueImpl implements RunnableQueue {
  private final LinkedList<Runnable> list = new LinkedList<Runnable>();
  private final Object emptySignal = new Object();
  private boolean listIsEmpty = true;
  private int nWaiting = 0;

  /**
   * Add a runnable to one end of the queue
   */
  public void add(Runnable r) {
    synchronized (list) {
      list.addFirst(r);
      list.notify();
      listIsEmpty = false;
    }
  }

  public void interrupt() {
    synchronized (list) {
      list.clear();
      list.notifyAll();
    }
  }

  /**
   * Remove a runnable from the queue.
   */
  public Runnable remove() throws InterruptedException {
    synchronized (list) {
      if (list.isEmpty()) {
        ++nWaiting;
        list.wait();
        --nWaiting;
        if (list.isEmpty()) {
          throw new InterruptedException();
        }
      }
      Runnable nextTask = list.removeLast();
      synchronized (emptySignal) {
        listIsEmpty = list.isEmpty();
        if (listIsEmpty) {
          emptySignal.notifyAll();
        }
      }
      return nextTask;
    }
  }

  public void waitUntilEmpty() throws InterruptedException {
    synchronized (emptySignal) {
      while (!listIsEmpty) {
        emptySignal.wait();
      }
    }
  }

  public int size() {
    synchronized (list) {
      return list.size();
    }
  }

  public int numberOfWaitingThreads() {
    return nWaiting;
  }
}
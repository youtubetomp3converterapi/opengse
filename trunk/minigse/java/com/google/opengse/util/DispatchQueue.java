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

/**
 * @author Mike Jennings
 */
public interface DispatchQueue {
  /**
   * Add a Runnable to this DispatchQueue
   *
   * @param r the Runnable to enqueue
   */
  void addRunnable(Runnable r);
  
  /**
   * Blocks until the queue is empty or until the specified timeout
   * occurs.
   *
   * The queue is first filled up with <code>maxThreads_</code>
   * <code>null</code> values so that all worker threads will exit if no
   * threads take longer than the timeout to exit.
   *
   * @param timeout is the timeout value in milliseconds
   * @return <code>true</code> if connection queue empties
   *         or <code>false</code> if timeout occured.
   */
  boolean quit(long timeout);
}


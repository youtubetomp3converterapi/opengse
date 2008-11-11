// Copyright 2002 Google Inc.
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


/**
 * Callback interface for timers.
 *
 * @see com.google.opengse.core.NetTimer
 * @author Peter Mattis
 */
public interface NetTimerCallback {
  /**
   * Called when the <code>NetTimer</code> this callback is associated
   * with is run.
   */
  public void handleTimerFired();
}
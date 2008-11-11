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

import java.util.Arrays;

/**
 * An <code>NetTimer</code> object encapsulates a single timeout. The
 * timers are stored inside a single <code>NetSelector</code> in a
 * <code>SortedSet</code>. Note that the only way to construct a
 * <code>NetTimer</code> is using <code>NetSelector.schedule</code>.
 *
 * @see com.google.opengse.core.NetSelector
 * @author Peter Mattis
 */
class NetTimer implements Comparable<NetTimer> {
  NetTimerCallback callback_;
  long timeout_;
  long id_;

  /**
   * Class constructor.
   *
   * @param callback
   *
   * @param timeout
   */
  public NetTimer(NetTimerCallback callback, long timeout, long id) {
    this.callback_ = callback;
    this.timeout_ = timeout;
    this.id_ = id;
  }

  public int compareTo(NetTimer other) {
    int c = Long.valueOf(timeout_).compareTo(other.timeout_);
    if (c == 0) {
      c = Long.valueOf(id_).compareTo(other.id_);
    }
    return c;
  }

//TODO(pmattis) Classes overriding equals() must override hashcode().
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NetTimer) {
      NetTimer that = (NetTimer) obj;
      return (timeout_ == that.timeout_) && (id_ == that.id_);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return hashCode(timeout_, id_);
  }

  private static int hashCode(Object... objects) {
    return Arrays.hashCode(objects);
  }

}
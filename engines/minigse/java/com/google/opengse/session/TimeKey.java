package com.google.opengse.session;

import java.util.Set;
import java.util.TreeSet;

/**
 * Author: Mike Jennings
 * Date: Feb 10, 2009
 * Time: 12:20:33 PM
 */
class TimeKey implements Comparable<TimeKey> {
  long time;
  String key;

  TimeKey(long time, String key) {
    this.time = time;
    this.key = key;
  }

  TimeKey(String key) {
    this(System.currentTimeMillis(), key);
  }

  void update() {
    time = System.currentTimeMillis();
  }

  /**
   * Default sort order makes oldest TimeKey object first
   * @param other
   * @return
   */
  public int compareTo(TimeKey other) {
    if (this.time == other.time) {
      return this.key.compareTo(other.key);
    }
    return (this.time < other.time) ? -1 : 1;
  }

  public int hashCode() {
    return key.hashCode();
  }

  public boolean equals(Object other) {
    return (this.compareTo((TimeKey)other) == 0);
  }

}

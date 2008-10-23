// Copyright 2003 Google Inc. All Rights Reserved.
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

package com.google.opengse;

/**
 * An interface used with the
 * {@link ThreadSafeCache#registerListener(CacheListener)} method.  Objects
 * which want to be notified of cache invalidate and evict events
 * implement this interface and register with the cache.
 *
 * @author Spencer Kimball
 */
public interface CacheListener<K, V> {
  /**
   * Notifies that an entry has been invalidated from the
   * cache. This method is not called when an entry is evicted
   * from the cache (see {@link #entryEvicted(Object,Object)}).
   *
   * @param key the cache key
   */
  void entryInvalidated(K key);

  /**
   * Notifies that an entry has been evicted from the cache.
   * The data field is not guaranteed to be non-null.
   *
   * @param key the cache key
   * @param data the associated data (possibly null)
   */
  void entryEvicted(K key, V data);
}
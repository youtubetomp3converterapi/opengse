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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple in-memory cache which synchronizes access to the cache
 * interface methods, making them thread-safe.  When the cache reaches
 * {@code maxSize} entries, the least-recently-used entry is evicted from
 * the cache.  The cache is implemented with {@code java.util.LinkedHashMap}.
 *
 * @author Spencer Kimball
 * @author Todd Larsen
 */
public class ThreadSafeCache<K, V> {

  private Map<K, V> hashMap_;
  // package scope for unit testing
  List<CacheListener<K, V>> cacheListeners_ = null;

  private static class CacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 4L;
    private int maxSize_;
    private ThreadSafeCache<K, V> cache_; // used to signal eviction

    /**
     * Create the linked hash map.  Always pass "accessOrder=true"
     * to the superclass constructor to get LRU behavior.
     */
    public CacheLinkedHashMap(ThreadSafeCache<K, V> cache, int maxSize,
                              int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor, true);
      cache_ = cache;
      maxSize_ = maxSize;
    }

    @Override protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
      if (this.size() > maxSize_) {
        if (cache_ != null) {
          cache_.signalEvict(eldest.getKey(), eldest.getValue());
        }
        return true;
      } else {
        return false;
      }
    }
  }

  /**
   * Create a cache with a specified maximum size.
   * @param maxSize maximum number of entries in the cache
   */
  public ThreadSafeCache(int maxSize) {
    hashMap_ = new CacheLinkedHashMap<K, V>(this, maxSize, 16, 0.75f);
  }

  /**
   * Create a cache with a default max size, set to {@code Integer.MAX_VALUE}.
   */
  public ThreadSafeCache() {
    this(Integer.MAX_VALUE);
  }

  /**
   * Inserts an object in the cache.
   */
  public synchronized void put(final K key, final V data) {
    hashMap_.put(key, data);
  }

  /**
   * Fetches an object from the cache.
   * @return the object if it was found, {@code null} if it was not
   */
  public synchronized V get(final K key) {
    return hashMap_.get(key);
  }

  /**
   * Notifies the cache that an object has changed (which will remove
   * the object from the cache).
   */
  public synchronized void invalidate(final K key) {
    if (hashMap_.containsKey(key)) {
      signalInvalidate(key);
      hashMap_.remove(key);
    }
  }

  /**
   * Clears the contents of the cache.
   */
  public synchronized void clear() {
    hashMap_.clear();
  }

  /**
   * Returns an iterator to the keys in the cache.
   */
  public synchronized Iterator<K> keys() {
    return hashMap_.keySet().iterator();
  }

  /**
   * Returns the size of the cache.
   */
  public int size() {
    return hashMap_.size();
  }

  /**
   * Registers a cache listener. The cache listener is any object
   * implementing the {@link CacheListener} interface. This object's
   * {@link CacheListener#entryInvalidated(Object)} and
   * {@link CacheListener#entryEvicted(Object, Object)} methods are called for
   * each {@link #invalidate(Object)} and eviction event in the cache.
   *
   * @param listener
   */
  public synchronized void registerListener(CacheListener<K, V> listener) {
    if (cacheListeners_ == null) {
      cacheListeners_ = new ArrayList<CacheListener<K, V>>();
    }
    cacheListeners_.add(listener);
  }

  /**
   * Signals an invalidated entry in the cache. This method in turn
   * signals cache listeners and is called when an object is invalidated
   * via the {@link #invalidate(Object)} cache method. The call to this function
   * is done before the invalidation so the value can be queried by the
   * listener.
   * <p>
   * This method is not called unless the invalidated key is present in
   * the cache.
   * <p>
   * Note: this method is not called by {@link #clear()}.
   *
   * @param key is the cache key being invalidated
   */
  protected void signalInvalidate(K key) {
    if (cacheListeners_ == null) { return; }
    for (CacheListener<K, V> cacheListener : cacheListeners_) {
      cacheListener.entryInvalidated(key);
    }
  }

  /**
   * Signals an evicted entry from the cache. This method in turn
   * signals cache listeners and is called when an object is evicted
   * from the cache. This method is not called on {@link #invalidate(Object)};
   * instead, {@link #signalInvalidate(Object)} is used.
   *
   * @param key is the cache key being evicted
   * @param data is the corresponding data
   */
  protected void signalEvict(K key, V data) {
    if (cacheListeners_ == null) { return; }
    for (CacheListener<K, V> cacheListener : cacheListeners_) {
      cacheListener.entryEvicted(key, data);
    }
  }
}
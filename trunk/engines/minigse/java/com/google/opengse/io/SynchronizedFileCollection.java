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

package com.google.opengse.io;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Synchronized wrapper for FileCollection.
 *
 * @author Mike Jennings
 */
public class SynchronizedFileCollection implements FileCollection {
  private final Object lock = new Object();
  private FileCollection delegate;

  public SynchronizedFileCollection(FileCollection delegate) {
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public String getCollectionName() {
    synchronized (lock) {
      return delegate.getCollectionName();
    }
  }

  public URL[] getUrls() throws MalformedURLException {
    synchronized(lock) {
      return delegate.getUrls();
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public void releaseResources() {
    synchronized (lock) {
      delegate.releaseResources();
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public boolean containsFile(String file) {
    synchronized (lock) {
      return delegate.containsFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public List<String> getFileNames() {
    synchronized (lock) {
      return new ArrayList<String>(delegate.getFileNames());
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public byte[] getBytes(String file) throws IOException {
    synchronized (lock) {
      return delegate.getBytes(file);
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public URL getResource(String file) throws IOException {
    synchronized (lock) {
      return delegate.getResource(file);
    }
  }

  /**
   * {@inheritDoc}
   * Synchronized.
   */
  public URL[] getResources(String file) throws IOException {
    synchronized (lock) {
      return delegate.getResources(file);
    }
  }
}



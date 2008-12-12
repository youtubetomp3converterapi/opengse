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

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;

/**
 * A collection of FileCollections.
 *
 * @author Mike Jennings
 */
public class FileCollectionCollection implements FileCollection {
  String classpath = "";
  ArrayList<FileCollection> entries = new ArrayList<FileCollection>();
  Map<String, FileCollection> collectionname_to_entry
      = new HashMap<String, FileCollection>();
  Map<String, Set<String> > file_to_collectionnames
      = new HashMap<String, Set<String> >();

  /**
   * {@inheritDoc}
   */
  public List<String> getFileNames() {
    List<String> list = new LinkedList<String>();
    for (FileCollection entry : entries) {
      list.addAll(entry.getFileNames());
    }

    return Collections.unmodifiableList(list);
  }

  public URL[] getUrls() throws MalformedURLException {
    List<URL> ulist = new LinkedList<URL>();
    for (FileCollection entry : entries) {
      File f = new File(entry.getCollectionName());
      ulist.add(f.toURL());
    }
    URL[] urls = ulist.toArray(new URL[ulist.size()]);
    return urls;
  }

  public void addJarOrDirectory(File f) {
    if (f.isFile()) {
      addEntry(new JarFileCollection(f));
    } else {
      addEntry(new DirFileCollection(f));
    }
  }

  public void addDirectoryOfJars(File dir) {
    File[] jars = dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return name.endsWith(".jar");
      }
    });
    if (jars != null) {
      for (File jar : jars) {
        addJarOrDirectory(jar);
      }
    }
  }

  public void parse(String cp) {
    this.classpath = cp;
    StringTokenizer st = new StringTokenizer(cp, File.pathSeparator);
    entries.ensureCapacity(st.countTokens());
    while (st.hasMoreTokens()) {
      String entry = st.nextToken();
      entry = entry.replace('/', File.separatorChar);
      File f = new File(entry);
      addJarOrDirectory(f);
    }
  }

  public void addEntry(FileCollection entry) {
    String entryName = entry.getCollectionName();
    // only add to our list of entries if the entryname hasn't been seen before
    if (!collectionname_to_entry.containsKey(entryName)) {
      entries.add(entry);
    }
    collectionname_to_entry.put(entryName, entry);
  }

  public String getAsString() {
    StringBuffer sb = null;
    for (FileCollection entry : entries) {
      if (sb == null) {
        sb = new StringBuffer();
        sb.append(entry.getCollectionName());
      } else {
        sb.append(File.pathSeparator).append(entry.getCollectionName());
      }
    }

    return sb.toString();
  }

  public int getSize() {
    return entries.size();
  }

  public FileCollection getEntryAt(int index) {
    return entries.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public byte[] getBytes(String file) throws IOException {
    Set<String> collections = file_to_collectionnames.get(file);
    if (collections == null || collections.isEmpty()) {
      throw new IOException("Cannot find file " + file);
    }
    String collectionname = collections.iterator().next();
    FileCollection entry = collectionname_to_entry.get(collectionname);
    return entry.getBytes(file);
  }

  /**
   * {@inheritDoc}
   */
  public URL getResource(String file) throws IOException {
    Set<String> collections = file_to_collectionnames.get(file);
    if (collections == null || collections.isEmpty()) {
      throw new IOException("Cannot find file " + file);
    }
    String collectionname = collections.iterator().next();
    FileCollection entry = collectionname_to_entry.get(collectionname);
    return entry.getResource(file);
  }

  /**
   * {@inheritDoc}
   */
  public URL[] getResources(String file) throws IOException {
    if (!containsFile(file)) {
      return new URL[0];
    }
    Set<String> collections = file_to_collectionnames.get(file);
    URL[] urls = new URL[collections.size()];
    Iterator<String> iter = collections.iterator();
    for (int i = 0; i < urls.length; ++i) {
      FileCollection entry = collectionname_to_entry.get(iter.next());
      urls[i] = entry.getResource(file);
    }
    return urls;
  }

  /**
   * {@inheritDoc}
   */
  public boolean containsFile(String file) {
    Set<String> collections = file_to_collectionnames.get(file);
    if (collections == null) {
      collections = findCollectionsContaining(file);
      file_to_collectionnames.put(file, collections);
    }
    return !collections.isEmpty();
  }

  public Set<String> getCollectionsContainingFile(String file) {
    if (!containsFile(file)) {
      return Collections.emptySet();
    } else {
      return file_to_collectionnames.get(file);
    }
  }

  public void removeEntry(int index) {
    FileCollection entry = getEntryAt(index);
    collectionname_to_entry.remove(entry.getCollectionName());
    entries.remove(index);
    file_to_collectionnames.clear(); // all bets are off
  }

  private Set<String> findCollectionsContaining(String file) {
    Set<String> collectionNames = new HashSet<String>();
    for (FileCollection entry : entries) {
      if (entry.containsFile(file)) {
        collectionNames.add(entry.getCollectionName());
      }
    }
    return collectionNames;
  }

  /**
   * {@inheritDoc} 
   */
  public String getCollectionName() {
    return classpath;
  }

  /**
   * {@inheritDoc}
   * NOP for this implementation.
   */
  public void releaseResources() {
  }
}



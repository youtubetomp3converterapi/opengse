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

import java.io.File;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * A FileCollection that uses a directory.
 *
 * @author Mike Jennings
 */
public class DirFileCollection implements FileCollection {
  File dir;

  public DirFileCollection(File dir) {
    this.dir = dir;
  }

  /**
   * {@inheritDoc}
   */
  public String getCollectionName() {
    try {
      return dir.getCanonicalPath();
    } catch (IOException ex) {
      return null;
    }
  }

  public URL[] getUrls() throws MalformedURLException {
    URL[] arr = new URL[1];
    File f = new File(getCollectionName());
    arr[0] = f.toURL();
    return arr;
  }


  /**
   * {@inheritDoc}
   */
  public void releaseResources() {
    /* NOP for this implementation. */
  }

  /**
   * {@inheritDoc}
   */
  public boolean containsFile(String filename) {
    // convert the filename's forward slashes to whatever
    // this OS uses.
    filename = filename.replace('/', File.separatorChar);
    File f = new File(dir, filename);
    return f.exists();
  }

  /**
   * {@inheritDoc}
   */
  public List<String> getFileNames() {
    File[] files = FileUtil.getFiles(dir);
    Arrays.sort(files);
    List<String> list = new ArrayList<String>(files.length);
    int len = getCollectionName().length();
    for (int i = 0; i < files.length; ++i) {
      String s = files[i].toString();
      s = s.substring(len + 1);
      s = s.replace(File.separatorChar, '/');
      list.add(s);
    }

    return Collections.unmodifiableList(list);
  }

  /**
   * {@inheritDoc}
   */
  public byte[] getBytes(String file) throws IOException {
    file = file.replace('/', File.separatorChar);
    File f = new File(dir, file);
    long length = f.length();
    byte[] buf = new byte[(int) length];
    DataInputStream dis = new DataInputStream(new FileInputStream(f));
    dis.readFully(buf);
    dis.close();
    return buf;
  }

  /**
   * {@inheritDoc}
   */
  public URL getResource(String file) throws IOException {
    file = file.replace('/', File.separatorChar);
    File f = new File(dir, file);
    return f.toURL();
  }

  /**
   * {@inheritDoc}
   */
  public URL[] getResources(String file) throws IOException {
    URL[] urls = new URL[1];
    urls[0] = getResource(file);
    return urls;
  }
}


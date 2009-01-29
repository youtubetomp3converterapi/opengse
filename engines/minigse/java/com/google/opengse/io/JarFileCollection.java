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

package com.google.opengse.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * A FileCollection that uses a jar file as the file container.
 *
 * @author Mike Jennings
 */
public class JarFileCollection
    implements FileCollection {
  File jarfile;
  JarFile jar;

  public JarFileCollection(File jarfile) {
    this.jarfile = jarfile;
    getFileNames();
  }

  void openJar() {
    if (jar == null) {
      try {
        jar = new JarFile(jarfile);
      } catch (IOException ex) {
        ex.printStackTrace(System.err);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public List<String> getFileNames() {
    openJar();
    Enumeration<JarEntry> en = jar.entries();
    List<String> list = new LinkedList<String>();
    while (en.hasMoreElements()) {
      ZipEntry entry = en.nextElement();
      if (!entry.isDirectory()) {
        list.add(entry.getName());
      }
    }
    return Collections.unmodifiableList(list);
  }

  public List<ZipEntry> getZipEntries() {
    openJar();
    Enumeration<JarEntry> en = jar.entries();
    List<ZipEntry> list = new LinkedList<ZipEntry>();
    while (en.hasMoreElements()) {
      ZipEntry entry = en.nextElement();
      if (!entry.isDirectory()) {
        list.add(entry);
      }
    }
    return Collections.unmodifiableList(list);
  }

  public String getCollectionName() {
    try {
      return jarfile.getCanonicalPath();
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
    try {
      if (jar != null) {
        jar.close();
      }
    } catch (IOException ex) {
    }
    jar = null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean containsFile(String file) {
    openJar();
    ZipEntry entry = jar.getEntry(file);
    if (entry == null) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public byte[] getBytes(String file) throws IOException {
    openJar();
    ZipEntry entry = jar.getEntry(file);
    if (entry == null) {
      throw new IOException("Cannot find " + file);
    }
    InputStream istr = jar.getInputStream(entry);
    byte[] buf = new byte[512];
    ByteArrayOutputStream baos
        = new ByteArrayOutputStream((int) entry.getSize());
    int n;
    while ((n = istr.read(buf)) != -1) {
      baos.write(buf, 0, n);
    }
    istr.close();
    return baos.toByteArray();
  }

  public void writeEntryTo(String file, OutputStream os) throws IOException {
    openJar();
    ZipEntry entry = jar.getEntry(file);
    if (entry == null) {
      throw new IOException("Cannot find " + file);
    }
    byte[] buf = new byte[512];
    int n;
    InputStream istr = null;
    try {
      istr = jar.getInputStream(entry);
      while ((n = istr.read(buf)) != -1) {
        os.write(buf, 0, n);
      }
      os.flush();
    } finally {
      if (istr != null) {
        istr.close();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public URL getResource(String file) throws IOException {
    String url;
    if (file.startsWith("/")) {
      url = "jar:" + jarfile.toURL() + "!" + file;
    } else {
      url = "jar:" + jarfile.toURL() + "!/" + file;
    }
    return new URL(url);
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

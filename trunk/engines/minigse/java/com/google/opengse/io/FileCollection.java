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

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

/**
 * A collection of files, such as a directory, jar file, tarball, etc.
 */
public interface FileCollection {

  /**
   * The name of this collection.
   */
  public String getCollectionName();


  /**
   * Return this collection as a representation of one or more URLs (usually 1).
   * 
   * @return
   * @throws MalformedURLException
   */
  public URL[] getUrls() throws MalformedURLException;

  /**
   * Release any resources held by this collection.
   */
  public void releaseResources();

  /**
   * Determine if a given file is contained by this collection.
   * The file specified uses forward slashes.
   * eg. C:/path/to/file.ext
   */
  public boolean containsFile(String file);

  /**
   * Get the names of all of the files in this collection.
   */
  public List<String> getFileNames();

  /**
   * Get the bytes of a given file.
   */
  public byte[] getBytes(String file) throws IOException;

  /**
   * Get the given file as a URL.
   */
  public URL getResource(String file) throws IOException;

  /**
   * Get the given file as an array of URLs (useful when a container
   *  may contain more than one instance of a file).
   */
  public URL[] getResources(String file) throws IOException;
}

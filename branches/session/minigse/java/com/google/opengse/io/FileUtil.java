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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A collection of File-based utility functions.
 *
 * @author Mike Jennings
 */
public final class FileUtil {
  private FileUtil() { /* Utility class: do not instantiate */ }

  /**
   * Recursively gets all the files in a directory
   *
   * @param dir the directory to traverse for files
   */
  public static File[] getFiles(File dir) {
    List<File> list = new LinkedList<File>();
    getAllFilesRecursively(dir, list);
    File[] arr = new File[list.size()];
    list.toArray(arr);
    return arr;
  }

  private static void getAllFilesRecursively(File dir, List<File> filelist) {
    File[] files = dir.listFiles();
    if (files == null) {
      return;
    }
    int i;
    for (i = 0; i < files.length; ++i) {
      if (files[i].isFile()) {
        filelist.add(files[i]);
      } else if (files[i].isDirectory()) {
        getAllFilesRecursively(files[i], filelist);
      }
    }
  }

  /**
   * Gets a logger for this class
   *
   * @return the logger
   */
  private static Logger getLogger() {
    return Logger.getLogger(FileUtil.class.getName());
  }

  public static void deleteContentsOf(File dir) throws IOException {
    File[] files = getFiles(dir);
    Logger log = getLogger();
    if (files.length == 0) {
      log.info("No files in " + dir);
    }
    log.info("Deleting " + files.length + " files in " + dir);
    for (File f : files) {
      log.fine("deleting '" + f + "'");
      f.delete();

    }
    log.info("Done.");
    files = getFiles(dir);
    if (files.length != 0) {
      throw new IOException(
          "Could not delete all files! " + files[0] + " is still there!");
    }
  }

  /**
   * Writes from an input stream to a file.
   * @param istr the stream to read from
   * @param outfile the file to write to
   * @return the number of bytes written
   * @throws IOException
   */
  public static int writeToFile(InputStream istr, File outfile)
      throws IOException {
    FileOutputStream out = new FileOutputStream(outfile);
    try {
      return copy(istr, out);
    } finally {
      out.close();
    }
  }

  public static int copy(InputStream is, OutputStream os) throws IOException {
    byte[] buf = new byte[512];
    int bytesRead = 0;
    int totalBytesRead = 0;
    while (bytesRead != -1) {
      bytesRead = is.read(buf, 0, buf.length);
      if (bytesRead > 0) {
        totalBytesRead += bytesRead;
        os.write(buf, 0, bytesRead);
      }
    }
    return totalBytesRead;
  }

}


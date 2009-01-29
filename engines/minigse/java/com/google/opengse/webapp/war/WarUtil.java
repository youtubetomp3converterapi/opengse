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

package com.google.opengse.webapp.war;

import com.google.opengse.io.FileUtil;
import com.google.opengse.io.JarFileCollection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

/**
 * A collection of utility methods related to WAR files.
 *
 * @author Mike Jennings
 */
public final class WarUtil {
  private WarUtil() { /* Utility class: do not instantiate */ }

  private static final String WEB_INF_CLASSES = "WEB-INF/classes";
  private static final Logger LOGGER
      = Logger.getLogger(WarUtil.class.getName());

  /**
   * TODO: we probably need more than this .. e.g. when a war is
   * overwritten (i.e. with a newer timestamp). Maybe a timestamp file should
   * be kept under the explosed directory. Note this is only to detect
   * an updated war during the app. startup - and we don't need support
   * hot-deployment (yet).
   *
   * Compares the contents of a war file with a directory. Useful to determine
   * if a war file needs to be re-expanded.
   *
   * @param warfile the war file to check
   * @param exploded a directory which may already contain the expanded contents
   * of the war file.
   * @return true if the directory does not have the same contents as the
   * war file.
   * @throws IOException if something went wrong
   */
  public static boolean warIsDifferentFromExploded(File warfile, File exploded)
      throws IOException {
    if (!exploded.exists()) {
      return true;
    }
    LOGGER.info("Checking differences between contents of " + warfile
        + " and " + exploded);
    JarFileCollection war = new JarFileCollection(warfile);
    String because = "contents of " + warfile + " is different from "
        + exploded + " because ";
    for (ZipEntry entry : war.getZipEntries()) {
      File destfile = getFileFromEntry(entry, exploded);
      if (!destfile.exists()) {
        LOGGER.warning(because + destfile + " does not exist");
        war.releaseResources();
        return true; // we are different!
      }
      if (entry.getSize() != destfile.length()) {
        LOGGER.warning(because + destfile + " is a different size");
        LOGGER.warning(warfile + " says it is " + entry.getSize() + " but "
            + destfile + " is " + destfile.length());
        war.releaseResources();
        return true; // we are different!
      }
    }
    war.releaseResources();
    return false; // no differences detected
  }


  /**
   * Given a zipfile entry and a base directory, compute the appropriate
   * destination file for the entry.
   *
   * @param entry the zip entry to compute a file from
   * @param exploded the target directory
   * @return the computed destination file
   */
  private static File getFileFromEntry(ZipEntry entry, File exploded) {
    String entryname = entry.getName();
    // change (back)slashes to whatever our local OS uses
    String filename = entryname.replace('/', File.separatorChar);
    filename = filename.replace('\\', File.separatorChar);
    return new File(exploded, filename);
  }

  /**
   * Explode (expand) the given war file into the given directory.
   * Note: The contents of the destination directory may be deleted.
   *
   * @param warfile the war file to expand.
   * @param exploded the directory to expand to
   * @throws IOException if something went wrong
   */
  public static void explodeWarFile(File warfile, File exploded)
      throws IOException {
    LOGGER.warning("Exploding '" + warfile + "' into " + exploded);
    if (exploded.exists()) {
      FileUtil.deleteContentsOf(exploded);
    }
    JarFileCollection war = new JarFileCollection(warfile);
    List<String> entries = new ArrayList<String>(war.getFileNames());
    Collections.sort(entries, new WarComparator());
    for (String entry : entries) {
      // change (back)slashes to whatever our local OS uses
      String entryname = entry.replace('/', File.separatorChar);
      entryname = entryname.replace('\\', File.separatorChar);
      File destfile = new File(exploded, entryname);
      File tmpDestFile = new File(exploded, entryname + ".tmp");
      File dir = destfile.getParentFile();
      if (!dir.exists()) {
        dir.mkdirs();
      }
      LOGGER.info("extracting " + entry + " to " + tmpDestFile);
      FileOutputStream fileOutputStream = null;
      try {
        fileOutputStream = new FileOutputStream(tmpDestFile);
        war.writeEntryTo(entry, fileOutputStream);
        fileOutputStream.close();
        fileOutputStream = null;
        if (!tmpDestFile.renameTo(destfile)) {
          throw new IOException(
              "Cannot rename " + tmpDestFile + " to " + destfile);
        }
        if (!destfile.exists()) {
          throw new IOException(destfile + " does not exist!");
        }
      } catch (IOException ioe) {
        LOGGER.log(Level.WARNING, "", ioe);
        tmpDestFile.delete();
      } finally {
        if (fileOutputStream != null) {
          fileOutputStream.close();
        }
      }
    }
    war.releaseResources();
  }

  static class WarComparator implements Comparator<String> {
    public int compare(String s1, String s2) {
      boolean c1 = s1.startsWith(WEB_INF_CLASSES);
      boolean c2 = s2.startsWith(WEB_INF_CLASSES);
      if (c1 == c2) {
        return s1.compareTo(s2);
      }
      // if the second string starts with WEB-INF/classes and the first string
      // does not then the first string precedes the second
      return c2 ? -1 : 1;
    }
  }

}

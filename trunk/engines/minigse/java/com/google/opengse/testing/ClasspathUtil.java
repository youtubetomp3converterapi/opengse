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

package com.google.opengse.testing;

import com.google.opengse.io.FileCollection;
import com.google.opengse.io.FileCollectionCollection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * A collection of classpath-related utility methods.
 *
 * @author Mike Jennings
 */
public final class ClasspathUtil {

  private static final String CLASS_SUFFIX = ".class";

  private ClasspathUtil() { /* Utility class: do not instantiate. */ }

  /**
   * Get the current classpath as a FileCollection.
   */
  public static FileCollection getClasspathAsFileCollection()
      throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    // this part is kinda ugly. ClassLoader has no "getAllClasses" or
    // "getAllClassesInThisPackage" method, so we do various hacky things to
    // get available packages and classes
    String classpath = invokeGetClasspathMethodIfItExists(classLoader);
    if (classpath != null) {
      return getClasspathAsFileCollectionFromClasspath(classpath);
    }
    if (classLoader instanceof URLClassLoader) {
      return toFileCollection((URLClassLoader) classLoader);
    } else {
      return getClasspathAsFileCollectionFromClasspath(
          System.getProperty("java.class.path"));
    }
  }


  private static String invokeGetClasspathMethodIfItExists(ClassLoader cl) {
    Class<?> clazz = cl.getClass();
    try {
      Method m = clazz.getMethod("getClasspath");
      Object returnValue = m.invoke(cl);
      if (returnValue instanceof String) {
        return (String) returnValue;
      } else {
        return null;
      }
    } catch (NoSuchMethodException e) {
      return null;
    } catch (InvocationTargetException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  /**
   * Uses the system property java.class.path to determine the runtime
   * classpath. (Note: this is inherently unreliable as a Classloader is free to
   * ignore java.class.path)
   */
  private static FileCollection getClasspathAsFileCollectionFromClasspath(
      String cp)
      throws IOException {
    StringTokenizer tokenizer = new StringTokenizer(cp, File.pathSeparator);
    FileCollectionCollection collection = new FileCollectionCollection();
    while (tokenizer.hasMoreTokens()) {
      collection.addJarOrDirectory(new File(tokenizer.nextToken()));
    }
    return collection;
  }

  /**
   * Transforms a URLClassLoader into a FileCollection.
   */
  private static FileCollection toFileCollection(URLClassLoader urlcl)
      throws IOException {
    FileCollectionCollection collection = new FileCollectionCollection();
    for (URL url : urlcl.getURLs()) {
      collection.addJarOrDirectory(new File(url.getFile()));
    }
    return collection;
  }


  /**
   * Get all of the non-jdk packages in the current classpath.
   */
  public static Set<String> getAllPackages() throws IOException {
    FileCollection fileCollection = getClasspathAsFileCollection();
    FileCollection jdkFileCollection = JDKFileCollection.getFileCollection();
    Set<String> packages = new TreeSet<String>();
    for (String filename : fileCollection.getFileNames()) {
      if (filename.endsWith(CLASS_SUFFIX)) {
        if (!jdkFileCollection.containsFile(filename)) {
          filename = filename
              .substring(0, filename.length() - CLASS_SUFFIX.length());
          String classname = filename.replace(File.separatorChar, '.');
          String pkg = getPackage(classname);
          packages.add(pkg);
        }
      }
    }
    fileCollection.releaseResources();
    return packages;
  }

  private static String getPackage(String classname) {
    int lastdot = classname.lastIndexOf('.');
    if (lastdot == -1) {
      return ""; // default package
    }
    return classname.substring(0, lastdot);
  }

  /**
   * Get all of the classes in a given package.
   *
   * @param pkg the package to get classes for
   * @return all of the classes found
   */
  public static Set<String> getClassesInPackage(String pkg) throws IOException {
    Set<String> classes = new TreeSet<String>();
    int pkglength = pkg.length();
    if (pkglength > 0) {
      ++pkglength;
    }
    FileCollection fileCollection = getClasspathAsFileCollection();
    for (String filename : fileCollection.getFileNames()) {
      if (filename.endsWith(CLASS_SUFFIX)) {
        filename = filename.substring(0, filename.length() - 6);
        String classname = filename.replace('/', '.');
        if (classname.startsWith(pkg)
            && classname.substring(pkglength).indexOf('.') == -1) {
          classes.add(trimOffInnerClass(classname));
        }
      }
    }
    return classes;
  }

  private static String trimOffInnerClass(String classname) {
    int dollar = classname.indexOf('$');
    if (dollar == -1) {
      return classname;
    } else {
      return classname.substring(0, dollar);
    }
  }


  public static void main(String[] args) throws Exception {
    for (String pkg : getAllPackages()) {
      if (pkg.startsWith("com.google.opengse")) {
        System.out.println(pkg);
        for (String clazz : getClassesInPackage(pkg)) {
          System.out.println(clazz);
        }
      }
    }
  }
}

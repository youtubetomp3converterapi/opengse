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

package com.google.opengse;

import com.google.opengse.testing.ClasspathUtil;
//import com.google.opengse.testing.NoUnitTestNecessary;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Ensures all classes on the classpath have an associated test.
 *
 * @author Mike Jennings
 */
public abstract class AllClassesHaveATestTest extends TestCase {

  private static Set<String> classNamesToCheck;
  private static ClassLoader classLoader
      = Thread.currentThread().getContextClassLoader();

  public static junit.framework.Test suite() throws Exception {
    Set<String> candidates = new HashSet<String>();
    for (String pkg : ClasspathUtil.getAllPackages()) {
      if (pkg.startsWith("com.google.opengse")) {
          candidates.addAll(ClasspathUtil.getClassesInPackage(pkg));
      }
    }
    classNamesToCheck = new TreeSet<String>();
    for (String candidate : candidates) {
      if (candidate.endsWith("Test")) {
        continue;
      }
      if (candidate.endsWith("Tests")) {
        continue;
      }
      if (candidate.endsWith("TestCase")) {
        continue;
      }
      classNamesToCheck.add(candidate);
    }
    TestSuite suite = new TestSuite();
    for (String classNameToCheck : classNamesToCheck) {
      Class<?> clazz = classLoader.loadClass(classNameToCheck);
//      if ((clazz.getPackage().getAnnotation(NoUnitTestNecessary.class) != null)
//          || (clazz.getAnnotation(NoUnitTestNecessary.class) != null)) {
//        continue; // Skip packages/classes annotated with NoUnitTestNecessary
//      }
      if (isAJUnit3or4Test(clazz)) {
        continue; // Don't *require* tests for the tests.
      }
      if (Throwable.class.isAssignableFrom(clazz)) {
        continue; // Don't require tests for exceptions.
      }
      if (clazz.isInterface()) {
        continue; // Don't require tests for interfaces.
      }
      TestSuite subSuite = new TestSuite(classNameToCheck + " has a test");
      final String finalClassNameToCheck = classNameToCheck;

      subSuite.addTest(new AllClassesHaveATestTest() {
          @Override String getClassNameToCheck() {
            return finalClassNameToCheck;
          }
          @Override public String getName() {
            return getClassNameToCheck() + " needs a unit test!";
          }
        });
      suite.addTest(subSuite);
    }
    return suite;
  }

  abstract String getClassNameToCheck();

  @Override public void runTest() throws Throwable {
    String expectedNameOfTestClass = getClassNameToCheck() + "Test";
    try {
      Class<?> testClass = classLoader.loadClass(expectedNameOfTestClass);

      assertTrue(testClass.getName() + " should extend " + TestCase.class
          + " or have a method annotated with " + org.junit.Test.class,
          isAJUnit3or4Test(testClass));
    } catch (ClassNotFoundException e) {
      fail("Couldn't find test named " + expectedNameOfTestClass
          + " for " + getClassNameToCheck());
    }
  }

  private static boolean isAJUnit3or4Test(Class<?> testClass) {
    // JUnit3 tests extend TestCase
    if (TestCase.class.isAssignableFrom(testClass)) {
      return true;
    }

    Class<?> inheritedClass = testClass;
    while (inheritedClass != null) {
      for (Method method : inheritedClass.getDeclaredMethods()) {
        // JUnit4 runs methods marked with @Test in the class or any superclass.
        if (method.getAnnotation(org.junit.Test.class) != null) {
          return true;
        }
        // JUnit3 may use "public static Test suite()" methods.
        if ("suite".equals(method.getName())
            && Modifier.isPublic(method.getModifiers())
            && Modifier.isStatic(method.getModifiers())
            && junit.framework.Test.class.isAssignableFrom(
                method.getReturnType())) {
          return true;
        }
      }
      inheritedClass = inheritedClass.getSuperclass();
    }
    return false;
  }
}

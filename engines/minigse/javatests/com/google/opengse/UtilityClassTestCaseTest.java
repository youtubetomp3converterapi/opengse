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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for {@link UtilityClassTestCase}.
 *
 * @author Mick Killianey
 */
public class UtilityClassTestCaseTest {

  static AssertionError getError(final Class<?> classUnderTest) {
    try {
      UtilityClassTestCase testCase = new UtilityClassTestCase() {
        @Override protected Class<?> getClassUnderTest() {
          return classUnderTest;
        }
      };
      testCase.testThatClassHasNoPublicConstructors();
      testCase.testThatClassIsFinalAndPublicOrPackage();
      testCase.testThatFieldsAreStaticAndLowerVisibilityThanClass();
      testCase.testThatMethodsAreStaticAndLowerVisibilityThanClass();
      return null;
    } catch (AssertionError e) {
      return e;
    }
  }

  static void assertOk(Class<?> classUnderTest) {
    AssertionError error = getError(classUnderTest);
    assertNull(classUnderTest.getName() + " should be ok", error);
  }

  static void assertNotOk(Class<?> classUnderTest) {
    AssertionError error = getError(classUnderTest);
    assertNotNull(classUnderTest.getName() + " should generate error", error);
  }

  /** Has no declared constructor. */
  public static final class HasDefaultConstructor {
    /* no constructor specified, so has the default public constructor */
  }

  /** Has empty public constructor. */
  public static final class HasPublicConstructor {
    public HasPublicConstructor() { /* whatever */ }
  }

  /** Has empty protected constructor. */
  public static final class HasProtectedConstructor {
    protected HasProtectedConstructor() { /* whatever */ }
  }

  /** Has empty package-private constructor. */
  public static final class HasPackageConstructor {
    HasPackageConstructor() { /* whatever */ }
  }

  /** Has empty private constructor. */
  public static final class HasPrivateConstructorNoThrow {
    private HasPrivateConstructorNoThrow() { /* whatever */ }
  }

  /** Has private constructor that throws {@link AssertionError}. */
  public static final class HasPrivateConstructor {
    private HasPrivateConstructor() { throw new AssertionError(); }
  }

  @Test
  public void testThatClassMustHavePrivateConstructorOnly() {
    assertNotOk(HasDefaultConstructor.class);
    assertNotOk(HasPublicConstructor.class);
    assertNotOk(HasProtectedConstructor.class);
    assertNotOk(HasPackageConstructor.class);
    assertOk(HasPrivateConstructor.class);
  }

  /** Is a public class. */
  public static final class IsPublicClass {
    private IsPublicClass() { /* whatever */ }
  }

  /** Is a protected class. */
  protected static final class IsProtectedClass {
    private IsProtectedClass() { /* whatever */ }
  }

  /** Is a package-private class. */
  static final class IsPackageClass {
    private IsPackageClass() { /* whatever */ }
  }

  /** Is a private class:  unusable! */
  private static final class IsPrivateClass {
    private IsPrivateClass() { /* what's the point */ }
  }

  /** Is a non-final public class. */
  public static class IsNonFinalClass {
    private IsNonFinalClass() { throw new AssertionError(); }
  }

  @Test
  public void testTestThatClassIsFinalAndPublicOrPackage() {
    assertOk(IsPublicClass.class);
    assertNotOk(IsProtectedClass.class);
    assertOk(IsPackageClass.class);
    assertNotOk(IsPrivateClass.class);
    assertNotOk(IsNonFinalClass.class);
  }

  /** Has a non-static field which could never be instantiated. */
  public static final class HasNonStaticField {
    private HasNonStaticField() { /* whatever */ }
    public final String FOO = "foo";
  }

  /** Has a public static field in public class: ok. */
  public static final class HasPublicStaticFieldInPublicClass {
    private HasPublicStaticFieldInPublicClass() { /* whatever */ }
    public static final String FOO = "foo";
  }

  /**
   * A public static field in package-private class is confusing:
   * it only has package-private visibility.
   */
  static final class HasPublicStaticFieldInPackageClass {
    private HasPublicStaticFieldInPackageClass() { /* whatever */ }
    public static final String FOO = "foo";
  }

  /** Has a package-private static field in package-private class: ok. */
  static final class HasPackageStaticFieldInPackageClass {
    private HasPackageStaticFieldInPackageClass() { /* whatever */ }
    static final String FOO = "foo";
  }

  @Test
  public void testTestThatFieldsAreStaticAndLowerVisibilityThanClass() {
    assertNotOk(HasNonStaticField.class);
    assertOk(HasPublicStaticFieldInPublicClass.class);
    assertNotOk(HasPublicStaticFieldInPackageClass.class);
    assertOk(HasPackageStaticFieldInPackageClass.class);
  }

  /** A non-static method makes no sense...then this isn't a utility class. */
  public static final class HasNonStaticMethod {
    private HasNonStaticMethod() { /* whatever */ }
    public String getFoo() { return "foo"; }
  }

  /** A public-static method in a public class is ok. */
  public static final class HasPublicStaticMethodInPublicClass {
    private HasPublicStaticMethodInPublicClass() { /* whatever */ }
    public static String getFoo() { return "foo"; }
  }

  /**
   * A public-static method in a package class is confusing:
   * it only has package-private visibility.
   */
  static final class HasPublicStaticMethodInPackageClass {
    private HasPublicStaticMethodInPackageClass() { /* whatever */ }
    public static String getFoo() { return "foo"; }
  }

  /** A package-private static method in a package-private class is ok. */
  static final class HasPackageStaticMethodInPackageClass {
    private HasPackageStaticMethodInPackageClass() { /* whatever */ }
    static String getFoo() { return "foo"; }
  }

  @Test
  public void testTestThatMethodsAreStaticAndLowerVisibilityThanClass() {
    assertNotOk(HasNonStaticMethod.class);
    assertOk(HasPublicStaticMethodInPublicClass.class);
    assertNotOk(HasPublicStaticMethodInPackageClass.class);
    assertOk(HasPackageStaticMethodInPackageClass.class);
  }
}

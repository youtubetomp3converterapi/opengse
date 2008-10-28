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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Base class for utility classes, to check for the typical mistakes that
 * people make:
 * <ul>
 *   <li><b>The class should be public or package.</b>
 *   It makes no sense to be <em>protected</em>, since you shouldn't subclass a
 *   utility class, and it makes no sense to be <em>private</em>, since a
 *   typical security manager would stop you from accessing anything in it!
 *   </li>
 *   <li><b>The class should have no visible constructors.</b>  Users should
 *   not be permitted to instantiate a class with only static utilities, since
 *   it may give them the mistaken impression that they can override methods.
 *   </li>
 *   </li><b>The class should not be instantiable.  (Optional)</b>  Even if the
 *   class has no <em>visible</em> constructors, methods in the class may
 *   (incorrectly) invoke a private constructor within the class.  This can be
 *   explicitly prevented by having the private constructor fail: <pre>
 *   public final class Foo {
 *     private Foo() { throws new AssertionError(); }
 *     ...
 *   } </pre>  This test is <em>disabled</em> by default.
 *   <li><b>Fields should be static.</b>  Since you cannot instantiate a
 *   utility class, it makes no sense to create any non-static fields.
 *   </li>
 *   <li><b>Fields should be of equal or lesser visibility that the class.</b>
 *   Declaring a public method in a class with package visibility doesn't
 *   actually provide public access, since the method can only be invoked when
 *   qualified by the class (which does not have public visibility).
 *   </li>
 * </ul>
 *
 * The optional test for non-instantiability must be explicitly enabled in
 * subclasses by calling the {@link #optionalTestForNonInstantiability()}
 * method:
 * <pre>
 * {@literal @}org.junit.Test
 * {@literal @}Override public void optionalTestForNonInstantiability() {
 *   super.optionalTestForNonInstantiability();
 * }
 * </pre>
 * @author Mick Killianey
 */
public abstract class UtilityClassTestCase {

  protected abstract Class<?> getClassUnderTest();

  @Test
  public void testThatClassIsFinalAndPublicOrPackage() {
    Class<?> clazz = getClassUnderTest();
    int clazzModifiers = clazz.getModifiers();
    assertTrue("Utility class should be final",
        Modifier.isFinal(clazzModifiers));

    assertFalse("Utility class should not be private",
        Modifier.isPrivate(clazzModifiers));
    assertFalse("Utility class should not be protected",
        Modifier.isProtected(clazzModifiers));
  }

  @Test
  public void testThatClassHasNoPublicConstructors() {
    Class<?> clazz = getClassUnderTest();
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    for (Constructor<?> constructor : constructors) {
      assertTrue("Any constructor should be private",
          Modifier.isPrivate(constructor.getModifiers()));
    }
  }

  @Test
  public void testThatFieldsAreStaticAndLowerVisibilityThanClass() {
    Class<?> clazz = getClassUnderTest();
    int clazzModifiers = clazz.getModifiers();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      int fieldModifiers = field.getModifiers();

      assertTrue("Field: " + field + " should be static",
          Modifier.isStatic(fieldModifiers));

      assertFalse("Field: " + field + " should not be protected",
          Modifier.isProtected(fieldModifiers));

      if ((clazzModifiers & Modifier.PUBLIC) != Modifier.PUBLIC) {
        assertFalse(
            "In a non-public class, should not have a public field:" + field,
            Modifier.isPublic(fieldModifiers));
      }
    }
  }

  @Test
  public void testThatMethodsAreStaticAndLowerVisibilityThanClass() {
    Class<?> clazz = getClassUnderTest();
    int clazzModifiers = clazz.getModifiers();
    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      int methodModifiers = method.getModifiers();

      assertTrue("Field: " + method + " should be static",
          Modifier.isStatic(methodModifiers));

      assertFalse("Field: " + method + " should not be protected",
          Modifier.isProtected(methodModifiers));

      if ((clazzModifiers & Modifier.PUBLIC) != Modifier.PUBLIC) {
        assertFalse(
            "In a non-public class, should not have a public method:" + method,
            Modifier.isPublic(methodModifiers));
      }
    }
  }

  /* @Test */ /* Commented out because this is an optional test. */
  public void optionalTestForNonInstantiability() {
    Class<?> clazz = getClassUnderTest();
    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
      try {
        constructor.newInstance();
      } catch (InstantiationException e) {
        continue;
      } catch (IllegalAccessException e) {
        continue;
      } catch (InvocationTargetException e) {
        continue;
      } catch (RuntimeException e) {
        continue;
      }
      fail("The constructor: " + constructor + " permits instantiation!"
          + " (This test encourages good practice, but is entirely optional."
          + "  Override this with an empty test if you don't want it.)");
    }
  }
}

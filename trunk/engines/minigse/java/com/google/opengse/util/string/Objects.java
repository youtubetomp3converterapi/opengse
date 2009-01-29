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

package com.google.opengse.util.string;

import java.util.Arrays;

/**
 * Helper functions for operating on {@code Object}s.
 *
 * @author Laurence Gonsalves
 */
public final class Objects {
  private Objects() {}

  /**
   * Gets hash code of an object, optionally returns hash code based on the
   * "deep contents" of array if the object is an array.
   * <p>
   * If {@code o} is null, 0 is returned; if {@code o} is an array, the
   * corresponding {@link Arrays#deepHashCode(Object[])}, or
   * {@link Arrays#hashCode(int[])} or the like is used to calculate the hash
   * code.
   */
  public static int deepHashCode(Object o) {
    if (o == null) {
      return 0;
    }
    if (!o.getClass().isArray()) {
      return o.hashCode();
    }
    if (o instanceof Object[]) {
      return Arrays.deepHashCode((Object[]) o);
    }
    if (o instanceof boolean[]) {
      return Arrays.hashCode((boolean[]) o);
    }
    if (o instanceof char[]) {
      return Arrays.hashCode((char[]) o);
    }
    if (o instanceof byte[]) {
      return Arrays.hashCode((byte[]) o);
    }
    if (o instanceof short[]) {
      return Arrays.hashCode((short[]) o);
    }
    if (o instanceof int[]) {
      return Arrays.hashCode((int[]) o);
    }
    if (o instanceof long[]) {
      return Arrays.hashCode((long[]) o);
    }
    if (o instanceof float[]) {
      return Arrays.hashCode((float[]) o);
    }
    if (o instanceof double[]) {
      return Arrays.hashCode((double[]) o);
    }
    throw new AssertionError();
  }

  /**
   * Gets string representation of an object, or the "deep content" of the array
   * if the {@code o} is an array.
   * <p>
   * If {@code o} is null, {@code "null"} is returned; if {@code o} is an
   * array, the corresponding {@link Arrays#deepToString(Object[])},
   * {@link Arrays#toString(int[])} or the like is used to get the string.
   */
  public static String deepToString(Object o) {
    if (o == null) {
      return String.valueOf(o);
    }
    if (!o.getClass().isArray()) {
      return o.toString();
    }
    if (o instanceof Object[]) {
      return Arrays.deepToString((Object[]) o);
    }
    if (o instanceof boolean[]) {
      return Arrays.toString((boolean[]) o);
    }
    if (o instanceof char[]) {
      return Arrays.toString((char[]) o);
    }
    if (o instanceof byte[]) {
      return Arrays.toString((byte[]) o);
    }
    if (o instanceof short[]) {
      return Arrays.toString((short[]) o);
    }
    if (o instanceof int[]) {
      return Arrays.toString((int[]) o);
    }
    if (o instanceof long[]) {
      return Arrays.toString((long[]) o);
    }
    if (o instanceof float[]) {
      return Arrays.toString((float[]) o);
    }
    if (o instanceof double[]) {
      return Arrays.toString((double[]) o);
    }
    throw new AssertionError();
  }

  /**
   * Determines if two objects are equal as determined by
   * {@link Object#equals(Object)}, or "deeply equal" if both are arrays.
   * <p>
   * If both objects are null, true is returned; if both objects are array, the
   * corresponding {@link Arrays#deepEquals(Object[], Object[])}, or
   * {@link Arrays#equals(int[], int[])} or the like are called to determine
   * equality.
   * <p>
   * Note that this method does not "deeply" compare the fields of the
   * objects.
   */
  public static boolean deepEquals(
      Object o1, Object o2) {
    if (o1 == o2) {
      return true;
    }
    if (o1 == null || o2 == null) {
      return false;
    }

    Class<?> type1 = o1.getClass();
    Class<?> type2 = o2.getClass();
    if (!(type1.isArray() && type2.isArray())) {
      return o1.equals(o2);
    }
    if (o1 instanceof Object[] && o2 instanceof Object[]) {
      return Arrays.deepEquals((Object[]) o1, (Object[]) o2);
    }
    if (type1 != type2) {
      return false;
    }
    if (o1 instanceof boolean[]) {
      return Arrays.equals((boolean[]) o1, (boolean[]) o2);
    }
    if (o1 instanceof char[]) {
      return Arrays.equals((char[]) o1, (char[]) o2);
    }
    if (o1 instanceof byte[]) {
      return Arrays.equals((byte[]) o1, (byte[]) o2);
    }
    if (o1 instanceof short[]) {
      return Arrays.equals((short[]) o1, (short[]) o2);
    }
    if (o1 instanceof int[]) {
      return Arrays.equals((int[]) o1, (int[]) o2);
    }
    if (o1 instanceof long[]) {
      return Arrays.equals((long[]) o1, (long[]) o2);
    }
    if (o1 instanceof float[]) {
      return Arrays.equals((float[]) o1, (float[]) o2);
    }
    if (o1 instanceof double[]) {
      return Arrays.equals((double[]) o1, (double[]) o2);
    }
    throw new AssertionError();
  }

  /**
   * Determines whether the two, possibly {@code null}, objects are equal.
   *
   * <p>This method will return:
   * <ul>
   * <li>{@code true} if o1 and o2 are both null.
   * <li>{@code true} if o1 and o2 are both non-null and they are equal
   * according to {@link Object#equals(Object)}.
   * <li>{@code false} in all other situations.
   * </ul>
   * <p>Note that this assumes that all non-null objects passed to this
   * function fully conform to the contract specified by {@link
   * Object#equals(Object)}.
   */
  public static boolean equal(Object o1, Object o2) {
    return (o1 == o2) || ((o1 != null) && (o2 != null) && o1.equals(o2));
  }

  /**
   * Generates a hashcode for multiple values.
   *
   * <p>This is useful for implementing Object.hashCode(). For example, in an
   * object that has three properties, x, y and z, one could write:
   * <pre>
   * public int hashCode() {
   *   return Objects.hashCode(getX(), getY(), getZ());
   * }
   * </pre>
   */
  public static int hashCode(Object... objects) {
    return Arrays.hashCode(objects);
  }

  /**
   * Checks that the specified object is not {@code null}.
   *
   * @param o the object to check for nullness.
   * @return {@code o} if not null.
   * @throws NullPointerException if {@code o} is null.
   */
  public static <T> T nonNull(T o) {
    if (o == null) {
      throw new NullPointerException();
    }
    return o;
  }

  /**
   * Checks that the specified object is not {@code null}.
   *
   * @param o the object to check for nullness.
   * @param message exception message used in the event that a {@code
   * NullPointerException} is thrown.
   * @return {@code o} if not null.
   * @throws NullPointerException if {@code o} is null.
   */
  public static <T> T nonNull(T o, String message) {
    if (o == null) {
      throw new NullPointerException(message);
    }
    return o;
  }

  /**
   * Returns the first of the given parameters that is not {@code null} if any,
   * or otherwise throws {@link NullPointerException}.
   *
   * @return {@code first} if {@code first} is not {@code null}, or
   *     {@code second} if {@code first} is {@code null} and {@code second} is
   *     not {@code null}.
   * @throws NullPointerException if both {@code first} and {@code second} were
   *     {@code null}.
   */
  public static <T> T firstNonNull(T first, T second) {
    return first != null ? first : nonNull(second);
  }
}

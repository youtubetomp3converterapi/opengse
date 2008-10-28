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

import com.google.opengse.UtilityClassTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Unit test for {@link StreamUtils} utility methods.
 *
 * @author Mick Killianey
 */
public class StreamUtilsTest extends UtilityClassTestCase {

  @Override protected Class<?> getClassUnderTest() {
    return StreamUtils.class;
  }

  @Test
  public void testToStream() throws IOException {
    String expected = "Hello, world\nGoodbye my sweet.";
    ByteArrayInputStream io = new ByteArrayInputStream(expected.getBytes());
    assertEquals(expected, StreamUtils.toString(io));
  }

  @Ignore @Test
  public void testToStreamClosesWhenDone() throws IOException {
    String expected = "Lots\tOf\nFunky\rDelimiter";
    final AtomicBoolean inputStreamWasClosed = new AtomicBoolean(false);
    ByteArrayInputStream io = new ByteArrayInputStream(expected.getBytes()) {
      @Override public void close() throws IOException {
        inputStreamWasClosed.set(true);
        super.close();
      }
    };
    assertEquals(expected, StreamUtils.toString(io));
    assertTrue(inputStreamWasClosed.get());
  }
}

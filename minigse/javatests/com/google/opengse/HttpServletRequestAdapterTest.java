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

import org.easymock.classextension.EasyMock;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletInputStream;

/**
 * Unit test for {@link HttpServletRequestAdapter}
 *
 * @author Mick Killianey
 */
public class HttpServletRequestAdapterTest {

  private HttpRequest mockSubset;
  private HttpServletRequestAdapter adapter;
  private static final String NAME = "foo";
  private static final String VALUE = "bar";

  @Before
  public void setUp() {
    mockSubset = EasyMock.createStrictMock(HttpRequest.class);
    EasyMock.expect(mockSubset.getConnectionInformation()).andReturn(null);
    EasyMock.replay(mockSubset);
    adapter = new HttpServletRequestAdapter(mockSubset);
    EasyMock.verify(mockSubset);
    // We have to do a replay and reset here because HttpServletRequestAdapter
    // calls getConnectionInformation() on the HttpRequest in its constructor.
    EasyMock.reset(mockSubset);
  }

  @SuppressWarnings("unchecked")
  static <E> Enumeration<E> createMockEnumeration() {
    return EasyMock.createMock(Enumeration.class);
  }

  @Test(expected = NullPointerException.class)
  public void testCannotConstructWithNullSubset() {
    new HttpServletRequestAdapter(null);
  }

  @Test
  public void testGetHeader() {
    EasyMock.expect(mockSubset.getHeader(NAME)).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getHeader(NAME), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetHeaders() {
    final Enumeration<String> mockEnumeration = createMockEnumeration();
    EasyMock.expect(mockSubset.getHeaders(NAME)).andReturn(mockEnumeration);
    EasyMock.replay(mockSubset, mockEnumeration);
    assertThat(adapter.getHeaders(NAME), sameInstance(mockEnumeration));
    EasyMock.verify(mockSubset, mockEnumeration);
  }

  @Test
  public void testGetHeaderNames() {
    final Enumeration<String> mockEnumeration = createMockEnumeration();
    EasyMock.expect(mockSubset.getHeaderNames()).andReturn(mockEnumeration);
    EasyMock.replay(mockSubset, mockEnumeration);
    assertThat(adapter.getHeaderNames(), sameInstance(mockEnumeration));
    EasyMock.verify(mockSubset, mockEnumeration);
  }

  @Test
  public void testGetMethod() {
    EasyMock.expect(mockSubset.getMethod()).andReturn(NAME);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getMethod(), is(NAME));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetPathTranslated() {
    EasyMock.expect(mockSubset.getPathTranslated()).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getPathTranslated(), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetQueryString() {
    EasyMock.expect(mockSubset.getQueryString()).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getQueryString(), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetRequestURI() {
    EasyMock.expect(mockSubset.getRequestURI()).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getRequestURI(), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetRequestURL() {
    final StringBuffer requestUrl = new StringBuffer(VALUE);
    EasyMock.expect(mockSubset.getRequestURL()).andReturn(requestUrl);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getRequestURL(), is(requestUrl));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testSetCharacterEncoding_passesEncodingToSubset()
      throws UnsupportedEncodingException {
    mockSubset.setCharacterEncoding(EasyMock.eq(VALUE));
    EasyMock.expectLastCall().once();
    EasyMock.replay(mockSubset);
    adapter.setCharacterEncoding(VALUE);
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testSetCharacterEncoding_allowsExceptionToPassBack()
      throws UnsupportedEncodingException {
    final UnsupportedEncodingException expected
        = new UnsupportedEncodingException();
    mockSubset.setCharacterEncoding(EasyMock.eq(VALUE));
    EasyMock.expectLastCall().andThrow(expected);
    EasyMock.replay(mockSubset);
    try {
      adapter.setCharacterEncoding(VALUE);
      fail("Should pass exception through");
    } catch (UnsupportedEncodingException actual) {
      assertThat(actual, sameInstance(expected));
    }
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetInputStream_passesBackStream() throws IOException {
    final ServletInputStream mockSis
        = EasyMock.createMock(ServletInputStream.class);
    EasyMock.expect(mockSubset.getInputStream()).andReturn(mockSis);
    EasyMock.replay(mockSubset);
    EasyMock.replay(mockSis);
    assertThat(adapter.getInputStream(), sameInstance(mockSis));
    EasyMock.verify(mockSubset);
    EasyMock.verify(mockSis);
  }

  @Test
  public void testGetInputStream_allowsExceptionToPassBack()
      throws IOException {
    final IOException expected = new IOException();
    EasyMock.expect(mockSubset.getInputStream()).andThrow(expected);
    EasyMock.replay(mockSubset);
    try {
      adapter.getInputStream();
      fail("Should throw exception");
    } catch (IOException actual) {
      assertThat(actual, sameInstance(expected));
    }
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetParameterMap() {
    final String[] value1 = { "one" };
    final String[] value2 = { "two", "dos" };
    final Map<String, String[]> map = new HashMap<String, String[]>();
    map.put("1", value1);
    map.put("2", value2);
    EasyMock.expect(mockSubset.getParameterMap()).andReturn(map);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getParameterMap(), is(map));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetProtocol() {
    EasyMock.expect(mockSubset.getProtocol()).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getProtocol(), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetScheme() {
    EasyMock.expect(mockSubset.getScheme()).andReturn(VALUE);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getScheme(), is(VALUE));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetReader_passesBackValueFromSubset()
      throws IOException {
    final BufferedReader mockReader = EasyMock.createMock(BufferedReader.class);
    EasyMock.expect(mockSubset.getReader()).andReturn(mockReader);
    EasyMock.replay(mockSubset);
    EasyMock.replay(mockReader);
    assertThat(adapter.getReader(), sameInstance(mockReader));
    EasyMock.verify(mockSubset);
    EasyMock.verify(mockReader);
  }

  @Test
  public void testGetReader_passesThroughExceptionFromSubset()
      throws IOException {
    final IOException expected = new IOException();
    EasyMock.expect(mockSubset.getReader()).andThrow(expected);
    EasyMock.replay(mockSubset);
    try {
      adapter.getReader();
      fail("Should pass through exception");
    } catch (IOException actual) {
      assertThat(actual, sameInstance(expected));
    }
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetLocale() {
    final Locale locale = Locale.ITALIAN;
    EasyMock.expect(mockSubset.getLocale()).andReturn(locale);
    EasyMock.replay(mockSubset);
    assertThat(adapter.getLocale(), is(locale));
    EasyMock.verify(mockSubset);
  }

  @Test
  public void testGetLocales() {
    final Enumeration<Locale> mockEnumeration = createMockEnumeration();
    EasyMock.expect(mockSubset.getLocales()).andReturn(mockEnumeration);
    EasyMock.replay(mockSubset, mockEnumeration);
    assertThat(adapter.getLocales(), sameInstance(mockEnumeration));
    EasyMock.verify(mockSubset, mockEnumeration);
  }
}

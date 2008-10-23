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

package com.google.opengse.util;

import com.google.opengse.UtilityClassTestCase;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Properties;

/**
 * @author Mike Jennings
 */
public class PropertiesUtilTest extends UtilityClassTestCase {

  @Override
  protected Class<?> getClassUnderTest() {
    return PropertiesUtil.class;
  }

  private static final String KEY1 = "x";
  private static final String KEY2 = "y";
  private static final String PORT = "port";
  private static final String HOST = "host";
  private static final String URL = "url";
  private static final String HOST2 = "host2";

  @Test
  public void testIncompleteAliasAsValue() throws Exception {
    Properties props = new Properties();
    props.setProperty(KEY1, "$");
    String value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("$", value);
    props.setProperty(KEY1, "${");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("${", value);
    props.setProperty(KEY1, "${}");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("${}", value);
  }

  @Test
  public void testBadAliasAsValue() throws Exception {
    Properties props = new Properties();

    props.setProperty(KEY1, "$}");
    String value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("$}", value);

    props.setProperty(KEY1, "$$");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("$$", value);

    props.setProperty(KEY1, "${$");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("${$", value);
  }

  @Test
  public void testAliasWithoutValue() throws Exception {
    Properties props = new Properties();

    props.setProperty(KEY1, "${host}");
    String value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("${host}", value);
  }

  @Test
  public void test2LevelsOfIndirection() throws Exception {
    Properties props = new Properties();

    props.setProperty(URL, "http://${host}:${port}");
    props.setProperty(KEY1, "${url}");
    String value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://${host}:${port}", value);
    props.setProperty(HOST, "localhost");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://localhost:${port}", value);
    props.setProperty(HOST, "local${host}");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://local${host}:${port}", value);

    props.setProperty(HOST, "local${}");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://local${}:${port}", value);

    props.setProperty(HOST, "local${y}");
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://local${y}:${port}", value);

    props.setProperty(KEY2, HOST);
    value = PropertiesUtil.getAliasedProperty(props, KEY1, null);
    assertEquals("http://localhost:${port}", value);
  }

  @Test
  public void testSimpleAlias() throws Exception {
    Properties props = new Properties();

    props.setProperty(URL, "http://${host}:${port}");
    props.setProperty(HOST, "sparky");
    props.setProperty(PORT, "8090");
    String value = PropertiesUtil.getAliasedProperty(props, URL, null);
    assertEquals("http://sparky:8090", value);
  }

  @Test
  public void testAliasedPropertyWithRepeatedValue() throws Exception {
    Properties props = new Properties();
    props.setProperty(URL, "http://${host}:${port},http://${host2}:${port}");
    props.setProperty(HOST, "sparky");
    props.setProperty(HOST2, "buddy");
    props.setProperty(PORT, "8090");
    String value = PropertiesUtil.getAliasedProperty(props, URL, null);
    assertEquals("http://sparky:8090,http://buddy:8090", value);
  }
}

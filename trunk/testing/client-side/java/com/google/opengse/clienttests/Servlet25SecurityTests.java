// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.clienttests;

import org.junit.Test;

/**
 * All security related servlet 2.5 compliance tests.
 *
 * @author Mike Jennings
 */
public class Servlet25SecurityTests extends ServletTestsWhichConnectToARemoteServer {

  /**
   * A role-name may be specified with the servlet, with an optional role-link,
   * which references to a role defined externally in web.xml.
   *
   * And HttpServletRequest#isUserInRole should return the role-name, instead of
   * the role-link value.
   *
   * Pre-condition: user of "role-name" needs be authenticated first.
   */
  @Test
  public void testServletRoleNameWithLink1()  throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/ServletSecurityRoleTest?target-role=role-name");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("role-name=true");
    get.connectToServerAndAssert();
  }
  @Test
  public void testServletRoleNameWithLink2()  throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/ServletSecurityRoleTest?target-role=external_role");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("external_role=false");
    get.connectToServerAndAssert();
  }

  /**
   * When no "role-link" specified, the role-name references to a role defined
   * externally in web.xml.
   *
   * Pre-condition: user of "another_role" needs be authenticated first.
   */
  @Test
  public void testServletRoleNameWithoutLink()  throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/ServletSecurityRoleTest?target-role=another_role");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("another_role=true");
    get.connectToServerAndAssert();
  }
}
// Copyright 2006 Google Inc. All Rights Reserved.
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

package com.google.opengse.configuration.webxml;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppEjbLocalRef;
import com.google.opengse.configuration.WebAppEjbRef;
import com.google.opengse.configuration.WebAppEnvEntry;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppFormLoginConfig;
import com.google.opengse.configuration.WebAppInitParam;
import com.google.opengse.configuration.WebAppListener;
import com.google.opengse.configuration.WebAppLoginConfig;
import com.google.opengse.configuration.WebAppMimeMapping;
import com.google.opengse.configuration.WebAppResourceEnvRef;
import com.google.opengse.configuration.WebAppResourceRef;
import com.google.opengse.configuration.WebAppSecurityConstraint;
import com.google.opengse.configuration.WebAppSecurityRole;
import com.google.opengse.configuration.WebAppSecurityRoleRef;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.configuration.WebAppTagLib;
import com.google.opengse.configuration.WebAppWebResourceCollection;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Class that knows how to dump a WebAppConfiguration object to a
 * WEB-INF/web.xml file.
 *
 * @author Mike Jennings
 */
public final class WebXmlDump {

  private WebXmlDump() { /* Utility class: do not instantiate. */
  }

  private static final String XML_PREFIX
      = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  private static final String XML_DOCTYPE_FOR_23
      = "<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, "
      + "Inc.//DTD Web Application 2.3//EN\""
      + " \"http://java.sun.com/dtd/web-app_2_3.dtd\">";

  /**
   * Dump configuration to the supplied PrintWriter.
   * @param webapp The web app configuration to write
   * @param out the PrintWriter to write the configuration to
   */
  public static void dump(WebAppConfiguration webapp, PrintWriter out) {
    dump(webapp, 2.5f, out);
  }

  /**
   * Dump configuration to the supplied PrintWriter.
   *
   * @param webapp the configuration to dump
   * @param version what version to use (2.3, 2.5)
   */
  public static void
  dump(
      WebAppConfiguration webapp, float version, PrintWriter out) {
    if (version != 2.3f && version != 2.5f) {
      throw new IllegalArgumentException("Unsupported version: " + version);
    }
    out.println(XML_PREFIX);
    if (version == 2.3f) {
      out.println(XML_DOCTYPE_FOR_23);
      out.println();
      out.println("<web-app>");
    } else {
      out.println("<web-app xmlns=\"http://java.sun.com/xml/ns/javaee\"");
      out.println("         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
      out.println("         xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee");
      out.println("         http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\"");
      out.println("         version=\"2.5\">");
    }
    if (webapp.getIcon() != null) {
      out.println("  <icon>");
      if (webapp.getIcon().getSmallIcon() != null) {
        out.println("    <small-icon>" + webapp.getIcon().getSmallIcon()
            + "</small-icon>");
      }
      if (webapp.getIcon().getLargeIcon() != null) {
        out.println("    <large-icon>" + webapp.getIcon().getLargeIcon()
            + "</large-icon>");
      }
      out.println("  </icon>");
    }
    out.println();
    if (webapp.getDisplayName() != null) {
      out.println(
          "  <display-name>" + webapp.getDisplayName() + "</display-name>");
    }
    if (webapp.getDescription() != null) {
      out.println(
          "  <description>" + webapp.getDescription() + "</description>");
    }
    if (webapp.isDistributable()) {
      out.println("  <distributable/>");
    }

    out.println();
    for (WebAppListener listener : webapp.getListeners()) {
      out.println("  <listener>");
      if (listener.getListenerClass() != null) {
        out.println("    <listener-class>" + listener.getListenerClass()
            + "</listener-class>");
      }
      out.println("  </listener>");
    }

    for (WebAppContextParam cparam : webapp.getContextParams()) {
      out.println("  <context-param>");
      if (cparam.getParamName() != null) {
        out.println(
            "    <param-name>" + cparam.getParamName() + "</param-name>");
      }
      if (cparam.getParamValue() != null) {
        out.println(
            "    <param-value>" + cparam.getParamValue() + "</param-value>");
      }
      if (cparam.getDescription() != null) {
        out.println(
            "    <description>" + cparam.getDescription() + "</description>");
      }
      out.println("  </context-param>");
    }
    for (WebAppFilter filter : webapp.getFilters()) {
      out.println("  <filter>");
      if (filter.getDescription() != null) {
        out.println(
            "    <description>" + filter.getDescription() + "</description>");
      }
      if (filter.getDisplayName() != null) {
        out.println("    <display-name>" + filter.getDisplayName() + "</display-name>");
      }
      if (filter.getFilterName() != null) {
        out.println("    <filter-name>" + filter.getFilterName() + "</filter-name>");
      }
      if (filter.getFilterClass() != null) {
        out.println("    <filter-class>" + filter.getFilterClass() + "</filter-class>");
      }
      if (filter.getIcon() != null) {
        if (filter.getIcon().getSmallIcon() != null) {
          out.println("    <small-icon>" + filter.getIcon().getSmallIcon()
              + "</small-icon>");
        }
        if (filter.getIcon().getLargeIcon() != null) {
          out.println("    <large-icon>" + filter.getIcon().getLargeIcon()
              + "</large-icon>");
        }
      }
      for (WebAppInitParam initparam : filter.getInitParams()) {
        out.println("    <init-param>");
        if (initparam.getDescription() != null) {
          out.println("      <description>" + initparam.getDescription()
              + "</description>");
        }
        if (initparam.getParamName() != null) {
          out.println("      <param-name>" + initparam.getParamName()
              + "</param-name>");
        }
        if (initparam.getParamValue() != null) {
          out.println("      <param-value>" + initparam.getParamValue()
              + "</param-value>");
        }
        out.println("    </init-param>");
      }
      out.println("  </filter>");
    }
    dumpServletDefinitions(webapp, out);
    dumpFilterMappings(webapp, out);

    for (WebAppServletMapping smap : webapp.getServletMappings()) {
      out.println("  <servlet-mapping>");
      if (smap.getServletName() != null) {
        out.println(
            "    <servlet-name>" + smap.getServletName() + "</servlet-name>");
      }
      if (smap.getUrlPattern() != null) {
        out.println(
            "    <url-pattern>" + smap.getUrlPattern() + "</url-pattern>");
      }
      out.println("  </servlet-mapping>");
    }
    if (webapp.getSessionConfig() != null) {
      out.println("  <session-config>");
      if (webapp.getSessionConfig().getSessionTimeout() != null) {
        out.println("    <session-timeout>"
            + webapp.getSessionConfig().getSessionTimeout()
            + "</session-timeout>");
      }
      out.println("  </session-config>");
    }
    for (WebAppMimeMapping mapping : webapp.getMimeMappings()) {
      out.println("  <mime-mapping>");
      if (mapping.getExtension() != null) {
        out.println(
            "    <extension>" + mapping.getExtension() + "</extension>");
      }
      if (mapping.getMimeType() != null) {
        out.println("    <mime-type>" + mapping.getMimeType() + "</mime-type>");
      }
      out.println("  </mime-mapping>");
    }
    if (webapp.getWelcomeFileList() != null) {
      out.println("  <welcome-file-list>");
      for (String file : webapp.getWelcomeFileList().getWelcomeFiles()) {
        out.println("    <welcome-file>" + file + "</welcome-file>");
      }
      out.println("  </welcome-file-list>");
    }
    for (WebAppErrorPage epage : webapp.getErrorPages()) {
      out.println("  <error-page>");
      if (epage.getErrorCode() != null) {
        out.println(
            "    <error-code>" + epage.getErrorCode() + "</error-code>");
      }
      if (epage.getExceptionType() != null) {
        out.println("    <exception-type>" + epage.getExceptionType()
            + "</exception-type>");
      }
      if (epage.getLocation() != null) {
        out.println("    <location>" + epage.getLocation() + "</location>");
      }
      out.println("  </error-page>");
    }
    for (WebAppTagLib taglib : webapp.getTagLibs()) {
      out.println("  <taglib>");
      if (taglib.getTaglibUri() != null) {
        out.println(
            "    <taglib-uri>" + taglib.getTaglibUri() + "</taglib-uri>");
      }
      if (taglib.getTaglibLocation() != null) {
        out.println("    <taglib-location>" + taglib.getTaglibLocation()
            + "</taglib-location>");
      }
      out.println("  </taglib>");
    }
    for (WebAppResourceEnvRef envref : webapp.getResourceEnvRefs()) {
      out.println("  <resource-env-ref>");
      if (envref.getDescription() != null) {
        out.println(
            "    <description>" + envref.getDescription() + "</description>");
      }
      if (envref.getEnvRefName() != null) {
        out.println("    <resource-env-ref-name>" + envref.getEnvRefName()
            + "<resource-env-ref-name>");
      }
      if (envref.getEnvRefType() != null) {
        out.println("    <resource-env-ref-type>" + envref.getEnvRefType()
            + "</resource-env-ref-type>");
      }
      out.println("  </resource-env-ref>");
    }
    for (WebAppResourceRef ref : webapp.getResourceRefs()) {
      out.println("  <resource-ref>");
      if (ref.getDescription() != null) {
        out.println(
            "    <description>" + ref.getDescription() + "</description>");
      }
      if (ref.getResRefName() != null) {
        out.println(
            "    <res-ref-name>" + ref.getResRefName() + "</res-ref-name>");
      }
      if (ref.getResType() != null) {
        out.println("    <res-type>" + ref.getResType() + "</res-type>");
      }
      if (ref.getResAuth() != null) {
        out.println("    <res-auth>" + ref.getResAuth() + "</res-auth>");
      }
      if (ref.getResSharingScope() != null) {
        out.println("    <res-sharing-scope>" + ref.getResSharingScope()
            + "</res-sharing-scope>");
      }
      out.println("  </resource-ref>");
    }
    for (WebAppSecurityConstraint sc : webapp.getSecurityConstraints()) {
      out.println("  <security-constraint>");
      if (sc.getDisplayName() != null) {
        out.println(
            "    <display-name>" + sc.getDisplayName() + "</display-name>");
      }
      for (WebAppWebResourceCollection wrc : sc.getWebResourceCollections()) {
        out.println("    <web-resource-collection>");
        if (wrc.getDescription() != null) {
          out.println(
              "      <description>" + wrc.getDescription() + "</description>");
        }
        if (wrc.getWebResourceName() != null) {
          out.println("      <web-resource-name>" + wrc.getWebResourceName()
              + "</web-resource-name>");
        }
        for (String url : wrc.getUrlPatterns()) {
          out.println("      <url-pattern>" + url + "</url-pattern>");
        }
        for (String htm : wrc.getHttpMethods()) {
          out.println("      <http-method>" + htm + "</http-method>");
        }
        out.println("    </web-resource-collection>");
      }
      if (sc.getAuthConstraint() != null) {
        out.println("    <auth-constraint>");
        if (sc.getAuthConstraint().getDescription() != null) {
          out.println("      <description>"
              + sc.getAuthConstraint().getDescription() + "</description>");
        }
        for (String role : sc.getAuthConstraint().getRoleNames()) {
          out.println("      <role-name>" + role + "</role-name>");
        }
        out.println("    </auth-constraint>");
      }

      if (sc.getUserDataConstraint() != null) {
        out.println("    <user-data-constraint>");
        if (sc.getUserDataConstraint().getDescription() != null) {
          out.println("      <description>"
              + sc.getUserDataConstraint().getDescription() + "</description>");
        }
        if (sc.getUserDataConstraint().getTransportGuarantee() != null) {
          out.println("      <transport-guarantee>"
              + sc.getUserDataConstraint().getTransportGuarantee() + "</transport-guarantee>");
        }
        out.println("    </user-data-constraint>");
      }


      out.println("  </security-constraint>");
    }
    if (webapp.getLoginConfig() != null) {
      WebAppLoginConfig lconfig = webapp.getLoginConfig();
      out.println("  <login-config>");
      if (lconfig.getAuthMethod() != null) {
        out.println(
            "    <auth-method>" + lconfig.getAuthMethod() + "</auth-method>");
      }
      if (lconfig.getRealmName() != null) {
        out.println(
            "    <realm-name>" + lconfig.getRealmName() + "</realm-name>");
      }
      WebAppFormLoginConfig formlogin = lconfig.getFormLoginConfig();
      if (formlogin != null) {
        out.println("    <form-login-config>");
        if (formlogin.getFormLoginPage() != null) {
          out.println("      <form-login-page>" + formlogin.getFormLoginPage()
              + "</form-login-page>");
        }
        if (formlogin.getFormErrorPage() != null) {
          out.println("      <form-error-page>" + formlogin.getFormErrorPage()
              + "</form-error-page>");
        }
        out.println("    </form-login-config>");
      }
      out.println("  </login-config>");
    }
    for (WebAppSecurityRole role : webapp.getSecurityRoles()) {
      out.println("  <security-role>");
      if (role.getDescription() != null) {
        out.println(
            "    <description>" + role.getDescription() + "</description>");
      }
      if (role.getRoleName() != null) {
        out.println("    <role-name>" + role.getRoleName() + "</role-name>");
      }
      out.println("  </security-role>");
    }

    for (WebAppEnvEntry envEntry : webapp.getEnvEntries()) {
      out.println("  <env-entry>");
      if (envEntry.getDescription() != null) {
        out.println(
            "    <description>" + envEntry.getDescription() + "</description>");
      }
      if (envEntry.getEnvEntryName() != null) {
        out.println("    <env-entry-name>" + envEntry.getEnvEntryName()
            + "</env-entry-name>");
      }
      if (envEntry.getEnvEntryType() != null) {
        out.println("    <env-entry-type>" + envEntry.getEnvEntryType()
            + "</env-entry-type>");
      }
      out.println("  </env-entry>");
    }

    for (WebAppEjbRef ejbref : webapp.getEjbRefs()) {
      out.println("  <ejb-ref>");
      if (ejbref.getDescription() != null) {
        out.println(
            "    <description>" + ejbref.getDescription() + "</description>");
      }
      if (ejbref.getEjbLink() != null) {
        out.println("    <ejb-link>" + ejbref.getEjbLink() + "</ejb-link>");
      }
      if (ejbref.getEjbRefName() != null) {
        out.println(
            "    <ejb-ref-name>" + ejbref.getEjbRefName() + "</ejb-ref-name>");
      }
      if (ejbref.getEjbRefType() != null) {
        out.println(
            "    <ejb-ref-type>" + ejbref.getEjbRefType() + "</ejb-ref-type>");
      }
      if (ejbref.getHome() != null) {
        out.println("    <home>" + ejbref.getHome() + "</home>");
      }
      if (ejbref.getRemote() != null) {
        out.println("    <remote>" + ejbref.getRemote() + "</remote>");
      }
      out.println("  </ejb-ref>");
    }
    for (WebAppEjbLocalRef elr : webapp.getEjbLocalRefs()) {
      out.println("  <ejb-local-ref>");
      if (elr.getDescription() != null) {
        out.println(
            "    <description>" + elr.getDescription() + "</description>");
      }
      if (elr.getEjbLink() != null) {
        out.println("    <ejb-link>" + elr.getEjbLink() + "</ejb-link>");
      }
      if (elr.getEjbRefName() != null) {
        out.println(
            "    <ejb-ref-name>" + elr.getEjbRefName() + "</ejb-ref-name>");
      }
      if (elr.getEjbRefType() != null) {
        out.println(
            "    <ejb-ref-type>" + elr.getEjbRefType() + "</ejb-ref-type>");
      }
      if (elr.getLocal() != null) {
        out.println("    <local>" + elr.getLocal() + "</local>");
      }
      if (elr.getLocalHome() != null) {
        out.println("    <local-home>" + elr.getLocalHome() + "</local-home>");
      }
      out.println("  </ejb-local-ref>");
    }
    out.println("</web-app>");
    out.flush();
  }

  private static void dumpFilterMappings(WebAppConfiguration webapp, PrintWriter out) {
    for (WebAppFilterMapping fmapping : webapp.getFilterMappings()) {
      out.println("  <filter-mapping>");
      if (fmapping.getFilterName() != null) {
        out.println(
            "    <filter-name>" + fmapping.getFilterName() + "</filter-name>");
      }
      if (fmapping.getUrlPatterns() != null) {
        for (String urlPattern : fmapping.getUrlPatterns()) {
          out.println(
              "    <url-pattern>" + urlPattern + "</url-pattern>");
        }
      }
      if (fmapping.getServletNames() != null) {
        for (String servletName : fmapping.getServletNames()) {
          out.println("    <servlet-name>" + servletName + "</servlet-name>");
        }
      }
      for (String dispatcher : fmapping.getDispatchers()) {
        out.println("    <dispatcher>" + dispatcher + "</dispatcher>");
      }
      out.println("  </filter-mapping>");
    }
  }

  private static void dumpServletDefinitions(WebAppConfiguration webapp, PrintWriter out) {
    for (WebAppServlet servlet : webapp.getServlets()) {
      out.println("  <servlet>");
      if (servlet.getIcon() != null) {
        out.println("    <icon>");
        if (servlet.getIcon().getSmallIcon() != null) {
          out.println("      <small-icon>" + servlet.getIcon().getSmallIcon()
              + "</small-icon>");
        }
        if (servlet.getIcon().getLargeIcon() != null) {
          out.println("      <large-icon>" + servlet.getIcon().getLargeIcon()
              + "</large-icon>");
        }
        out.println("    </icon>");
      }
      if (servlet.getDescription() != null) {
        out.println(
            "    <description>" + servlet.getDescription() + "</description>");
      }
      if (servlet.getDisplayName() != null) {
        out.println("    <display-name>" + servlet.getDisplayName()
            + "</display-name>");
      }
      if (servlet.getServletName() != null) {
        out.println("    <servlet-name>" + servlet.getServletName()
            + "</servlet-name>");
      }
      if (servlet.getServletClass() != null) {
        out.println("    <servlet-class>" + servlet.getServletClass()
            + "</servlet-class>");
      }
      if (servlet.getJspFile() != null) {
        out.println("    <jsp-file>" + servlet.getJspFile() + "</jsp-file>");
      }
      for (WebAppInitParam initparam : servlet.getInitParams()) {
        out.println("    <init-param>");
        if (initparam.getDescription() != null) {
          out.println("      <description>" + initparam.getDescription()
              + "</description>");
        }
        if (initparam.getParamName() != null) {
          out.println("      <param-name>" + initparam.getParamName()
              + "</param-name>");
        }
        if (initparam.getParamValue() != null) {
          out.println("      <param-value>" + initparam.getParamValue()
              + "</param-value>");
        }
        out.println("    </init-param>");
      }
      if (servlet.getLoadOnStartup() != null) {
        out.println("    <load-on-startup>" + servlet.getLoadOnStartup()
            + "</load-on-startup>");
      }
      if (servlet.getRunAs() != null) {
        out.println("    <run-as>");
        if (servlet.getRunAs().getRoleName() != null) {
          out.println("      <role-name>" + servlet.getRunAs().getRoleName()
              + "</role-name>");
        }
        if (servlet.getRunAs().getDescription() != null) {
          out.println("      <description>"
              + servlet.getRunAs().getDescription() + "</description>");
        }
        out.println("    </run-as>");
      }
      for (WebAppSecurityRoleRef roleRef : servlet.getSecurityRoleRefs()) {
        out.println("    <security-role-ref>");
        if (roleRef.getDescription() != null) {
          out.println("      <description>" + roleRef.getDescription()
              + "</description>");
        }
        if (roleRef.getRoleName() != null) {
          out.println(
              "      <role-name>" + roleRef.getRoleName() + "</role-name>");
        }
        if (roleRef.getRoleLink() != null) {
          out.println(
              "      <role-link>" + roleRef.getRoleLink() + "</role-link>");
        }
        out.println("    </security-role-ref>");
      }
      out.println("  </servlet>");
    }// for (servlets)
  }

  public static void dump(WebAppConfiguration webapp) {
    dump(webapp, new PrintWriter(System.out));
  }

}

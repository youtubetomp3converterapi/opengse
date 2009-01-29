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

package com.google.opengse.configuration.impl;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppEjbLocalRef;
import com.google.opengse.configuration.WebAppEjbRef;
import com.google.opengse.configuration.WebAppEnvEntry;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppIcon;
import com.google.opengse.configuration.WebAppListener;
import com.google.opengse.configuration.WebAppLoginConfig;
import com.google.opengse.configuration.WebAppMimeMapping;
import com.google.opengse.configuration.WebAppResourceEnvRef;
import com.google.opengse.configuration.WebAppResourceRef;
import com.google.opengse.configuration.WebAppSecurityConstraint;
import com.google.opengse.configuration.WebAppSecurityRole;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.configuration.WebAppSessionConfig;
import com.google.opengse.configuration.WebAppTagLib;
import com.google.opengse.configuration.WebAppWelcomeFileList;

import java.util.LinkedList;
import java.util.List;

/**
 * A mutable implementation of WebAppConfiguration.
 *
 * @author Mike Jennings
 */
public final class MutableWebAppConfiguration extends HasDescription
    implements WebAppConfiguration {

  private WebAppIcon icon;
  private String displayName;
  private boolean distributable;
  private final List<WebAppContextParam> contextParams
      = new LinkedList<WebAppContextParam>();
  private final List<WebAppFilter> filters = new LinkedList<WebAppFilter>();
  private final List<WebAppFilterMapping> filterMappings
      = new LinkedList<WebAppFilterMapping>();
  private final List<WebAppListener> listeners
      = new LinkedList<WebAppListener>();
  private final List<WebAppServlet> servlets = new LinkedList<WebAppServlet>();
  private final List<WebAppServletMapping> servletMappings
      = new LinkedList<WebAppServletMapping>();
  private WebAppSessionConfig sessionConfig;
  private final List<WebAppMimeMapping> mimeMappings
      = new LinkedList<WebAppMimeMapping>();
  private WebAppWelcomeFileList welcomeFiles;
  private final List<WebAppErrorPage> errorPages
      = new LinkedList<WebAppErrorPage>();
  private final List<WebAppTagLib> taglibs = new LinkedList<WebAppTagLib>();
  private final List<WebAppResourceEnvRef> envRefs
      = new LinkedList<WebAppResourceEnvRef>();
  private final List<WebAppResourceRef> resRefs
      = new LinkedList<WebAppResourceRef>();
  private final List<WebAppSecurityConstraint> securityConstraints
      = new LinkedList<WebAppSecurityConstraint>();
  private WebAppLoginConfig loginConfig;
  private final List<WebAppSecurityRole> securityRoles
      = new LinkedList<WebAppSecurityRole>();
  private final List<WebAppEnvEntry> envEntries
      = new LinkedList<WebAppEnvEntry>();
  private final List<WebAppEjbRef> ejbRefs
      = new LinkedList<WebAppEjbRef>();
  private final List<WebAppEjbLocalRef> localEjbRefs
      = new LinkedList<WebAppEjbLocalRef>();

  private MutableWebAppConfiguration() {
    super();
  }

  public static MutableWebAppConfiguration create() {
    return new MutableWebAppConfiguration();
  }

  public WebAppIcon getIcon() {
    return icon;
  }

  public void setIcon(final WebAppIcon icon) {
    this.icon = icon;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }


  public boolean isDistributable() {
    return distributable;
  }

  public void setDistributable(final String ignored) {
    distributable = true;
  }

  public WebAppContextParam[] getContextParams() {
    return contextParams.toArray(new WebAppContextParam[contextParams.size()]);
  }

  public void addContextParam(final WebAppContextParam param) {
    contextParams.add(param);
  }

  public WebAppFilter[] getFilters() {
    return filters.toArray(new WebAppFilter[filters.size()]);
  }

  public void addFilter(final WebAppFilter filter) {
    filters.add(filter);
  }

  public WebAppFilterMapping[] getFilterMappings() {
    return filterMappings
        .toArray(new WebAppFilterMapping[filterMappings.size()]);
  }

  public void addFilterMapping(final WebAppFilterMapping filterMapping) {
    filterMappings.add(filterMapping);
  }

  public WebAppListener[] getListeners() {
    return listeners.toArray(new WebAppListener[listeners.size()]);
  }

  public void addListener(final WebAppListener listener) {
    listeners.add(listener);
  }

  public WebAppServlet[] getServlets() {
    return servlets.toArray(new WebAppServlet[servlets.size()]);
  }

  public void addServlet(final WebAppServlet servlet) {
    servlets.add(servlet);
  }

  public WebAppServletMapping[] getServletMappings() {
    return servletMappings
        .toArray(new WebAppServletMapping[servletMappings.size()]);
  }

  public void addServletMapping(final WebAppServletMapping mapping) {
    servletMappings.add(mapping);
  }

  public WebAppSessionConfig getSessionConfig() {
    return sessionConfig;
  }

  public void setSessionConfig(final WebAppSessionConfig sessionConfig) {
    this.sessionConfig = sessionConfig;
  }

  public WebAppMimeMapping[] getMimeMappings() {
    return mimeMappings.toArray(new WebAppMimeMapping[mimeMappings.size()]);
  }

  public void addMimeMapping(final WebAppMimeMapping mapping) {
    mimeMappings.add(mapping);
  }

  public WebAppWelcomeFileList getWelcomeFileList() {
    return welcomeFiles;
  }

  public void setWelcomeFileList(final WebAppWelcomeFileList welcomeFileList) {
    this.welcomeFiles = welcomeFileList;
  }

  public String[] getWelcomeFiles() {
    return new String[0];
  }

  public WebAppErrorPage[] getErrorPages() {
    return errorPages.toArray(new WebAppErrorPage[errorPages.size()]);
  }

  public void addErrorPage(final WebAppErrorPage errorPage) {
    errorPages.add(errorPage);
  }

  public WebAppTagLib[] getTagLibs() {
    return taglibs.toArray(new WebAppTagLib[taglibs.size()]);
  }

  public void addTagLib(final WebAppTagLib taglib) {
    taglibs.add(taglib);
  }

  public WebAppResourceEnvRef[] getResourceEnvRefs() {
    return envRefs.toArray(new WebAppResourceEnvRef[envRefs.size()]);
  }

  public void addResourceEnvRef(final WebAppResourceEnvRef ref) {
    envRefs.add(ref);
  }

  public WebAppResourceRef[] getResourceRefs() {
    return resRefs.toArray(new WebAppResourceRef[resRefs.size()]);
  }

  public void addResourceRef(final WebAppResourceRef ref) {
    resRefs.add(ref);
  }

  public WebAppSecurityConstraint[] getSecurityConstraints() {
    return securityConstraints
        .toArray(new WebAppSecurityConstraint[securityConstraints.size()]);
  }

  public void addSecurityConstraint(
      final WebAppSecurityConstraint securityConstraint) {
    securityConstraints.add(securityConstraint);
  }

  public WebAppLoginConfig getLoginConfig() {
    return loginConfig;
  }

  public void setLoginConfig(final WebAppLoginConfig loginConfig) {
    this.loginConfig = loginConfig;
  }

  public WebAppSecurityRole[] getSecurityRoles() {
    return securityRoles.toArray(new WebAppSecurityRole[securityRoles.size()]);
  }

  public void addSecurityRole(final WebAppSecurityRole securityRole) {
    securityRoles.add(securityRole);
  }

  public WebAppEnvEntry[] getEnvEntries() {
    return envEntries.toArray(new WebAppEnvEntry[envEntries.size()]);
  }

  public void addEnvEntry(final WebAppEnvEntry envEntry) {
    envEntries.add(envEntry);
  }

  public WebAppEjbRef[] getEjbRefs() {
    return ejbRefs.toArray(new WebAppEjbRef[ejbRefs.size()]);
  }

  public void addEjbRef(final WebAppEjbRef ref) {
    ejbRefs.add(ref);
  }

  public WebAppEjbLocalRef[] getEjbLocalRefs() {
    return localEjbRefs.toArray(new WebAppEjbLocalRef[localEjbRefs.size()]);
  }

  public void addEjbLocalRef(final WebAppEjbLocalRef localRef) {
    localEjbRefs.add(localRef);
  }
}

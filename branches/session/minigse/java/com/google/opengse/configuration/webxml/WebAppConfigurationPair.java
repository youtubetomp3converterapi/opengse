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

package com.google.opengse.configuration.webxml;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppIcon;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppListener;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.configuration.WebAppSessionConfig;
import com.google.opengse.configuration.WebAppMimeMapping;
import com.google.opengse.configuration.WebAppWelcomeFileList;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppTagLib;
import com.google.opengse.configuration.WebAppResourceEnvRef;
import com.google.opengse.configuration.WebAppResourceRef;
import com.google.opengse.configuration.WebAppSecurityConstraint;
import com.google.opengse.configuration.WebAppLoginConfig;
import com.google.opengse.configuration.WebAppSecurityRole;
import com.google.opengse.configuration.WebAppEnvEntry;
import com.google.opengse.configuration.WebAppEjbRef;
import com.google.opengse.configuration.WebAppEjbLocalRef;
import static com.google.opengse.configuration.webxml.WebAppConfigurationCombiner.*;

/**
 * Represents a pair of WebAppConfiguration objects logically combined.
 * Uses functions in WebAppConfigurationCombiner to combine the various
 * elements of WebAppConfiguration.
 *
 * @author Mike Jennings
 */
final class WebAppConfigurationPair implements WebAppConfiguration {
  private WebAppIcon icon;
  private String displayName;
  private String description;
  private boolean distributable;
  private WebAppContextParam[] contextParams;
  private WebAppFilter[] filters;
  private WebAppFilterMapping[] filterMappings;
  private WebAppListener[] listeners;
  private WebAppServlet[] servlets;
  private WebAppServletMapping[] servletMappings;
  private WebAppMimeMapping[] mimeMappings;
  private WebAppErrorPage[] errorPages;
  private WebAppTagLib[] tagLibs;
  private WebAppResourceEnvRef[] resourceEnvRefs;
  private WebAppResourceRef[] resourceRefs;
  private WebAppSecurityConstraint[] securityConstraints;
  private WebAppSecurityRole[] securityRoles;
  private WebAppEnvEntry[] envEntries;
  private WebAppEjbRef[] ejbRefs;
  private WebAppEjbLocalRef[] ejbLocalRefs;
  private WebAppLoginConfig loginConfig;
  private WebAppSessionConfig sessionConfig;
  private WebAppWelcomeFileList welcomeFileList;

  private WebAppConfigurationPair(WebAppConfiguration parent, WebAppConfiguration child) {
    if (parent == null) {
      throw new NullPointerException("null parent parameter!");
    }
    if (child == null) {
      throw new NullPointerException("null child parameter!");
    }
    // use the child's icon if it is non-null, otherwise use the parent's icon
    icon = combine(parent.getIcon(), child.getIcon());
    // use the child's display name if it is non-null, otherwise use the parent's
    displayName = combineDisplayName(parent.getDisplayName(), child.getDisplayName());
    // use the child's description if it is non-null, otherwise use the parent's
    description = combineDescription(parent.getDescription(), child.getDescription());
    contextParams = combine(parent.getContextParams(), child.getContextParams());
    filters = combine(parent.getFilters(), child.getFilters());
    filterMappings = combine(parent.getFilterMappings(), child.getFilterMappings());
    listeners = combine(parent.getListeners(), child.getListeners());
    servlets = combine(parent.getServlets(), child.getServlets());
    servletMappings = combine(parent.getServletMappings(), child.getServletMappings());
    mimeMappings = combine(parent.getMimeMappings(), child.getMimeMappings());
    errorPages = combine(parent.getErrorPages(), child.getErrorPages());
    tagLibs = combine(parent.getTagLibs(), child.getTagLibs());
    resourceEnvRefs = combine(parent.getResourceEnvRefs(), child.getResourceEnvRefs());
    resourceRefs = combine(parent.getResourceRefs(), child.getResourceRefs());
    securityConstraints = combine(parent.getSecurityConstraints(), child.getSecurityConstraints());
    securityRoles = combine(parent.getSecurityRoles(), child.getSecurityRoles());
    envEntries = combine(parent.getEnvEntries(), child.getEnvEntries());
    ejbRefs = combine(parent.getEjbRefs(), child.getEjbRefs());
    ejbLocalRefs = combine(parent.getEjbLocalRefs(), child.getEjbLocalRefs());
    loginConfig = combine(parent.getLoginConfig(), child.getLoginConfig());
    sessionConfig = combine(parent.getSessionConfig(), child.getSessionConfig());
    welcomeFileList = combine(parent.getWelcomeFileList(), child.getWelcomeFileList());
    distributable = child.isDistributable();
  }

  static WebAppConfiguration create(WebAppConfiguration parent, WebAppConfiguration child) {
    return new WebAppConfigurationPair(parent, child);
  }

  public WebAppIcon getIcon() {
    return icon;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public boolean isDistributable() {
    return distributable;
  }

  public WebAppContextParam[] getContextParams() {
    return contextParams;
  }

  public WebAppFilter[] getFilters() {
    return filters;
  }

  public WebAppFilterMapping[] getFilterMappings() {
    return filterMappings;
  }

  public WebAppListener[] getListeners() {
    return listeners;
  }

  public WebAppServlet[] getServlets() {
    return servlets;
  }

  public WebAppServletMapping[] getServletMappings() {
    return servletMappings;
  }

  public WebAppMimeMapping[] getMimeMappings() {
    return mimeMappings;
  }

  public WebAppErrorPage[] getErrorPages() {
    return errorPages;
  }

  public WebAppTagLib[] getTagLibs() {
    return tagLibs;
  }

  public WebAppResourceEnvRef[] getResourceEnvRefs() {
    return resourceEnvRefs;
  }

  public WebAppResourceRef[] getResourceRefs() {
    return resourceRefs;
  }

  public WebAppSecurityConstraint[] getSecurityConstraints() {
    return securityConstraints;
  }

  public WebAppSecurityRole[] getSecurityRoles() {
    return securityRoles;
  }

  public WebAppEnvEntry[] getEnvEntries() {
    return envEntries;
  }

  public WebAppEjbRef[] getEjbRefs() {
    return ejbRefs;
  }

  public WebAppEjbLocalRef[] getEjbLocalRefs() {
    return ejbLocalRefs;
  }

  public WebAppLoginConfig getLoginConfig() {
    return loginConfig;
  }

  public WebAppSessionConfig getSessionConfig() {
    return sessionConfig;
  }

  public WebAppWelcomeFileList getWelcomeFileList() {
    return welcomeFileList;
  }
  
    
}

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

package com.google.opengse.configuration;

/**
 * Configuration for a webapp. Essentially models
 * http://java.sun.com/dtd/web-app_2_3.dtd
 *
 * @author Mike Jennings
 */
public interface WebAppConfiguration {
  WebAppIcon getIcon();
  String getDisplayName();
  String getDescription();
  boolean isDistributable();
  WebAppContextParam[] getContextParams();
  WebAppFilter[] getFilters();
  WebAppFilterMapping[] getFilterMappings();
  WebAppListener[] getListeners();
  WebAppServlet[] getServlets();
  WebAppServletMapping[] getServletMappings();
  WebAppSessionConfig getSessionConfig();
  WebAppMimeMapping[] getMimeMappings();
  WebAppWelcomeFileList getWelcomeFileList();
  WebAppErrorPage[] getErrorPages();
  WebAppTagLib[] getTagLibs();
  WebAppResourceEnvRef[] getResourceEnvRefs();
  WebAppResourceRef[] getResourceRefs();
  WebAppSecurityConstraint[] getSecurityConstraints();
  WebAppLoginConfig getLoginConfig();
  WebAppSecurityRole[] getSecurityRoles();
  WebAppEnvEntry[] getEnvEntries();
  WebAppEjbRef[] getEjbRefs();
  WebAppEjbLocalRef[] getEjbLocalRefs();
}

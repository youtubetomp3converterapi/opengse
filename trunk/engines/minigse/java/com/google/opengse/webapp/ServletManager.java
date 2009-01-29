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

package com.google.opengse.webapp;

import com.google.opengse.ServletUtils;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppInitParam;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.filters.RegularExpressionRequestHandler;
import com.google.opengse.filters.RegularExpressionRequestHandlerDispatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages servlets and filters.
 *
 * @author Mike Jennings
 */
public final class ServletManager {

  private static final Logger LOGGER = Logger
      .getLogger(ServletManager.class.getName());

  private final Map<String, RequestHandlerFactory> servletNameToInfo;
  private final Map<String, List<WebAppServletMapping>> servletNameToMappings;
  private final Map<String, FilterInfo> filterNameToInfo;
  private final Map<UriKey, List<RequestHandlerFactory>> uriPatternToServlets;
  private final Map<UriKey, List<FilterInfo>> uriPatternToRequestFilters;
  private final Map<UriKey, List<FilterInfo>> uriPatternToForwardFilters;
  private final Map<UriKey, List<FilterInfo>> uriPatternToIncludeFilters;
  private final Map<UriKey, List<FilterInfo>> uriPatternToErrorFilters;
  private final ErrorPageManager errorManager;

  /**
   * Orders in which filters are defined (in web.xml) are singificant and
   * must be preserved.
   */
  public ServletManager() {
    servletNameToInfo = new HashMap<String, RequestHandlerFactory>();
    filterNameToInfo = new LinkedHashMap<String, FilterInfo>();
    uriPatternToServlets = new HashMap<UriKey, List<RequestHandlerFactory>>();
    uriPatternToRequestFilters = new HashMap<UriKey, List<FilterInfo>>();
    uriPatternToForwardFilters = new HashMap<UriKey, List<FilterInfo>>();
    uriPatternToIncludeFilters = new HashMap<UriKey, List<FilterInfo>>();
    uriPatternToErrorFilters = new HashMap<UriKey, List<FilterInfo>>();
    errorManager = new ErrorPageManager();
    servletNameToMappings = new HashMap<String, List<WebAppServletMapping>>();
  }

  void addErrorPage(WebAppErrorPage errorPage) {
    errorManager.addErrorPage(errorPage);
  }

  void addServlet(WebAppServlet servletDef, Servlet servlet) {
    servletNameToInfo.put(servletDef.getServletName(),
        new ServletRequestHandlerFactory(errorManager, servletDef, servlet));
  }

  void addFilter(WebAppFilter filterDef, Filter filter) {
    filterNameToInfo.put(filterDef.getFilterName(),
        new FilterInfo(filterDef, filter));
  }

  void combineFilterAndServletMappings(
      RegularExpressionRequestHandlerDispatcher regexHandler)
      throws WebAppConfigurationException {
    createPlaceholderServletsIfNecessary();
    combineFilterAndServletMappings(
        regexHandler.getRequestHandler(), uriPatternToRequestFilters);
    combineFilterAndServletMappings(
        regexHandler.getForwardHandler(), uriPatternToForwardFilters);
    combineFilterAndServletMappings(
        regexHandler.getIncludeHandler(), uriPatternToIncludeFilters);
    combineFilterAndServletMappings(
        regexHandler.getErrorHandler(), uriPatternToErrorFilters);
  }

  void combineFilterAndServletMappings(
      RegularExpressionRequestHandler regexHandler,
      Map<UriKey, List<FilterInfo>> uriPatternToFilters)
      throws WebAppConfigurationException {
    createPlaceholderServletsIfNecessary();
    for (UriKey uriPattern : uriPatternToServlets.keySet()) {
      List<RequestHandlerFactory> servlets
          = uriPatternToServlets.get(uriPattern);
      // if more than one servlet is mapped to a given pattern
      // we take the first and ignore the rest.
      RequestHandlerFactory firstMatchingServlet = servlets.iterator().next();
      FilterChain handler = firstMatchingServlet.getRequestHandler();
      if (handler != null) {
        List<FilterInfo> filterDefs
            = getMatchingFilters(uriPattern, uriPatternToFilters);
        if (filterDefs != null) {
          Filter[] filters = new Filter[filterDefs.size()];
          int i = 0;
          for (FilterInfo filterDef : filterDefs) {
            filters[i++] = filterDef.getFilter();
          }
          // wrap the servlet handler in filters
          FilterChain chain = ServletUtils.createChain(handler, filters);
          handler = chain;
        }

        if (uriPattern.isGlobalPattern()) {
          regexHandler.replaceDefaultHandler(handler);
        } else {
          String regex = uriPattern.toRegex();
          if (!regexHandler.setHandler(regex, handler)) {
            // throw an exception if we are replacing a non-global pattern
            throw new WebAppConfigurationException(
                "Pattern '" + uriPattern + "' already registered");
          }
        }
      }
    }
  }

  /**
   * Get all of the filters which match the given uri pattern.
   *
   * TODO: we need revisit this:
   * 1) distinguish direct-uripattern-mapping from servlet-uripattern-mapping
   * to avoid over-matching.
   * E.g. /a/* => servlet-a => filter-a;  /a/b => servlet-b
   *     now for /a/b, we will return filter-a, which is wrong
   * E.g. /a/* => filter-a;  /a/b=>servlet-b
   *     /a/b => filter-a .. that's all right.
   * 2) we may have duplicated filters for the same pattern:
   * E.g. /* => servlet-a;  * (servlet) => filter; we will have /* => filter
   *     /b => servlet-b;  * (servlet) => filter; we will have /b => filter
   *     Now for /b: we will match /* and /b ... and have two same "filter"s
   *
   * @return matched filters for the specified uriPattern.
   */
  private List<FilterInfo> getMatchingFilters(
      UriKey uriPattern, Map<UriKey, List<FilterInfo>> uriPatternToFilters) {
    List<FilterInfo> matchedFilters = new ArrayList<FilterInfo>();
    for (UriKey uri : uriPatternToFilters.keySet()) {
      if (uriPattern.equals(uri) || uriPattern.isSubsetOf(uri)) {
        matchedFilters.addAll(uriPatternToFilters.get(uri));
      }
    }
    return matchedFilters;
  }


  void addServletMapping(WebAppServletMapping mapping)
      throws WebAppConfigurationException {
    String servletName = mapping.getServletName();
    if (servletName == null) {
      throw new WebAppConfigurationException(
          "No servlet-name in servlet-mapping");
    }
    RequestHandlerFactory info = servletNameToInfo.get(servletName);
    if (info == null) {
      LOGGER.info("Could not find servlet-name '" + servletName + "'");
    } else {
      List<WebAppServletMapping> mappings = getMappingsForServlet(servletName);
      mappings.add(mapping);
      addServletMapping(new UriKey(mapping.getUrlPattern()), info);
    }
  }

  private List<WebAppServletMapping> getMappingsForServlet(String servletName) {
    List<WebAppServletMapping> mappings
        = servletNameToMappings.get(servletName);
    if (mappings == null) {
      mappings = new ArrayList<WebAppServletMapping>();
      servletNameToMappings.put(servletName, mappings);
    }
    return mappings;
  }

  private List<WebAppServletMapping> getMappingsForAllServlets() {
    List<WebAppServletMapping> allMappings
        = new ArrayList<WebAppServletMapping>();
    for (List<WebAppServletMapping> servletMappings
        : servletNameToMappings.values()) {
      allMappings.addAll(servletMappings);
    }
    return allMappings;
  }

  void addFilterMapping(WebAppFilterMapping mapping)
      throws WebAppConfigurationException {
    String filterName = mapping.getFilterName();
    if (filterName == null) {
      throw new WebAppConfigurationException(
          "No filter-name in filter-mapping");
    }
    if (mapping.getServletNames() == null && mapping.getUrlPatterns() == null) {
      throw new WebAppConfigurationException(
          "No servlet-name or url-pattern element");
    }
    FilterInfo info = filterNameToInfo.get(filterName);
    if (info == null) {
      LOGGER.severe("Error: Could not find filter-name '" + filterName + "'");
      return;
    }
    if (!info.initialized) {      // should never happen
      LOGGER.severe("Error: Filter not initialized '" + filterName + "'");
      return;
    }

    if (mapping.getServletNames() != null) {
      for (String servletName : mapping.getServletNames()) {
        if (servletName.equals("*")) {
          addFilterMappingForAllServlets(info, mapping);
        } else if (!servletNameToInfo.containsKey(servletName)) {
          throw new WebAppConfigurationException(
              "Unknown servlet-name '" + servletName + "'");
        } else {
          addFilterMappingForOneServlet(info, servletName, mapping);
        }
      }
    }

    if (mapping.getUrlPatterns() != null) {
      for (String urlPattern : mapping.getUrlPatterns()) {
          addFilterMapping(info, urlPattern, mapping);
      }
    }
  }

  private void addFilterMappingForOneServlet(
      FilterInfo info, String servletName, WebAppFilterMapping filterMapping) {
    List<WebAppServletMapping> mappings = getMappingsForServlet(servletName);
    for (WebAppServletMapping mapping : mappings) {
      addFilterMapping(info, mapping.getUrlPattern(), filterMapping);
    }
  }

  private void addFilterMappingForAllServlets(
      FilterInfo info, WebAppFilterMapping filterMapping) {
    List<WebAppServletMapping> mappings = getMappingsForAllServlets();
    for (WebAppServletMapping mapping : mappings) {
      addFilterMapping(info, mapping.getUrlPattern(), filterMapping);
    }
  }

  private boolean isForType(WebAppFilterMapping filterMapping, String type) {
    if (type == null) {
      // this should never happen
      throw new NullPointerException();
    }
    String[] dispatchers = filterMapping.getDispatchers();
    if (dispatchers == null || dispatchers.length == 0) {
      // if no dispatchers are specified, REQUEST type defaults to true
      return "REQUEST".equals(type);   // default to "REQUEST"
    }
    for (String dispatcher : dispatchers) {
      if (dispatcher != null && dispatcher.equals(type)) {
        return true;
      }
    }
    return false; // did not find the specified type
  }

  private void addFilterMapping(
      FilterInfo info, String urlPattern, WebAppFilterMapping filterMapping) {
    UriKey uriPattern = new UriKey(urlPattern);
    if (isForType(filterMapping, "REQUEST")) {
      List<FilterInfo> filters = uriPatternToRequestFilters.get(uriPattern);
      if (filters == null) {
        filters = new ArrayList<FilterInfo>();
        uriPatternToRequestFilters.put(uriPattern, filters);
      }
      filters.add(info);
    }

    if (isForType(filterMapping, "FORWARD")) {
      List<FilterInfo> filters = uriPatternToForwardFilters.get(uriPattern);
      if (filters == null) {
        filters = new ArrayList<FilterInfo>();
        uriPatternToForwardFilters.put(uriPattern, filters);
      }
      filters.add(info);
    }

    if (isForType(filterMapping, "INCLUDE")) {
      List<FilterInfo> filters = uriPatternToIncludeFilters.get(uriPattern);
      if (filters == null) {
        filters = new ArrayList<FilterInfo>();
        uriPatternToIncludeFilters.put(uriPattern, filters);
      }
      filters.add(info);
    }

    if (isForType(filterMapping, "ERROR")) {
      List<FilterInfo> filters = uriPatternToErrorFilters.get(uriPattern);
      if (filters == null) {
        filters = new ArrayList<FilterInfo>();
        uriPatternToErrorFilters.put(uriPattern, filters);
      }
      filters.add(info);
    }
  }


  private void addServletMapping(
      UriKey uriPattern, RequestHandlerFactory info) {
    List<RequestHandlerFactory> servlets = uriPatternToServlets.get(uriPattern);
    if (servlets == null) {
      servlets = new ArrayList<RequestHandlerFactory>();
      uriPatternToServlets.put(uriPattern, servlets);
    }
    servlets.add(info);
  }

  String getFirstUrlPatternForServletName(String servletName) {
    List<WebAppServletMapping> mappings = getMappingsForServlet(servletName);
    if (mappings.isEmpty()) {
      return null;
    }
    return mappings.iterator().next().getUrlPattern();
  }

  private void createPlaceholderServletsIfNecessary() {
    // make a copy of all of the filter uri patterns
    Set<UriKey> filterPatternsWithoutServlets = new HashSet<UriKey>(
        uriPatternToRequestFilters.keySet());
    filterPatternsWithoutServlets.addAll(uriPatternToForwardFilters.keySet());
    filterPatternsWithoutServlets.addAll(uriPatternToIncludeFilters.keySet());
    filterPatternsWithoutServlets.addAll(uriPatternToErrorFilters.keySet());

    // subtract all of the servlet uri patterns
    filterPatternsWithoutServlets.removeAll(uriPatternToServlets.keySet());
    ServletRequestHandlerFactory notFound = new ServletRequestHandlerFactory(
            errorManager, null, new NotFoundServlet());
    // the NotFoundServlet doesn't need to be initialized
    notFound.initialized = true;
    for (UriKey uriPattern : filterPatternsWithoutServlets) {
      addServletMapping(uriPattern, notFound);
    }
  }


  void initializeServlets(ServletContext context) {
    for (RequestHandlerFactory info : servletNameToInfo.values()) {
      info.initialize(context);
    }
  }

  void initializeFilters(ServletContext context) {
    for (FilterInfo info : filterNameToInfo.values()) {
      info.initialize(context);
    }
  }

  static ServletConfig createServletConfig(ServletContext context,
      WebAppServlet servletDef) {
    Map<String, String> initValues = new HashMap<String, String>();
    if (servletDef.getInitParams() != null) {
      for (WebAppInitParam param : servletDef.getInitParams()) {
        initValues.put(param.getParamName(), param.getParamValue());
      }
    }
    return new ServletOrFilterConfig(context, servletDef.getServletName(),
        initValues);
  }

  static FilterConfig createFilterConfig(ServletContext context,
      WebAppFilter filterDef) {
    Map<String, String> initValues = new HashMap<String, String>();
    if (filterDef.getInitParams() != null) {
      for (WebAppInitParam param : filterDef.getInitParams()) {
        initValues.put(param.getParamName(), param.getParamValue());
      }
    }
    return new ServletOrFilterConfig(context, filterDef.getFilterName(),
        initValues);
  }


  private static class NotFoundServlet extends HttpServlet {

    private static final long serialVersionUID = 3L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response)
        throws ServletException, IOException {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }


  /**
   * This class is used as a key class when managing Uri Patterns. It ensures
   * that any Uri Pattern used as a Key will always have a leading "/", thereby
   * accepting patterns that were specified without one.
   *
   * @author Michael Guntsch
   */
  private static class UriKey {

    private String key;

    private UriKey(String rawKey) {
      super();
      if (rawKey.startsWith("/")) {
        key = rawKey;
      } else {
        key = "/" + rawKey;
      }
    }

    /**
     * Returns true if this UriKey is a subset of the other UriKey.
     *
     * In other words, everything matched by this UriKey will also be matched
     * by the other UriKey
     */
    public boolean isSubsetOf(UriKey other) {
      if (other.isGlobalPattern()) {
        return true; // we are always the subset of the global pattern
      }
      if (this.isGlobalPattern()) {
        return false; // global pattern cannot be a subset of non-global pattern
      }
      // now we do naive subset checking...
      if (!other.hasWildcard()) {
        return false;
      }

      // other has a wildcard, this does not
      if (!other.key.endsWith("*")) {
        return false; // can only handle cases where other pattern ends with *
      }

      String thisKey = key;
      if (this.hasWildcard()) {  // strip out the wildcard before matching
        thisKey = key.substring(0, key.indexOf("*"));
      }

      int star = other.key.indexOf('*');
      String prefix = other.key.substring(0, star);
      return thisKey.startsWith(prefix);
    }

    boolean hasWildcard() {
      return (key.indexOf('*') != -1);
    }

    boolean isGlobalPattern() {
      return (key.equals("/*"));
    }

    public String toRegex() {
      if (isGlobalPattern()) {
        return ".*";
      }
      StringBuilder sb = new StringBuilder();
      for (char ch : key.toCharArray()) {
        switch (ch) {
          case '*':
            sb.append(".*");
            break;
          case '.':
            sb.append("\\.");
            break;
          default:
            sb.append(ch);
        }
      }
      return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof UriKey) {
        UriKey other = (UriKey) obj;
        return key.equals(other.key);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return key.hashCode();
    }

    @Override
    public String toString() {
      return key;
    }
  }

}

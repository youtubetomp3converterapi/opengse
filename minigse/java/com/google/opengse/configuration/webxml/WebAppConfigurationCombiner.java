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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility methods for logically combining WebAppConfiguration-related objects
 *
 * @author Mike Jennings
 */
public final class WebAppConfigurationCombiner {

  private WebAppConfigurationCombiner() {
  }

  /**
   * Logically combine two WebAppConfiguration objects into one WebAppConfiguration object.
   */
  public static WebAppConfiguration combine(WebAppConfiguration parent, WebAppConfiguration child) {
    return WebAppConfigurationPair.create(parent, child);
  }

  static WebAppIcon combine(WebAppIcon parent, WebAppIcon child) {
    // use the child's icon if it is non-null, otherwise use the parent's icon
    return (child == null) ? parent : child;
  }

  static String combineDisplayName(String parent, String child) {
    // use the child's display name if it is non-null, otherwise use the parent's
    return (child == null) ? parent : child;
  }

  static String combineDescription(String parent, String child) {
    // use the child's description if it is non-null, otherwise use the parent's
    return (child == null) ? parent : child;
  }

  static WebAppListener[] combine(WebAppListener[] parent, WebAppListener[] child) {
    // combine the listeners without duplicating the classes
    Set<String> listenerClasses = new HashSet<String>();
    List<WebAppListener> listeners = new ArrayList<WebAppListener>();
    for (WebAppListener listener : parent) {
      if (listenerClasses.add(listener.getListenerClass())) {
        listeners.add(listener);
      }
    }
    for (WebAppListener listener : child) {
      if (listenerClasses.add(listener.getListenerClass())) {
        listeners.add(listener);
      }
    }
    WebAppListener[] arr = new WebAppListener[listeners.size()];
    listeners.toArray(arr);
    return arr;
  }


  static WebAppContextParam[] combine(WebAppContextParam[] parent, WebAppContextParam[] child) {
    SetList<WebAppContextParam> setlist = new SetList<WebAppContextParam>();
    for (WebAppContextParam cp : parent) {
      setlist.add(cp.getParamName(), cp);
    }
    for (WebAppContextParam cp : child) {
      setlist.add(cp.getParamName(), cp);
    }
    return setlist.toArray(new WebAppContextParam[setlist.size()]);
  }

  static WebAppFilter[] combine(WebAppFilter[] parent, WebAppFilter[] child) {
    SetList<WebAppFilter> setlist = new SetList<WebAppFilter>();
    for (WebAppFilter cp : parent) {
      setlist.add(cp.getFilterName(), cp);
    }
    for (WebAppFilter cp : child) {
      setlist.add(cp.getFilterName(), cp);
    }
    return setlist.toArray(new WebAppFilter[setlist.size()]);
  }

  static WebAppServlet[] combine(WebAppServlet[] parent, WebAppServlet[] child) {
    SetList<WebAppServlet> setlist = new SetList<WebAppServlet>();
    for (WebAppServlet cp : parent) {
      setlist.add(cp.getServletName(), cp);
    }
    for (WebAppServlet cp : child) {
      setlist.add(cp.getServletName(), cp);
    }
    return setlist.toArray(new WebAppServlet[setlist.size()]);
  }

  static WebAppFilterMapping[] combine(WebAppFilterMapping[] parent, WebAppFilterMapping[] child) {
    SetList<WebAppFilterMapping> setlist = new SetList<WebAppFilterMapping>();
    for (WebAppFilterMapping cp : parent) {
//      setlist.add(toOrderedString(cp.getUrlPatterns()), cp);
      setlist.add(cp.getFilterName(), cp);
    }
    for (WebAppFilterMapping cp : child) {
      setlist.add(cp.getFilterName(), cp);
//      setlist.add(toOrderedString(cp.getUrlPatterns()), cp);
    }
    return setlist.toArray(new WebAppFilterMapping[setlist.size()]);
  }

  private static String toOrderedString(String[] array) {
    if (array.length == 0) {
      return "";
    }
    Arrays.sort(array);
    StringBuilder sb = new StringBuilder(array[0]);
    for (int i = 1; i < array.length; ++i) {
      sb.append(";").append(array[i]);
    }
    return sb.toString();
  }

  static WebAppServletMapping[] combine(WebAppServletMapping[] parent,
      WebAppServletMapping[] child) {
    SetList<WebAppServletMapping> setlist = new SetList<WebAppServletMapping>();
    for (WebAppServletMapping cp : parent) {
      setlist.add(cp.getUrlPattern(), cp);
    }
    for (WebAppServletMapping cp : child) {
      setlist.add(cp.getUrlPattern(), cp);
    }
    return setlist.toArray(new WebAppServletMapping[setlist.size()]);
  }

  static WebAppMimeMapping[] combine(WebAppMimeMapping[] parent, WebAppMimeMapping[] child) {
    SetList<WebAppMimeMapping> setlist = new SetList<WebAppMimeMapping>();
    for (WebAppMimeMapping cp : parent) {
      setlist.add(cp.getExtension(), cp);
    }
    for (WebAppMimeMapping cp : child) {
      setlist.add(cp.getExtension(), cp);
    }
    return setlist.toArray(new WebAppMimeMapping[setlist.size()]);
  }

  private static class WebAppWelcomeFileListImpl implements WebAppWelcomeFileList {

    private String[] files;

    private WebAppWelcomeFileListImpl(String[] _files) {
      files = _files;
    }

    public String[] getWelcomeFiles() {
      return files;
    }
  }

  static WebAppWelcomeFileList combine(WebAppWelcomeFileList parent, WebAppWelcomeFileList child) {
    SetList<String> setlist = new SetList<String>();
    if (parent != null) {
      for (String welcomeFile : parent.getWelcomeFiles()) {
        setlist.add(welcomeFile, welcomeFile);
      }
    }
    if (child != null) {
      for (String welcomeFile : child.getWelcomeFiles()) {
        setlist.add(welcomeFile, welcomeFile);
      }
    }
    return new WebAppWelcomeFileListImpl(setlist.toArray(new String[setlist.size()]));
  }

  private static String toString(WebAppErrorPage epage) {
    if (epage.getErrorCode() == null && epage.getExceptionType() == null) {
      return "";
    }
    if (epage.getExceptionType() == null) {
      return epage.getErrorCode().toString() + ".";
    }
    if (epage.getErrorCode() == null) {
      return "." + epage.getExceptionType();
    }
    return epage.getErrorCode().toString() + epage.getExceptionType();
  }

  static WebAppErrorPage[] combine(WebAppErrorPage[] parent, WebAppErrorPage[] child) {
    SetList<WebAppErrorPage> setlist = new SetList<WebAppErrorPage>();
    for (WebAppErrorPage ep : parent) {
      setlist.add(toString(ep), ep);
    }
    for (WebAppErrorPage ep : child) {
      setlist.add(toString(ep), ep);
    }
    return setlist.toArray(new WebAppErrorPage[setlist.size()]);
  }

  static WebAppTagLib[] combine(WebAppTagLib[] parent, WebAppTagLib[] child) {
    SetList<WebAppTagLib> setlist = new SetList<WebAppTagLib>();
    for (WebAppTagLib tl : parent) {
      setlist.add(tl.getTaglibUri(), tl);
    }
    for (WebAppTagLib tl : child) {
      setlist.add(tl.getTaglibUri(), tl);
    }
    return setlist.toArray(new WebAppTagLib[setlist.size()]);
  }

  static WebAppResourceEnvRef[] combine(WebAppResourceEnvRef[] parent,
      WebAppResourceEnvRef[] child) {
    return new WebAppResourceEnvRef[0];
  }

  static WebAppResourceRef[] combine(WebAppResourceRef[] parent, WebAppResourceRef[] child) {
    return new WebAppResourceRef[0];
  }

  static WebAppSecurityConstraint[] combine(WebAppSecurityConstraint[] parent,
      WebAppSecurityConstraint[] child) {
    return new WebAppSecurityConstraint[0];
  }

  static WebAppSecurityRole[] combine(WebAppSecurityRole[] parent, WebAppSecurityRole[] child) {
    return new WebAppSecurityRole[0];
  }

  static WebAppEnvEntry[] combine(WebAppEnvEntry[] parent, WebAppEnvEntry[] child) {
    return new WebAppEnvEntry[0];
  }

  static WebAppEjbRef[] combine(WebAppEjbRef[] parent, WebAppEjbRef[] child) {
    return new WebAppEjbRef[0];
  }

  static WebAppEjbLocalRef[] combine(WebAppEjbLocalRef[] parent, WebAppEjbLocalRef[] child) {
    return new WebAppEjbLocalRef[0];
  }

  static WebAppLoginConfig combine(WebAppLoginConfig parent, WebAppLoginConfig child) {
    return null;
  }

  static WebAppSessionConfig combine(WebAppSessionConfig parent, WebAppSessionConfig child) {
    return (child == null) ? parent : child;
  }

}

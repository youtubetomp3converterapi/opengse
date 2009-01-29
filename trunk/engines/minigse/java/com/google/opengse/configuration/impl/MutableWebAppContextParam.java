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

import com.google.opengse.configuration.WebAppContextParam;

/**
 * A mutable implementation of WebAppContextParam.
*
* @author Mike Jennings
*/
public final class MutableWebAppContextParam extends HasDescription implements
    WebAppContextParam {
  private String paramName;
  private String paramValue;

  private MutableWebAppContextParam() {
    super();
  }

  public static MutableWebAppContextParam create() {
    return new MutableWebAppContextParam();
  }

  public String getParamName() {
    return paramName;
  }

  public void setParamName(final String paramName) {
    this.paramName = paramName;
  }

  public String getParamValue() {
    return paramValue;
  }

  public void setParamValue(final String paramValue) {
    this.paramValue = paramValue;
  }

}

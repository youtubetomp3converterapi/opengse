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

import com.google.opengse.configuration.WebAppErrorPage;

/**
 * A mutable implementation of WebAppErrorPage.
*
* @author Mike Jennings
*/
public final class MutableWebAppErrorPage implements WebAppErrorPage {
  private Integer errorCode;
  private String exceptionType, location;

  private MutableWebAppErrorPage() {
  }

  public static MutableWebAppErrorPage create() {
    return new MutableWebAppErrorPage();
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String ecode) {
    errorCode = Integer.valueOf(ecode);
  }

  public String getExceptionType() {
    return exceptionType;
  }

  public void setExceptionType(String exceptionType) {
    this.exceptionType = exceptionType;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

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

package com.google.opengse.core;

import com.google.opengse.ServletEngineFactory;
import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineConfiguration;

import java.io.IOException;

import javax.servlet.FilterChain;

/**
 * A factory for servlet engines
 *
 * @author Mike Jennings
 */
public class ServletEngineFactoryImpl implements ServletEngineFactory {

  public ServletEngine createServletEngine(
      FilterChain dispatcher, ServletEngineConfiguration config)
      throws IOException, InterruptedException {
    return ServletEngineImpl.create(dispatcher, config);
  }
}

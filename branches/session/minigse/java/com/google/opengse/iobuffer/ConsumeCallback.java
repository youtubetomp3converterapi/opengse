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

package com.google.opengse.iobuffer;

import java.io.IOException;

/**
 * Callback interface to handle the contents of an IOBuffer on
 * a call to the buffer's consume method. This mechanism can
 * be used to dispense with the bytes in an IOBuffer in an
 * application-dependent fashion. For example, sending the
 * bytes to a network interface, a file, or processing them.
 *
 * @see com.google.opengse.iobuffer.IOBuffer
 * @see com.google.opengse.HttpResponse
 * @author Spencer Kimball
 */
public interface ConsumeCallback {
  /**
   * Called when the IOBuffer object's consume method is invoked.
   *
   * @param iobuffer is the calling IOBuffer
   * @param done <code>true</code> if this is the last data to consume
   * @exception IOException is thrown on a consume failure
   */
  void handleConsume(IOBuffer iobuffer, boolean done) throws IOException;
}
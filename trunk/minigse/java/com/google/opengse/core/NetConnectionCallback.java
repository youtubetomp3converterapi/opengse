// Copyright 2002 Google Inc.
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

import java.io.IOException;

/**
 * Callback interface for network operations (read, write, close).
 *
 * @see NetConnection
 * @author Peter Mattis
 */
public interface NetConnectionCallback {
  /**
   * Called from <code>NetConnection</code> when a connect event occurs
   * on the <code>NetConnection</code> this callback is associated
   * with.
   *
   * @param conn
   */
  public void handleConnect(NetConnection conn) throws IOException;

  /**
   * Called from <code>NetConnection</code> when a read event occurs
   * on the <code>NetConnection</code> this callback is associated
   * with.
   *
   * @param conn
   */
  public void handleRead(NetConnection conn) throws IOException;

  /**
   * Called from <code>NetConnection</code> when a write event occurs
   * on the <code>NetConnection</code> this callback is associated
   * with.
   *
   * @param conn
   */
  public void handleWrite(NetConnection conn) throws IOException;

  /**
   * Called from <code>NetConnection</code> when the connection is
   * closed. Close events are normally indicated by reading -1 bytes
   * from the connection. However, a NetConnection may get closed for
   * other reasons, such as handleRead or handleWrite throwing an
   * exception. This callback provides a hook for notification of the
   * connection being closed for any reason.
   *
   * @param conn
   */
  public void handleClose(NetConnection conn);

  /**
   * Returns an HTML formatted string containing available status
   * information. For example, an HttpConnection might implement this
   * method by returning the URI, request type, request size, remote
   * IP address and thread stack trace (if applicable)...
   *
   * @return HTML formatted status string
   */
  public String getHtmlStatus(NetConnection conn);
}
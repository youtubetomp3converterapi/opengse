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

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.WebAppWelcomeFileList;
import com.google.opengse.configuration.webxml.WebAppConfigurationFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * A basic file-serving servlet. This servlet gets invoked when nothing else
 * handles an incoming request.
 *
 * @author Mike Jennings
 */
public class FileServingServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private WebAppConfiguration config;
  private String[] welcomeFiles;

  @Override
  public void init() throws ServletException {
    super.init();

    WebAppConfiguration config;
    WebAppWelcomeFileList welcomeFileList;
    String contextdir = this.getServletContext().getRealPath("/");
    try {
      config = WebAppConfigurationFactory.getConfiguration(new File(contextdir));
      welcomeFileList = config.getWelcomeFileList();
    } catch (WebAppConfigurationException e) {
      // Errors on getting configuration from web.xml
      welcomeFileList = null;
    }
    if (welcomeFileList != null) {
      welcomeFiles = welcomeFileList.getWelcomeFiles();
      for (int i = 0; i < welcomeFiles.length; ++i) {
        welcomeFiles[i] = welcomeFiles[i].trim();
      }
    }
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    URL resource = getResourceURLFromRequest(req);
    if (resource == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    URLConnection urlConn = null;
    String file = resource.getPath();
    int lastSlash = file.lastIndexOf('/');
    if (lastSlash != -1) {
      file = file.substring(lastSlash + 1);
    }
    if (file.equals("")) {
      String resourceAsString = resource.toString();
      for (String welcomeFile : welcomeFiles) {
        URL resourceToTry = new URL(resourceAsString + welcomeFile);
        urlConn = resourceToTry.openConnection();
        if (urlConn != null && urlConn.getContentLength() > 0) {
          file = welcomeFile;
          break;
        }
      }
    }
    String mimeType = getServletContext().getMimeType(file);
    if (urlConn == null) {
      urlConn = resource.openConnection();
    }
    int length = urlConn.getContentLength();
    if (mimeType == null) {
      // fall back to using the JRE to figure out the mime-type
      mimeType = urlConn.getContentType();
      if (mimeType == null) {
        /*
         * If the jvm didn't know the mime type and it was not registered in
         * any web.xml, send an error, cuz it's the only thing we can do.
         */
        resp.sendError(HttpServletResponse.SC_NOT_FOUND,
            "Unknown mime type for '" + file + "'");
        return;
      }
    }
    urlConn.connect();
    InputStream istr = urlConn.getInputStream();
    if (istr == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND,
          "Cannot read from '" + resource + "'");
      return;
    }
    OutputStream ostr = null;
    try {
      ostr = resp.getOutputStream();
    } catch (IOException e) {
      // ignore if stream is not available, not supported, or just bad ...
    } catch (IllegalStateException e) {
      // ignore if stream is not available, not supported, or just bad ...
    }
    try {
      if (ostr != null) {
        writeFileContent(resp, mimeType, length, istr, ostr);
      } else {
        /*
         * Bad output stream, and we fall back to PrintWriter.
         * Note: Jasper runtime only supports PrintWriter.
         */
        PrintWriter printWriter = resp.getWriter();
        if (printWriter != null) {
          resp.setContentType(mimeType);
          resp.setContentLength(length);
          ostr = new ByteArrayOutputStream();
          writeFileContent(resp, mimeType, length, istr, ostr);

          // TODO: locale?
          printWriter.write((((ByteArrayOutputStream) ostr).toString()));
        }  // else - we are done: bad input
      }
    } finally {
      istr.close();
      if (ostr != null) {
        ostr.close();
      }
    }
  }

  private void writeFileContent(HttpServletResponse resp, String mimeType,
                                int length, InputStream istr, OutputStream ostr)
      throws IOException {
    resp.setContentType(mimeType);
    resp.setContentLength(length);
    try {
      copy(istr, ostr, length);
      istr.close();
      istr = null;
      ostr.close();
      ostr = null;
    } finally {
      if (istr != null) {
        istr.close();
      }
    }
  }

  /**
   * Copies up to maxBytes from istr to ostr
   * @param istr
   * @param out
   * @param maxBytes
   * @return the total number of bytes copied
   * @throws IOException
   */
  private static int copy(
      final InputStream istr, final OutputStream out, final int maxBytes)
      throws IOException {
    byte[] buf = new byte[512];
    int bytesRead = 0;
    int totalBytesRead = 0;
    while (bytesRead != -1 && totalBytesRead < maxBytes) {
      bytesRead = istr.read(buf, 0, buf.length);
      if (bytesRead > 0) {
        totalBytesRead += bytesRead;
        out.write(buf, 0, bytesRead);
      }
    }
    return totalBytesRead;
  }


  private URL getResourceURLFromRequest(final HttpServletRequest req)
      throws MalformedURLException {
    String uri = req.getRequestURI();
    String contextPath = req.getContextPath();
    String resource = uri.substring(contextPath.length());
    if (resource.startsWith("/WEB-INF/")) {
      // make sure we don't serve things from the WEB-INF directory
      return null;
    }
    return this.getServletContext().getResource(resource);
  }

}

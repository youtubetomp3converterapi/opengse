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

package com.google.opengse.performance;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

/**
 * Client: the primary actor in any simulation. A client can set
 * its attributes such as link speed, time between requests,
 * and request payload. The client can take action upon receiving
 * each response.
 *
 * <p>Although this class extends {@link Comparable}, its
 * {@link #compareTo(Client)} method is inconsistent with equals.
 *
 * @author Spencer Kimball
 */
public abstract class Client implements Comparable<Client> {
  /**
   * Random: specifically disallow different seeds for
   * repeatability.
   */
  protected static final Random RANDOM = new Random(0);

  /**
   * Client characteristics
   */
  protected String name_;
  protected double speed_;   // link speed
  protected double time_;    // time between requests
  protected double latency_; // server processing latency
  protected double size_;    // request size

  /**
   * Client statistics
   */
  protected long bytes_;
  protected long wait_;
  protected int requests_;
  protected int successes_;
  protected int failures_;

  /**
   * Operating vars
   */
  protected IOBuffer req_;
  protected IOBuffer resp_;
  protected StringBuffer header_;
  protected long start_;
  protected long start_write_;
  protected long last_write_;
  protected boolean keep_alive_;
  protected int content_length_;
  protected int bytes_read_;
  protected String status_;
  protected boolean chunked_;
  protected boolean read_header_;
  protected SocketChannel connected_;
  protected long next_; // next request time (in ms)

  /**
   * Constructor.
   */
  public Client(String name) {
    this.name_ = name;
    this.speed_   = Math.max(9.6, RANDOM.nextGaussian () *
                             Simulator.FLAG_speedstddev +
                             Simulator.FLAG_speedmean);
    this.time_    = Math.max(0, RANDOM.nextGaussian () *
                             Simulator.FLAG_timestddev +
                             Simulator.FLAG_timemean);
    this.size_    = 0.0;
    this.latency_ = 0.0;

    this.bytes_ = 0;
    this.wait_ = 0;
    this.requests_ = 0;
    this.successes_ = 0;
    this.failures_ = 0;

    this.req_ = new IOBuffer();
    this.resp_ = new IOBuffer();

    this.header_ = new StringBuffer();
    this.start_ = 0;
    this.connected_ = null;
    this.next_ = System.currentTimeMillis() +
                 (long) (Math.random() * Simulator.FLAG_timemean);
  }

  /**
   * Set the request
   */
  public abstract void setRequest();

  /**
   * Handles the response from the server
   */
  public abstract void handleResponse();

  public int compareTo(Client c) {
    return (int) (next_ - c.next_);
  }

  @Override
  public String toString() {
    return name_ + " [speed(Kbs):" + (int) speed_ + ", time(ms):" + (int) time_
        + ", latency(ms):" + (int) latency_
        + ", size(Kb):" + (int) (size_ / 1024) + "]";
  }

  public boolean writeRequest(SocketChannel channel) throws IOException {
    long now = System.currentTimeMillis();

    if (start_ == 0) {
      start_ = System.currentTimeMillis();
      requests_ += 1;
      start_write_ = now;
      last_write_ = now;
      size_ = req_.availableBytes();
    }

    // compute the maximum bytes we can write up to this point
    int max = (int) ((speed_ * 1024 / 8) * ((now - last_write_) / 1000.0));

    // write
    for (;;) {
      ByteBuffer buf = req_.getReadBuffer();

      if (buf == null) {
        if (Simulator.FLAG_verbose) {
          System.out.println("wrote " + (int) (size_ / 1024) + " Kb in " +
                             (now - start_write_) + " ms: " + this);
        }
        return true;
      } else if (max == 0) {
        return false;
      }

      int oldLimit = buf.limit();
      buf.limit(Math.min(buf.position() + max, oldLimit));
      int written = channel.write(buf);
      buf.limit(oldLimit);
      last_write_ = System.currentTimeMillis();

      if (written == 0 || max == 0) {
        return false;
      } else {
        bytes_ += written;
        max -= written;
      }
    }
  }

  public boolean readResponse(SocketChannel channel) throws IOException {
    // first time at read. Mark the wait time here
    if (start_ != 0) {
      // - subtract off time spent transmitting data, as this is
      // not attributable to the server's delays
      // - subtract off latency, as this is also not delay attributed
      // to the server for our purposes of comparing servlet engines
      long wait = (System.currentTimeMillis() - start_);
      long xfer = (long) (1000 * (size_ / (speed_ * 1024 / 8)));
      wait_ += (wait - xfer);
      start_ = 0;
      read_header_ = false;
      header_ = new StringBuffer();
      bytes_read_ = 0;
      resp_.clear();
    }

    // read
    for (;;) {
      ByteBuffer buf = resp_.getWriteBuffer();
      int position = buf.position();
      int read = channel.read(buf);
      bytes_read_ += read;

      // if we haven't processed the header, keep appending the
      // bytes to the header string buffer
      if (read_header_ == false) {
        for (int i = 0; i < read; i++) {
          byte[] byteArray = buf.array();
          char c = (char) byteArray[buf.arrayOffset() + position + i];
          // if last two characters are '\n', we've read the header
          String last3 = "";
          try {
            last3 = header_.substring(header_.length() - 3, header_.length());
          } catch (StringIndexOutOfBoundsException e) { /* ignored */ }
          if (c == '\n' && last3.equals("\r\n\r")) {
            String header = header_.toString().toLowerCase();
            read_header_ = true;
            // attempt to get status
            try {
              status_ = header.substring(9, 12);
              if ("200".equals(status_)) {
                successes_ += 1;
              } else if ("302".equals(status_)) {
                // Normally we count 302 (redirect) responses as failures, but
                // derived classes can override this behavior.
                String locationHeader = extractLocationHeader(header);
                if (acceptRedirect(locationHeader)) {
                  successes_ += 1;
                } else {
                  System.out.println("failure, unexpected redirect: " + header);
                  failures_ += 1;
                }
              } else {
                System.out.println("failure, status: " + header);
                failures_ += 1;
              }
            } catch (RuntimeException e) {
              System.out.println("exception: " + e.getMessage());
              failures_ += 1;
            }
            // attempt to determine keep alive
            keep_alive_ = (header.indexOf("connection: keep-alive") != -1);
            // attempt to determine content length
            String str = new String("content-length: ");
            int index = header.indexOf(str);
            if (index != -1) {
              index += str.length();
              try {
                int end = header.indexOf("\n", index);
                content_length_ =
                  Integer.parseInt(header.substring(index, end).trim());
              } catch (RuntimeException e) {
                System.out.println("exception: " + e.getMessage());
                keep_alive_ = false;
                content_length_ = -1;
              }
            }

            header_.append(c);
            bytes_read_ -= header_.length();
            header_.delete(0, header_.length());
            break;
          } else {
            header_.append(c);
          }
        }
      }

      if (read == 0) {
        return false;
      } else if (read == -1) {
        keep_alive_ = false;
        next_ = System.currentTimeMillis() + (long) time_;
        return true;
      } else if (keep_alive_ == true && bytes_read_ >= content_length_) {
        next_ = System.currentTimeMillis() + (long) time_;
        return true;
      }
    }
  }

  public void clientError(String msg) {
    System.err.println(this + ": " + msg); // + ", request:\n" + req_);
    failures_ += 1;
    start_ = 0;
    next_ = System.currentTimeMillis() + (long) time_;
  }

  /**
   * Tell whether to accept a response with the given redirect URI.  The default
   * implementation rejects any redirect status (only accepts 200 responses as
   * success).
   * @param redirectURI the value of the Location header in the response.
   * @return <code>true</code> if this redirect should be accepted as success.
   */
  protected boolean acceptRedirect(String redirectURI) {
    return false;
  }

  /**
   * Extract the Location header, which specifies the URI on a redirect (302)
   * response.
   * @param headers the full set of headers from the response.  Assumed to be
   *     lowercase.
   * @return the value of the location header, or the empty string if none can
   *     be found.
   */
  private static String extractLocationHeader(String headers) {
    int index = headers.indexOf("\r\nlocation");
    if (index != -1) {
      String locationHeader = headers.substring(index + 12);
      index = locationHeader.indexOf("\r\n");
      if (index != -1) {
        locationHeader = locationHeader.substring(0, index);
        return locationHeader;
      }
    }
    return "";
  }
}

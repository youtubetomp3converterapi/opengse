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
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * Simulator: simulates pseudo-realistic traffic by creating
 * clients with differing characteristics such as link speed
 * and request interval.
 * <p/>
 * Approximate link speed metrics:
 * <p/>
 * Dial-up modem: 28.8 to 56Kbps
 * ISDN: 64Kbps (1 B-channel) or 128Kbps (2 B-channels)
 * Satellite: 700Kbps
 * DSL: 256Kbps to 1.5Mbps (1500Kbps)
 * Cable Modem: 1.5 to 3Mbps (1500 to 3000 Kbps)
 * T1: 1.544Mbps (1544Kbps) for a point-to-point dedicated line
 * T2: 6.312Mbps (6312Kbps) for a point-to-point dedicated line
 * T3: 44.736Mbps (44073Kbps) for a point-to-point dedicated, digital circuit
 *
 * @author Spencer Kimball
 */
public abstract class Simulator {

  private static final Logger LOGGER = Logger.getLogger(Simulator.class.getName());

  /**
   * The server hostname
   */
  public static String FLAG_server = "localhost";
  public static String HELP_server =
      "server hostname";

  /**
   * The server port
   */
  public static int FLAG_port = 8082;
  public static String HELP_port =
      "server port";

  /**
   * The simulation period (seconds)
   */
  public static int FLAG_simperiod = 20;
  public static String HELP_simperiod =
      "simulation period (s)";

  /**
   * The number of clients
   */
  public static int FLAG_numclients = 500;
  public static String HELP_numclients =
      "number of clients";

  /**
   * Mean link speed
   */
  public static int FLAG_speedmean = 128;
  public static String HELP_speedmean =
      "mean link speed (Kbps)";

  /**
   * Standard deviation link speed
   */
  public static int FLAG_speedstddev = 64;
  public static String HELP_speedstddev =
      "standard deviation in link speed (Kbps)";

  /**
   * Mean time between requests
   */
  public static int FLAG_timemean = 10000;
  public static String HELP_timemean =
      "mean time between successive requests (ms)";

  /**
   * Standard deviation time between requests
   */
  public static int FLAG_timestddev = 2500;
  public static String HELP_timestddev =
      "standard deviation in time between requests (ms)";

  /**
   * Enable keep alive
   */
  public static boolean FLAG_keepalive = false;
  public static String HELP_keepalive =
      "enable keep alive HTTP connections";

  /**
   * Verbose output
   */
  public static boolean FLAG_verbose = false;
  public static String HELP_verbose =
      "include verbose output";

  /**
   * The authoritative list of clients
   */
  protected ArrayList<Client> list_ = new ArrayList<Client>();


  /**
   * Return a new client
   */
  public abstract Client newClient(String name);

  /**
   * Print the setup info
   */
  public void outputSetup() {

    report("Client characteristics\t[avg/stddev]");
    report("----------------------");
    report("Number of clients:\t" + FLAG_numclients);
    report("Simulation period:\t" + FLAG_simperiod + " s");
    report("Link speed (Kbps):\t" +
        FLAG_speedmean + "/" + FLAG_speedstddev);
    report("Request interval (ms):\t" +
        FLAG_timemean + "/" + FLAG_timestddev);
  }

  /**
   * Print the output info
   */
  public void outputResults() {
    int successes = 0;
    int failures = 0;
    long wait = 0;
    for (Client c : list_) {
      successes += c.successes_;
      failures += c.failures_;
      wait += c.wait_;
    }

    if ((successes + failures) == 0) {
      report("");
      report("No results...");
      System.exit(1);
    }

    report("");
    report("Results:");
    report("--------");
    report("Successes:\t" +
        successes + " (" +
        ((successes * 100) / (successes + failures)) + "%)");
    report("Failures:\t" +
        failures + " (" +
        ((failures * 100) / (successes + failures)) + "%)");

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(2);
    report(
        "Average wait:\t" + nf.format((double) wait / successes) + " ms");
    report(
        "Requests/sec:\t" + nf.format((double) successes / FLAG_simperiod));
  }

  /**
   * Run the simulation
   */
  public void run() throws Exception {
    outputSetup();

    // the server address
    InetSocketAddress addr = new InetSocketAddress(FLAG_server, FLAG_port);

    // a sorted set for holding the clients
    TreeSet<Client> set = new TreeSet<Client>();
    for (int i = 0; i < FLAG_numclients; i++) {
      Client c = newClient("client #" + i);
      set.add(c);
      list_.add(c);
    }

    // get a selector
    Selector selector = Selector.open();

    // loop until the simulation period is over
    long start = System.currentTimeMillis();
    while ((System.currentTimeMillis() - start) / 1000 <= FLAG_simperiod) {
      Client client;

      // open connections for clients who are ready
      while (set.size() > 0 && (client = set.first()) != null) {
        if (client.next_ > System.currentTimeMillis()) {
          break;
        } else {
          set.remove(client);
        }

        // open a new socket if the client is not yet connected
        if (client.connected_ == null) {
          SocketChannel sc = null;
          try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.socket().setTcpNoDelay(true);
            sc.connect(addr);
            sc.register(selector, SelectionKey.OP_CONNECT, client);
            client.connected_ = sc;
          } catch (SocketException se) {
            set.add(client);
            LOGGER.warning("unable to send new request: " +
                se.getMessage() + ", " +
                (list_.size() - set.size()) +
                " outstanding requests");
            if (sc != null) {
              sc.close();
            }
            break;
          }
        } else {
          // re-interest in writing the next request
          client.connected_.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
        }
      }

      // handle selectable I/O
      for (Iterator<SelectionKey> i = selector.selectedKeys().iterator();
           i.hasNext();) {
        SelectionKey key = i.next();
        SocketChannel channel = (SocketChannel) key.channel();
        i.remove();

        if (key.isValid()) {
          client = (Client) key.attachment();
          try {
            if (key.isConnectable()) {
              if (channel.finishConnect()) {
                //System.out.println("connected: " + client);
                client.setRequest();
                key.interestOps(SelectionKey.OP_WRITE);
              }
            }
            if (key.isWritable()) {
              if (client.writeRequest(channel)) {
                //System.out.println("request written: " + client.name_);
                key.interestOps(SelectionKey.OP_READ);
              }
            }
            if (key.isReadable()) {
              if (client.readResponse(channel)) {
                //System.out.println("response read: " + client.name_);
                client.resp_.flush();
                client.handleResponse();
                if (FLAG_keepalive && client.keep_alive_) {
                  client.setRequest();
                  key.interestOps(0);
                } else {
                  key.interestOps(0);
                  channel.close();
                  client.connected_ = null;
                }
                set.add(client);
              }
            }
          } catch (IOException e) {
            client.clientError(e.getMessage());
            set.add(client);
            //e.printStackTrace();
            try {
              channel.close();
              client.connected_ = null;
            } catch (IOException x) {
              x.printStackTrace();
            }
          }
        }
      }

      // select until the next client is ready
      if (set.size() > 0) {
        client = set.first();
        long delay = Math.max(0, client.next_ - System.currentTimeMillis());
        if (delay > 0) {
          selector.select(delay);
        }
      } else {
        selector.select(10);
      }
    }

    outputResults();
  }

  private void report(String text) {
    System.out.println(text);
  }
}

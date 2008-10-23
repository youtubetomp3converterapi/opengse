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

/**
 * LoadSimulator:
 *
 * Clients are simulated using the following attributes:
 * - random request payload
 * - random response latency
 *
 * The {@link WaitServlet} servlet is used to approximate
 * request latency. In the real world, this would correspond
 * to a database access or other I/O-bound operation common
 * to servlet processing.
 *
 * @author Spencer Kimball
 */
public class LoadSimulator extends Simulator {
  /**
   * Mean request size (size of request in KB)
   */
  public static int FLAG_SIZE_MEAN = 20;

  /**
   * Standard deviation request size
   */
  public static int FLAG_SIZE_STD_DEV = 10;

  /**
   * Mean response latency (time for server to process response)
   */
  public static int FLAG_LATENCY_MEAN = 50;

  /**
   * Standard deviation response latency
   */
  public static int FLAG_LATENCY_STD_DEV = 40;

  /**
   * Return a new client of type LoadClient
   */
  @Override
  public Client newClient(String name) {
    return new LoadClient(name);
  }

  /**
   * Output the setup information
   */
  @Override
  public void outputSetup() {
    super.outputSetup();
    System.out.println("Request size (KB):\t" +
                       FLAG_SIZE_MEAN + "/" + FLAG_SIZE_STD_DEV);
    System.out.println("Response latency (ms):\t" +
                       FLAG_LATENCY_MEAN + "/" + FLAG_LATENCY_STD_DEV);
  }

  /**
   * Output results
   */
  @Override
  public void outputResults() {
    super.outputResults();
  }

  public static void main(String[] args) throws Exception {
    // parse command line arguments using the Flags object

    LoadSimulator sim = new LoadSimulator();
    sim.run();
    System.exit(0);
  }
}

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

import com.google.opengse.configuration.WebAppUserDataConstraint;

public class MutableWebAppUserDataConstraint extends HasDescription implements WebAppUserDataConstraint {
  private String transportGuarantee;

  private MutableWebAppUserDataConstraint() {
  }

  public static MutableWebAppUserDataConstraint create() {
    return new MutableWebAppUserDataConstraint();
  }

  public String getTransportGuarantee() {
    return transportGuarantee;
  }

  public void setTransportGuarantee(String transportGuarantee) {
    this.transportGuarantee = transportGuarantee;
  }
}

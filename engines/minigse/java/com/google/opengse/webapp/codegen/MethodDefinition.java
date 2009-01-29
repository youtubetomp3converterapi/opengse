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

package com.google.opengse.webapp.codegen;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * @author jennings
 *         Date: Jun 22, 2008
 */
public final class MethodDefinition {
  private boolean isStatic;
  private boolean isPublic;
  private String returnType;
  private final String methodName;
  private final List<String> lines;

  public MethodDefinition(String methodName) {
    this.methodName = methodName;
    isPublic = true;
    returnType = "void";
    lines = new ArrayList<String>();
  }

  public void addLine(String line) {
    lines.add(line);
  }

  public void write(PrintWriter out) {
    out.print("  ");
    if (isPublic) {
      out.print("public ");
    }
    if (isStatic) {
      out.print("static ");
    }
    out.print(returnType + " " + methodName + "(");
    out.print(") ");
    out.println("{");
    for (String line : lines) {
      out.println("    " + line);
    }
    out.println("  }");
  }

  public void setStatic(boolean aStatic) {
    isStatic = aStatic;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public void setReturnType(Class<?> clazz) {
    setReturnType(clazz.getSimpleName());
  }

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
  }
}

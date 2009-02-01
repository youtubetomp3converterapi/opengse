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
import java.util.Iterator;

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
  private List<String> throwsClauses;
  private List<String> args;

  public MethodDefinition(String methodName) {
    this.methodName = methodName;
    isPublic = true;
    returnType = "void";
    lines = new ArrayList<String>();
    throwsClauses = new ArrayList<String>();
    args = new ArrayList<String>();
  }

  public MethodDefinition addLine(String line) {
    lines.add(line);
    return this;
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
    printArgs(out);
    out.print(") ");
    printThrowsClauses(out);
    out.println("{");
    for (String line : lines) {
      out.println("    " + line);
    }
    out.println("  }");
  }

  private void printThrowsClauses(PrintWriter out) {
    if (throwsClauses.isEmpty()) {
      return;
    }
    Iterator<String> iter = throwsClauses.iterator();
    out.print("throws " + iter.next());
    while(iter.hasNext()) {
      out.print(", " + iter.next());
    }
    out.print(" ");
  }

  private void printArgs(PrintWriter out) {
    if (args.isEmpty()) {
      return;
    }
    Iterator<String> iter = args.iterator();
    out.print(iter.next());
    while(iter.hasNext()) {
      out.print(", " + iter.next());
    }
  }

  public MethodDefinition addArg(String type, String name) {
    args.add(type + " " + name);
    return this;
  }

  public MethodDefinition addArg(Class<?> type, String name) {
    return addArg(type.getSimpleName(), name);
  }

  public MethodDefinition setStatic(boolean aStatic) {
    isStatic = aStatic;
    return this;
  }

  public MethodDefinition setReturnType(String returnType) {
    this.returnType = returnType;
    return this;
  }

  public MethodDefinition setReturnType(Class<?> clazz) {
    return setReturnType(clazz.getSimpleName());
  }

  public MethodDefinition addThrowsClause(Class<?> clazz) {
    return addThrowsClause(clazz.getSimpleName());
  }

  public MethodDefinition addThrowsClause(String exceptionType) {
    throwsClauses.add(exceptionType);
    return this;
  }

  public MethodDefinition setPublic(boolean aPublic) {
    isPublic = aPublic;
    return this;
  }
}

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
import java.util.ArrayList;
import java.util.List;

/**
 * @author jennings Date: Jun 22, 2008
 */
public final class ClassDefinition {

  private String comment;
  private String superClass;
  private final String classname;
  private final String packagename;
  private final List<String> imports;
  private final List<MethodDefinition> methods;

  public ClassDefinition(String packagename, String classname) {
    this.packagename = packagename;
    this.classname = classname;
    imports = new ArrayList<String>();
    methods = new ArrayList<MethodDefinition>();
  }

  public void addImport(String imp) {
    imports.add(imp);
  }

  public void addImport(Class<?> clazz) {
    imports.add(clazz.getName());
  }

  public void addMethod(MethodDefinition md) {
    methods.add(md);
  }

  public void setSuperClass(String superClass) {
    this.superClass = superClass;
  }

  public void setSuperClass(Class<?> clazz) {
    setSuperClass(clazz.getSimpleName());
  }

  public void write(PrintWriter out) {
    if (comment != null) {
      out.println("// " + comment);
    }
    out.println("package " + packagename + ";");
    out.println();
    writeImports(out);
    out.println();
    out.print("public class " + classname);
    if (superClass != null) {
      out.printf(" extends " + superClass);
    }
    out.println(" {");

    out.println();
    writeMethods(out);
    out.println("}");
  }

  private void writeImports(PrintWriter out) {
    for (String imp : imports) {
      out.println("import " + imp + ";");
    }
  }

  private void writeMethods(PrintWriter out) {
    for (MethodDefinition md : methods) {
      md.write(out);
    }
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}

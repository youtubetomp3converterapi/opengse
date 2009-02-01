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
/**
 *
 * @author jennings
 * Date: Nov 19, 2008
 */
package com.google.opengse.webapp.war;

import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.webapp.WebAppConfigurationBuilder;
import com.google.opengse.webapp.codegen.ClassDefinition;
import com.google.opengse.webapp.codegen.MethodDefinition;
import com.google.opengse.configuration.webxml.WebXmlDump;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.Properties;
import java.io.*;

/**
 * This class generates webapp skeletons.
 *
 */
class SkeletonMaker {
  private static final String EXAMPLE_CODE_PACKAGE = "com.google.opengse.examples";
  private static final String EXAMPLE_CODE_CLASS = "SomeServlet";

  static void createWebApp(Properties props) throws IOException {
    File webapp = PropertiesUtil.getFile(props, "dir");
    if (webapp == null) {
      System.err.println("Need to supply a --dir parameter");
      return;
    }
    String swebapp = PropertiesUtil.getAliasedProperty(props, "dir", null);
    if (swebapp != null && swebapp.equals("true")) {
      System.err.println("Sorry, can't create a webapp in a directory named 'true'");
      return;
    }

    File parent = webapp;
    webapp = new File(parent, "web");
    if (!parent.exists()) {
      parent.mkdirs();
    }
    if (!parent.isDirectory()) {
      System.err.println("'" + parent + "' is not a directory");
      return;
    }
    if (!parent.canWrite()) {
      System.err.println("Cannot write to directory '" + parent + "'");
      return;
    }
    String context = PropertiesUtil.getAliasedProperty(props, "context", null);
    if (context == null) {
      System.err.println("Need a --context parameter");
      return;
    }
    if (webapp.exists()) {
      System.err.println(webapp + " already exists. Please delete it.");
      return;
    }
    File buildxmlfile = new File(parent, "build.xml");
    if (buildxmlfile.exists()) {
      System.err.println(buildxmlfile + " already exists. Please delete it.");
      return;
    }

    webapp.mkdirs();
    File webinf = new File(webapp, "WEB-INF");
    webinf.mkdirs();
    if (!webinf.exists()) {
      System.err.println("Can't create '" + webinf +"' for some reason.");
      return;
    }
    File classes = new File(webinf, "classes");
    classes.mkdirs();
    if (!classes.exists()) {
      System.err.println("Can't create '" + classes + "' for some reason.");
      return;
    }
    File javadir = new File(webinf, "java");
    javadir.mkdirs();
    if (!javadir.exists()) {
      System.err.println("Can't create '" + javadir + "' for some reason.");
      return;
    }

    ClassDefinition classdef = new ClassDefinition(EXAMPLE_CODE_PACKAGE, EXAMPLE_CODE_CLASS);
    classdef.setSuperClass(HttpServlet.class);
    classdef.addImport(HttpServlet.class);
    classdef.addImport(HttpServletRequest.class);
    classdef.addImport(HttpServletResponse.class);
    classdef.addImport(ServletException.class);
    classdef.addImport(IOException.class);
    MethodDefinition method = new MethodDefinition("service");
    method.setPublic(true);
    method.addThrowsClause(ServletException.class).addThrowsClause(IOException.class);
    method.addArg(HttpServletRequest.class, "request");
    method.addArg(HttpServletResponse.class, "response");
    method.addLine("response.setContentType(\"text/plain\");");
    method.addLine("response.getWriter().println(\"Hello World!\");");
    classdef.addMethod(method);

    File javafile = new File(javadir
        , EXAMPLE_CODE_PACKAGE.replace('.', File.separatorChar) + File.separator + EXAMPLE_CODE_CLASS + ".java");
    javafile.getParentFile().mkdirs();
    PrintWriter java = new PrintWriter(javafile);
    try {
      classdef.write(java);
    } finally {
      java.close();
    }
    
    WebAppConfigurationBuilder wxmlb = new WebAppConfigurationBuilder();
    wxmlb.addContextParam("global.foo1", "global.bar1");
    wxmlb.addContextParam("global.foo2", "global.bar2");
    wxmlb.unsafe_addServlet("myservlet", EXAMPLE_CODE_PACKAGE + "." + EXAMPLE_CODE_CLASS, "*.cgi", "chocolate", "good");
    File webxmlfile = new File(webinf, "web.xml");
    PrintWriter webxml = new PrintWriter(webxmlfile);
    try {
      WebXmlDump.dump(wxmlb.getConfiguration(), webxml);
    } finally {
      webxml.close();
    }
    System.err.println("Created '" + webxmlfile + "'");
    File propsfile = new File(parent, context + ".properties");
    Properties wprops = new Properties();
    wprops.setProperty("webapp." + context, "${basedir}/web-exploded");
    wprops.setProperty("port", PropertiesUtil.getAliasedProperty(props, "port", "8080"));
    OutputStream os = new FileOutputStream(propsfile);
    try {
      wprops.store(os, "auto-generated by OpenGSE");
    } finally {
      os.close();
    }
    System.err.println("Created " + propsfile);


    PrintWriter buildxml = new PrintWriter(buildxmlfile);
    try {
      buildAntFile(buildxml, context, webinf);
    } finally {
      buildxml.close();
    }
    System.err.println("Use ant -f /path/to/" + buildxmlfile.getName() + " to build");
    System.err.println("Use 'java -jar opengse.jar --props=/path/to/" + propsfile.getName() + "' to deploy");
  }

  private static void buildAntFile(PrintWriter out, String context, File webinf) {
    out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
    out.println("<project name=\"" + context + "\" default=\"build\">");
    out.println("  <!-- global build properties -->");
    out.println("  <property name=\"web.dir\" value=\"${basedir}/web\"/>");
    out.println("  <property name=\"java.dir\" value=\"${web.dir}/WEB-INF/java\" />");
    out.println("  <property name=\"web.exploded.dir\" value=\"${basedir}/web-exploded\"/>");
    out.println("  <property name=\"classes.dir\" value=\"${web.exploded.dir}/WEB-INF/classes\"/>");
    out.println("  <property name=\"war.file\" value=\"${basedir}/" + context +".war\"/>");
    out.println("  <property name=\"opengse.jar\" value=\"${basedir}/../../opengse.jar\"/>");
    out.println();
    out.println("  <patternset id=\"compiler.resources\">");
    out.println("    <include name=\"**/?*.properties\" />");
    out.println("    <include name=\"**/?*.txt\" />");
    out.println("    <include name=\"**/?*.xml\" />");
    out.println("    <include name=\"**/?*.gif\" />");
    out.println("    <include name=\"**/?*.png\" />");
    out.println("    <include name=\"**/?*.jpeg\" />");
    out.println("    <include name=\"**/?*.jpg\" />");
    out.println("    <include name=\"**/?*.html\" />");
    out.println("    <include name=\"**/?*.dtd\" />");
    out.println("    <include name=\"**/?*.tld\" />");
    out.println("    <include name=\"**/?*.ser\" />");
    out.println("  </patternset>");
    out.println();
    out.println("  <target name=\"build\" depends=\"create-exploded-dir\">");
    out.println("    <echo>");
    out.println("      copying files from ${web.dir} to ${web.exploded.dir}");
    out.println("    </echo>");
    out.println("    <copy todir=\"${web.exploded.dir}\">");
    out.println("      <fileset dir=\"${web.dir}\" excludes=\"**/WEB-INF/java/**\"/>");
    out.println("    </copy>");
    out.println();
    out.println("    <mkdir dir=\"${classes.dir}\"/>");
    out.println("    <javac srcdir=\"${java.dir}\"");
    out.println("      destdir=\"${classes.dir}\">");
    out.println("      <classpath>");
    out.println("        <pathelement location=\"${opengse.jar}\"/>");
    out.println("      </classpath>");
    out.println("    </javac>");
    out.println("    <copy todir=\"${classes.dir}\">");
    out.println("      <fileset dir=\"${java.dir}\">");
    out.println("        <patternset refid=\"compiler.resources\" />");
    out.println("        <type type=\"file\" />");
    out.println("      </fileset>");
    out.println("    </copy>");
    out.println();
    out.println("    <delete file=\"${war.file}\"/>");
    out.println("    <war destfile=\"${war.file}\"");
    out.println("         webxml=\"${web.exploded.dir}/WEB-INF/web.xml\">");
    out.println("      <fileset dir=\"${web.exploded.dir}\"/>");
    out.println("    </war>");
    out.println("  </target>");
    out.println();
    out.println("  <target name=\"create-exploded-dir\">");
    out.println("    <delete dir=\"${web.exploded.dir}\"/>");
    out.println("    <mkdir dir=\"${web.exploded.dir}\"/>");
    out.println("  </target>");
    out.println();
    out.println("</project>");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
    out.println("");
  }

}

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

package com.google.opengse.configuration.webxml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.EntityResolver;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of XML-related utility methods.
 *
 * @author Mike Jennings
 */
public final class XMLUtil {
  private XMLUtil() { /* Utility class: do not instantiate. */ }

  public static Document readerToDocument(Reader reader)
      throws ParserConfigurationException, IOException, SAXException {
    return readerToDocument(reader, false);
  }


  public static Document readerToDocument(Reader reader, boolean validating)
      throws ParserConfigurationException, IOException, SAXException {

    DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
    dFactory.setNamespaceAware(true);
    dFactory.setValidating(validating);
    dFactory.setExpandEntityReferences(false);
    DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
    if (!validating) {
      // if not validating, don't attempt to download DTDs from the internet
      dBuilder.setEntityResolver(new NoOpEntityResolver());
    }
    InputSource inputSource = new InputSource(reader);
    return dBuilder.parse(inputSource);
  }


  public static Node[] getChildNodes(Node node, short type) {
    NodeList children = node.getChildNodes();
    if (children == null) {
      return new Node[0];
    }
    int n = children.getLength();
    List<Node> elnodelist = new ArrayList<Node>(n);
    for (int i = 0; i < n; ++i) {
      Node childnode = children.item(i);
      if (childnode.getNodeType() == type) {
        elnodelist.add(childnode);
      }
    }
    Node[] empty = {};
    return elnodelist.toArray(empty);
  }

  public static String getNodeAttribute(Node node, String name) {
    return getNamedItemNodeValue(node.getAttributes(), name, null);
  }

  public static String getNodeAttribute(Node node, String name, String def) {
    return getNamedItemNodeValue(node.getAttributes(), name, def);
  }

  static String getNamedItemNodeValue(
      NamedNodeMap attributes, String name, String defvalue) {
    Node namenode = attributes.getNamedItem(name);
    if (namenode == null) {
      return defvalue;
    }
    if (namenode.getNodeValue() == null) {
      return defvalue;
    }
    return namenode.getNodeValue();
  }

  public static String getChildTextNodes(Node node) {
    Node[] textnodes = XMLUtil.getChildNodes(node, Node.TEXT_NODE);
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < textnodes.length; ++i)
      sb.append(textnodes[i].getNodeValue());
    return sb.toString();
  }

  public static Node findNamedElementNode(Node doc, String name) {
    Node[] elnodes = getChildNodes(doc, Node.ELEMENT_NODE);
    for (int i = 0; i < elnodes.length; ++i) {
      if (elnodes[i].getNodeName().equals(name)) {
        return elnodes[i];
      }
    }
    return null;
  }

  private static class NoOpEntityResolver implements EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) {
      return new InputSource(new StringReader("")); 
    }
  }


}

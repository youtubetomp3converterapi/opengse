// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.Binding;
import javax.naming.NameParser;

/**
 * A dumb implementation of Context that throws
 * {@link UnsupportedOperationException} for all of its methods.
 *
 * @author Mike Jennings
 */
class UnsupportedContext implements Context {

  public Object lookup(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Object lookup(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void bind(Name name, Object obj) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void bind(String name, Object obj) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void rebind(Name name, Object obj) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void rebind(String name, Object obj) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void unbind(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void unbind(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void rename(Name oldName, Name newName) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void rename(String oldName, String newName) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void destroySubcontext(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void destroySubcontext(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Context createSubcontext(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Context createSubcontext(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Object lookupLink(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Object lookupLink(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NameParser getNameParser(Name name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public NameParser getNameParser(String name) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Name composeName(Name name, Name prefix) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public String composeName(String name, String prefix) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Object addToEnvironment(String propName, Object propVal) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Object removeFromEnvironment(String propName) throws NamingException {
    throw new UnsupportedOperationException();
  }

  public Hashtable<?, ?> getEnvironment() throws NamingException {
    throw new UnsupportedOperationException();
  }

  public void close() throws NamingException {
    throw new UnsupportedOperationException();
  }

  public String getNameInNamespace() throws NamingException {
    throw new UnsupportedOperationException();
  }
}

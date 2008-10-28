<%--
 Test for translation-time verification
 Should fail because there is one attribute missing. 
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:silly a="3" c="6"/>

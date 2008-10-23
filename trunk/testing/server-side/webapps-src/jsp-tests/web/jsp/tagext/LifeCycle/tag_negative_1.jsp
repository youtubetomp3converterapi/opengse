<%--
 Test for translation-time verification
 Fails because 3+2 != 6.
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:silly a="3" b="2" c="6"/>

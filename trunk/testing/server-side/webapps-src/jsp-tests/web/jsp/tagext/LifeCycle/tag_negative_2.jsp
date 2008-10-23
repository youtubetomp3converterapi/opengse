<%--
 Test for translation-time verification
 Should fail (at translation time) because the attributes are illegal
 (a is not an integer)
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:silly a="3" b="a" c="6"/>

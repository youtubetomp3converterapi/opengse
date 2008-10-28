<%--
 Test that comments are processed correctly...
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>
<x:count>
<!-- <x:count /> -->
</x:count>
<x:checkCount start="2"/>

Correct count and handling of JSP comments


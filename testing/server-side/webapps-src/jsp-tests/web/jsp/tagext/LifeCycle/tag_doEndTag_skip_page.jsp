<%-- Test for doEndTag SKIP_PAGE --%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>
<x:count skipPage="true">
once upon a time
</x:count>

<% if(true) throw new Error("Should not happen!"); %>


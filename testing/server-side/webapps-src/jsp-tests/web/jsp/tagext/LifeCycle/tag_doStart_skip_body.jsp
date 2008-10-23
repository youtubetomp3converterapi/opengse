<%-- Test for doStartTag SKIP_BODY --%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>
<x:count includeBody="true">
once upon a time
<x:count includeBody="false">
<% if(true) throw new Error("Call the errorpage"); %>
</x:count>
in a far far away kingdom...
</x:count>

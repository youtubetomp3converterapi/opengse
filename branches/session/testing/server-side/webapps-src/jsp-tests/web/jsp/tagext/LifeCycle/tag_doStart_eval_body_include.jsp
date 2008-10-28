<%-- Test for doStartTag EVAL_BODY_INCLUDE --%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>
<x:count includeBody="true">
once upon a time
<x:count includeBody="false">
this will not show up
</x:count>
in a far far away kingdom...
</x:count>

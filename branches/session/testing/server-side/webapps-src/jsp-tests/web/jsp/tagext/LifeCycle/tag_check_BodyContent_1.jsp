<%--
  Check that BodyContent provides access to the proper information
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:verbatim how="string">
once upon a time
</x:verbatim>
<x:verbatim how="reader">
once upon a time
</x:verbatim>
<x:verbatim how="writeout">
once upon a time
</x:verbatim>

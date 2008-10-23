<%--
  Check that BodyContent provides access to the proper information
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>
<x:verbatim how="string">
one
<x:verbatim how="reader">
two
<x:verbatim how="writeout">
three
</x:verbatim>
four
</x:verbatim>
five
</x:verbatim>

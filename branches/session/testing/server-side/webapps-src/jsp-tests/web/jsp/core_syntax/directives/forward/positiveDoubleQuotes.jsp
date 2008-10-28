<html>
<title>positiveDoubleQuotes</title>
<body>
<% /** Name :positiveDoubleQuotes
       Description : checking request time attributes with a
                     scriptlet expression in doublequotes to do forwarding
       Result :we expect the output without error to the forwarded file
 **/ %>
<%@ page autoFlush="false" %>
<% String test="includecommon"; %>
<jsp:forward page= "<%= test+\".html\" %>" />
</body>
</html>
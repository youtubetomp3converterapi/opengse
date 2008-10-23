<html>
<title>positiveSingleQuotes</title>
<body>
<%  /** Name : positiveSingleQuotes
        Description : we check if request time attribute work with include 
                     with a scriptlet expression given in single quotes
        Result : we expect the output with the file included without error
 **/ %>
<% String test="/tests/core_syntax/directives/include/includecommon"; %>
<jsp:include page= '<%= test+\".jsp\" %>' />
</body>
</html>

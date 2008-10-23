<html>
<title>positiveDoubleQuotes</title>
<body>
<% /**  Name : positiveDoubleQuotes
        Description : we check if request time attributes work with include
                      and we use them in double quoted expression
        Result : we should get the included file without error
 **/ %>
<% String test="/tests/core_syntax/directives/include/includecommon"; %>
<jsp:include page= "<%= test+\".jsp\" %>" />
</body>
</html>

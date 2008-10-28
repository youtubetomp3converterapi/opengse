<html>
<title>positiveRequestAttrPageRelative</title>
<body>
<% /**  Name : positiveRequestAttrPageRelative
        Description : we check if request time attributes work with include
                      and we use them in double quoted expression
        Result : we should get the included file without error
 **/ %>
<% String test="includecommon"; %>
<jsp:include page= '<%= test+".jsp" %>' flush="true" />
</body>
</html>

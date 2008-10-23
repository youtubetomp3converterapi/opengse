<html>
<title>positiveRequestAttrRelative</title>
<body>
<% /**  Name : positiveRequestAttrPageRelative
        Description : We check if request time attributes work by forwarding
                      the request using a page-relative path.
        Result : we should get the forwarded file without error
 **/ %>
<% String test="forwardcommon"; %>
<jsp:forward page= '<%= test+".jsp" %>' />
</body>
</html>

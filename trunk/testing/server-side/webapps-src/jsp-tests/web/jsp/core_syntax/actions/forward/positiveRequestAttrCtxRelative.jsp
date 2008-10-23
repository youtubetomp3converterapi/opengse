<html>
<title>positiveRequestAttrAbs</title>
<body>
<% /** 	Name : positiveRequestAttrCtxRelative
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:forward page > tag. We test if we get the forwarded jsp got
        parsed at request time and forwarded to the output using a 
        context-relative path.
**/ %>	 
<% String test="/jsp/core_syntax/actions/forward/forwardcommon"; %>
<!-- Request-time Dynamic inclusion, with a context-relative path -->
<jsp:forward page= '<%= test+".jsp" %>' />
</body>
</html>

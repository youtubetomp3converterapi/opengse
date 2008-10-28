<html>
<title>positiveRequestScopedObject</title>
<body>
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:forward page="/jsp/core_syntax/actions/useBean/ScopeResult.jsp">
    <jsp:param name="scope" value="request"/>
    <jsp:param name="objId" value="myBean"/>
</jsp:forward>
</body>
</html>

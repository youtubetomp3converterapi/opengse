<html>
<title>positiveSessionScopedObject</title>
<body>
<jsp:useBean id="myBean" scope="session" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:forward page="/jsp/core_syntax/actions/useBean/ScopeResult.jsp">
    <jsp:param name="scope" value="session"/>
    <jsp:param name="objId" value="myBean"/>
</jsp:forward>
</body>
</html>

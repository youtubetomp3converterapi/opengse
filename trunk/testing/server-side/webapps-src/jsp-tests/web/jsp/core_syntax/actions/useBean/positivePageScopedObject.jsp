<html>
<title>positivePageScopedObject</title>
<body>
<jsp:useBean id="myBean" scope="page" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:forward page="/jsp/core_syntax/actions/useBean/ScopeResult.jsp">
    <jsp:param name="scope" value="page"/>
    <jsp:param name="objId" value="myBean"/>
</jsp:forward>
</body>
</html>

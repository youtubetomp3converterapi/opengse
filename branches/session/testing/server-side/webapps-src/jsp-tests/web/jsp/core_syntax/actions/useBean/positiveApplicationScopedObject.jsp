<html>
<title>positiveApplicationScopedObject</title>
<body>
<jsp:useBean id="myBean" scope="application" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:forward page="/jsp/core_syntax/actions/useBean/ScopeResult.jsp">
    <jsp:param name="scope" value="application"/>
    <jsp:param name="objId" value="myBean"/>
</jsp:forward>
</body>
</html>

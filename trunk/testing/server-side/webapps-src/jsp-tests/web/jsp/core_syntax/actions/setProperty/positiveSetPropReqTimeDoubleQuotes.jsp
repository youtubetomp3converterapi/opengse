<html>
<title>positiveSetPropReqTimeDoubleQuotes</title>
<body>
<% /** 	Name : positiveSetPropReqTimeDoubleQuotes
	Description : use a scriptlet expression in double quotes
                      as 'value' attribute in setProperty
	Result :we should get the expected page without error
**/ %>
<!-- testing if are able to set a  property using double quoted expression -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="path" value="<%= request.getProtocol() %>" />
<jsp:getProperty name="myBean" property="path" />

</body>
</html>

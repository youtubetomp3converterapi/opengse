<html>
<title>positiveSetPropReqTimeSingleQuotes</title>
<body>
<% /** 	Name : positiveSetPropReqTimeSingleQuotes
	Description : use a scriptlet expression in single quotes
                      as 'value' attribute in setProperty
	Result :we should get the expected page without error
**/ %>
<!-- testing if are able to set a  property using single quoted expression -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="path" value='<%= request.getProtocol() %>' />
<jsp:getProperty name="myBean" property="path" />

</body>
</html>

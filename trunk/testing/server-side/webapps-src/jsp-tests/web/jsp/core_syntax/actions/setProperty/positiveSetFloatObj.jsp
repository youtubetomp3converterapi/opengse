<html>
<title>positiveSetFloatObj</title>
<body>
<% /** 	Name : positiveSetFloatObj
	Description : use a setProperty to set a Float value in a bean
	Result :should return a Float value.
**/ %>
<!-- We are testing if are able to set a Float property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectFloat" value="15" />
<jsp:getProperty name="myBean" property="objectFloat" />

</body>
</html>

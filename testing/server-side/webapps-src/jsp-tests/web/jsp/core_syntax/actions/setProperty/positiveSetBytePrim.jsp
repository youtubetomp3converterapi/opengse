<html>
<title>positiveSetBytePrim</title>
<body>
<% /** 	Name : positiveSetBytePrim
	Description : use a setProperty to set a byte value in a bean
	Result :should return a byte value.
**/ %>
<!-- We are testing if are able to set a primitiveByte property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveByte" value="123" />
<jsp:getProperty name="myBean" property="primitiveByte" />

</body>
</html>

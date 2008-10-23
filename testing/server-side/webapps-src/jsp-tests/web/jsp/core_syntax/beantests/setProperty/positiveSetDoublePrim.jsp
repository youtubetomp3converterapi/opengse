<html>
<title>positiveSetDoublePrim</title>
<body>
<% /** 	Name : positiveSetDoublePrim
	Description : use a setProperty to set a double value in a bean
	Result :should return a double value.
**/ %>	 
<!-- We are testing if are able to set a PrimitiveDouble property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveDouble" value="123456" />
<jsp:getProperty name="myBean" property="primitiveDouble" />

</body>
</html> 

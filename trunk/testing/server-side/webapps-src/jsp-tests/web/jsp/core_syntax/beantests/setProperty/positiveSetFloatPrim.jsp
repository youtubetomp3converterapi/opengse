<html>
<title>positiveSetFloatPrim</title>
<body>
<% /** 	Name : positiveSetFloatPrim
	Description : use a setProperty to set a float value in a bean
	Result :should return a float value.
**/ %>	 
<!-- We are testing if are able to set a primitiveFloat property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveFloat" value="123" />
<jsp:getProperty name="myBean" property="primitiveFloat" />

</body>
</html> 

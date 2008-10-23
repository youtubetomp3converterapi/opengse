<html>
<title>positiveSetBooleanPrim</title>
<body>
<% /** 	Name : positiveSetBooleanPrim
	Description : use a setProperty  to set a boolean value in a bean
	Result :should return a boolean value.
**/ %>	 
<!-- We are testing if are able to set a PrimitiveBoolean property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveBoolean" value="false" />
<jsp:getProperty name="myBean" property="primitiveBoolean" />

</body>
</html> 

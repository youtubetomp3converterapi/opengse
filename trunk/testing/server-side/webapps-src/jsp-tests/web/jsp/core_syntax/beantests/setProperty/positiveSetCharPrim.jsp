<html>
<title>positiveSetCharPrim</title>
<body>
<% /** 	Name : positiveSetCharPrim
	Description : use setProperty to set a char value in a bean.
	Result :should return a char value.
**/ %>	 
<!-- We are testing if are able to set a Primitive Character property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveChar" value="R" />
<jsp:getProperty name="myBean" property="primitiveChar" />

</body>
</html> 

<html>
<title>positiveSetIntPrim</title>
<body>
<% /** 	Name : positiveSetIntPrim
	Description :use a setProperty to set a int value in a bean. 
	Result :should return a int value.
**/ %>
<!-- We are testing if are able to set a PrimitiveInt property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveInt" value="12" />
<jsp:getProperty name="myBean" property="primitiveInt" />

</body>
</html>

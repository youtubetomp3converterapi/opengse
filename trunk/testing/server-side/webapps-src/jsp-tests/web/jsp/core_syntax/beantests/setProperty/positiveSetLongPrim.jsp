<html>
<title>positiveSetLongPrim</title>
<body>
<% /** 	Name : positiveSetLongPrim
	Description : use a setProperty to set a long value in a bean
	Result :should return a long value.
**/ %>
<!-- We are testing if are able to set a Long property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="primitiveLong" value="54321" />
<jsp:getProperty name="myBean" property="primitiveLong" />

</body>
</html> 

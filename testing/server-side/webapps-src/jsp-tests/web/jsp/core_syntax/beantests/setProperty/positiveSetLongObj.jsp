<html>
<title>positiveSetLongObj</title>
<body>
<% /** 	Name : positiveSetLongObj
	Description : use a setProperty to set a Long value in a bean
	Result :should return a Long value.
**/ %>	 
<!-- We are testing if are able to set a Long property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectLong" value="12345" />
<jsp:getProperty name="myBean" property="objectLong" />

</body>
</html> 

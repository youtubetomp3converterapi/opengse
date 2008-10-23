<html>
<title>positiveSetDoubleObj</title>
<body>
<% /** 	Name : positiveSetDoubleObj
	Description : use a setProperty to set a Double value in a bean
	Result :should return a Double value.
**/ %>	 
<!-- We are testing if are able to set a Double property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectDouble" value="123456" />
<jsp:getProperty name="myBean" property="objectDouble" />

</body>
</html> 

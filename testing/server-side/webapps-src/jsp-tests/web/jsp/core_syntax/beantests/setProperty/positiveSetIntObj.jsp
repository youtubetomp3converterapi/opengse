<html>
<title>positiveSetIntObj</title>
<body>
<% /** 	Name : positiveSetIntObj
	Description :use a setProperty to set a Integer value in a bean. 
	Result :should return a Integer value.
**/ %>	 
<!-- We are testing if are able to set an int property and get it -->
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectInt" value="21" />
<jsp:getProperty name="myBean" property="objectInt" />

</body>
</html> 

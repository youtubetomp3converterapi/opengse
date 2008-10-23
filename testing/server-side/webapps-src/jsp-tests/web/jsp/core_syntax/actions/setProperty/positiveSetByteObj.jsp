<html>
<title>positiveSetByteObj</title>
<body>
<% /** 	Name : positiveSetByteObj
	Description :use a setProperty to set a Byte value in a bean 
	Result :should return a Byte value.
**/ %>
<!-- We are testing if are able to set a byte property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectByte" value="123" />
<jsp:getProperty name="myBean" property="objectByte" />

</body>
</html>

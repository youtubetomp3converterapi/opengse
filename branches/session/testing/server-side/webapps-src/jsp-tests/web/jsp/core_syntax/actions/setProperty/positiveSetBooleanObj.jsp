<html>
<title>positiveSetBooleanObj</title>
<body>
<% /** 	Name : positiveSetBooleanObj
	Description : use a setProperty  to set a Boolean value in a bean
	Result : should return a Boolean value.
**/ %>
<!-- We are testing if are able to set a Boolean property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectBoolean" value="true" />
<jsp:getProperty name="myBean" property="objectBoolean" />

</body>
</html>

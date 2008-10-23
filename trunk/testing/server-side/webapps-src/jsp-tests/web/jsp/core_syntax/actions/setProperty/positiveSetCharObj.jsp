<html>
<title>positiveSetCharObj</title>
<body>
<% /** 	Name : positiveSetCharObj
	Description : use setProperty to set a Character value in a bean.
	Result :should return a Character value.
**/ %>
<!-- We are testing if are able to set a Character property and get it -->
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:setProperty name="myBean" property="objectChar" value="A" />
<jsp:getProperty name="myBean" property="objectChar" />

</body>
</html>

<html>
<title>positiveBeanPropertyEditor</title>
<body>
<% /** 	Name : positiveBeanPropertyEditor
	Description : Using a bean that has been configured with a 
                  PropertyEditor, validate that the editors
                  are in fact used.
	Result : Output to the client (see comments below)
**/ %>
<jsp:useBean id="propertyBean" class="core_syntax.actions.setProperty.PropertyBean" />
<jsp:setProperty name="propertyBean" property="PString" value="Validated" />
<jsp:setProperty name="propertyBean" property="PBoolean" value="false" />
<jsp:setProperty name="propertyBean" property="PInteger" value="218" />

<!-- getProperty PString should return "PString Validated" -->
<jsp:getProperty name="propertyBean" property="PString" />

<!-- getProperty PBoolean should return "true" -->
<jsp:getProperty name="propertyBean" property="PBoolean" />

<!-- getProperty PInteger should return "218314" -->
<jsp:getProperty name="propertyBean" property="PInteger" />

</body>
</html>

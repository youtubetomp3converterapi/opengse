
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetPropNoParam</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetPropNoParam
	Description : Create a valid useBean action in JSP. Set a specific 
	              property of that bean from the request using a 
	              <jsp:setProperty property="propName"> action. Then access 
	              that property.Ensure that the request contains a parameter 
	              with the same name as the Bean Name.
	Result :As we are setting "param as Str=SAPPOTA"
		It should return "SAPPOTA".
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.setProperty.SetpropBean">
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="str" />
<jsp:text><![CDATA[
]]></jsp:text>
</jsp:useBean>
<jsp:text><![CDATA[
<!-- Accessing the property thru a scriptlet -->

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
out.println(myBean.getStr());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>
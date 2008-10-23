
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetPropParam</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetPropParam
	Description : Create a valid useBean action in JSP. Set a specific 
		      property of that bean from the request using a 
		      <jsp:setPropertyparam="paramname> action. 
		      Then access that property.
	Result : As we are setting "param as Name=MANGO", It should 
		 return "MANGO".
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="name" param="Name" />
<jsp:text><![CDATA[
<!-- Accessing the property thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
out.println(myBean.getName());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>
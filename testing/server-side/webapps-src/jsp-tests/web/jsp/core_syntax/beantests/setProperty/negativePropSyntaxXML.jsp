
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativePropSyntax</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativePropSyntax
	Description : Create a valid useBean action in JSP. Use both a param 
		      and a value attribute in the setProperty action.
	Result : Fatal Translation Error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
]]></jsp:text>
<jsp:directive.page errorpage="Errorpage.jsp" />
<jsp:text><![CDATA[
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
<!-- Setting the property.. here the syntax is misused -->
]]></jsp:text>
<jsp:setProperty name="myBean" property="*" value="Kash" />
<jsp:text><![CDATA[
<!-- Accessing the properties thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(myBean.getName()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[ 
</body>
</html>
]]></jsp:text>

</jsp:root>
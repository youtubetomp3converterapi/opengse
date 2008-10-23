
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetPropAll</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetPropAll
	Description : Create a valid useBean action in JSP. Set all properties 
		      of that bean from the request using a 
		      <jsp:setProperty property="*"> action. Then access all 
		      properties of the bean.
	Result : As we are setting "param as Name=MANGO,num=90336,str=hello,"
		It should return "hello,MANGO,90336".
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="*" />
<jsp:text><![CDATA[
<!-- Accessing the properties thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(myBean.getStr()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[ <br> ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(myBean.getName()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<br> ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(myBean.getNum()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>
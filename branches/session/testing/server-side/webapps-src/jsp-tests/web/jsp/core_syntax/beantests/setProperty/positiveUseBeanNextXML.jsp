
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveUseBeanNext</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveUseBeanNext
	Description : Create a valid useBean action. In the body of the action, 
		      put setProperty statements to set specific properties of 
		      the bean.Ensure that the bean already exists. Call the 
		      page and check the value of the property that you have 
		      specified in the useBean body.
	Result :Should return the non-initial value of the property.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="num" value="90336" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="str" value="APPLE" />
<jsp:text><![CDATA[
<!-- Accessing the property thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
out.println(myBean.getNum());
out.println(myBean.getStr());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>
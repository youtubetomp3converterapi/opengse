
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveUseBeanInit</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveUseBeanInit
	Description : Create a valid useBean action. In the body of the action, 
		      put setProperty statements to set specific properties of 
		      the bean.Ensure that the bean class does not exist before 
		      calling this page. Call the page for the first time.
	Result :Should return the value of the property that was set in the
                useBean body.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean   -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="name" value="Tony" />
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
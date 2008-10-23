
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeInvalidClass</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeInvalidClass
	Description : Try to create a JSP with useBean tag that contains a
                      non-implicit class reference and no source file for that
                      class.
	Result :Fatal Translation Error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the not available bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.NotAvbl" />
<jsp:text><![CDATA[
<!-- accessing the bean thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 out.println(myBean.getCount());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>
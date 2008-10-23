
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeDupID</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeDupID
	Description : Try to create two useBean tags in the same JSP page
                      with same ID.
	Result :Should return fatal translation error.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 <!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<jsp:text><![CDATA[
<!-- Declaring the bean with out body.Here same name is used for the declaration -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.IntBean2" />
<jsp:text><![CDATA[
<!-- accessing the bean thru a scriptlet  -->
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
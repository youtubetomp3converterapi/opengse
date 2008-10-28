
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeBeanName</title>
<body>
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:directive.page errorPage="errorPage.jsp" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="myBean"  beanName="core_syntax.beantests.useBean.NCounter" 
type="core_syntax.beantests.useBean.NewCounter" />
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

<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveBeanNameTypeCast</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveBeanNameTypeCast
	Description : Declaring the bean using beanName.  
    When using beanName, the bean can either be a serialized
    Object, or a fully qualified class.  When the class is loaded,
    it is cast to the type specified by the "type" attribute.  
    This test uses a fully qualfied class.
	Result :We should get page output without error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with beanName as a class -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request"
beanName="core_syntax.actions.useBean.NewCounter"
type="core_syntax.actions.useBean.Counter" />
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
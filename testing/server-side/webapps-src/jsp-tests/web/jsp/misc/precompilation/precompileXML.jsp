
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>Precompilation Test</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  /**  Name:Precompilation 
         Description: Checks if the request is actually delivered
                      to this page or not if the request
                      parameter jsp_precompile is either not set
	              or set to "true" .
              
         Result: Will send the message "Got The request"  
                 if the jsp_precompile="false"
                 
                
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

got The request....The test parameter is
]]></jsp:text>

<jsp:declaration>
<![CDATA[ String test; ]]>

</jsp:declaration>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
  test=request.getParameter("test"); 
  if(test==null) //just to remove the dependency on Java 
  test="null" ;

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ test ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
</html>


]]></jsp:text>

</jsp:root>
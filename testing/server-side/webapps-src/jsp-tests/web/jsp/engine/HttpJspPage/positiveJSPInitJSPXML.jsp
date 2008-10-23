
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveJSPInitJSP</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  /**
       Name : positiveJSPInitJSP
       Description: we override the jspInit in the jsp itself
                    and check getServletConfig method works
       Result: we should get the expected string output
  */ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- to test HttpJspPage implementation -->
]]></jsp:text>

<jsp:declaration>
<![CDATA[ String result; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:declaration>
<![CDATA[ public void jspInit() {
     javax.servlet.ServletConfig con=getServletConfig();
     if(con!=null) {
      result="it works";
     }else {
       result="ServletConfig is null";
    }
  }
]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(result); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>

<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>theadsafeTrue</title>
<body>
<!-- This is going to make any client to wait for an infinite amount of time -->
<!-- If 'isthreadsafe' is true, following condition should happen -->
<!-- JSP processor dispatches multiple outstanding requests simultaneously -->
<!-- Here we keep buffer as 'none' so that output goes directly to the stream -->
]]></jsp:text>
<jsp:directive.page isThreadSafe="true" buffer="none" autoFlush="true" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int i=2; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(i); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ for(int j=1;j<20000; j++) { i++;  } 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>
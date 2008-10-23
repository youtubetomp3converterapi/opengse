
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[]]></jsp:text>
<jsp:directive.page buffer="5kb" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ int bufferSize = out.getBufferSize(); 
out.flush();
out.print("hello:");
int remainingSize =  out.getRemaining();
if ( remainingSize == (bufferSize-6) ) 
	out.print("getRemaining test Passed");
else { 
	out.println("getRemaining Test Failed");
	out.println ("Expected Buffer Size = " + bufferSize);
	out.println("Remaining Buffer Size = " + remainingSize);
     }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

</jsp:root>
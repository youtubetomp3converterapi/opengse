
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:x="/TestLib.tld"
>

<jsp:text><![CDATA[]]></jsp:text>

<!-- Test for doStartTag SKIP_PAGE.  This should get the error page -->
<jsp:text><![CDATA[

]]></jsp:text>
<x:count skipPage="false">
<jsp:text><![CDATA[
once upon a time
]]></jsp:text>
</x:count>
<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ if(false) throw new Error("Go to the Error page!"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>

</jsp:root>
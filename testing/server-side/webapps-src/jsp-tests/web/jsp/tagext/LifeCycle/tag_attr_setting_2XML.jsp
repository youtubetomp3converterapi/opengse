
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:x="/TestLib.tld"
>

<jsp:text><![CDATA[]]></jsp:text>

<!--
  Test that attribute setting is as wanted
  Intermix some different tags
-->
<jsp:text><![CDATA[

]]></jsp:text>
<x:count>
<jsp:text><![CDATA[
]]></jsp:text>
<x:count att1="one">
<jsp:text><![CDATA[
]]></jsp:text>
<x:aTag att="foo">
<jsp:text><![CDATA[
one
]]></jsp:text>
<x:checkCount nested="2"/>
<jsp:text><![CDATA[
]]></jsp:text>
</x:aTag>
<jsp:text><![CDATA[
]]></jsp:text>
</x:count>
<jsp:text><![CDATA[
]]></jsp:text>
<x:aTag att="bar"/>
<jsp:text><![CDATA[
]]></jsp:text>
<x:count att1="two">
<jsp:text><![CDATA[
]]></jsp:text>
<x:checkCount nested="2"/>
<jsp:text><![CDATA[
two
]]></jsp:text>
</x:count>
<jsp:text><![CDATA[
]]></jsp:text>
<x:checkCount nested="1"/>
<jsp:text><![CDATA[
]]></jsp:text>
</x:count>
<jsp:text><![CDATA[

]]></jsp:text>

</jsp:root>
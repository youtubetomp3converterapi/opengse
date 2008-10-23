
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:test="/TestLib.tld"
>

<jsp:text><![CDATA[<html>
<title>Persistent Values of instance variables inside tags</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : persistentValues
	Description : Tests to see if the values of instance variables remains persistent.
		     
	            
	Result :  Message should be printed at the beginning of each tag starting 
	          from the first tag and then at the end of each tag starting from 
		      the innner most tag
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[



]]></jsp:text>
<test:persistence tagid="one" >
<jsp:text><![CDATA[
]]></jsp:text>
<test:persistence tagid="two" >
<jsp:text><![CDATA[
]]></jsp:text>
<test:persistence tagid="three" >
<jsp:text><![CDATA[
]]></jsp:text>
</test:persistence>
<jsp:text><![CDATA[
]]></jsp:text>
</test:persistence>
<jsp:text><![CDATA[
]]></jsp:text>
</test:persistence>
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>
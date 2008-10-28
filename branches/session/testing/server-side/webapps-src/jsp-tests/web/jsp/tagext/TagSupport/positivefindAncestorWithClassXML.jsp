
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:test="/TestLib.tld"
>

<jsp:text><![CDATA[<html>
<title>positivefindAncestorWithClass</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name :positivefindAncestorWithClass
        Description : Test the coordination between nested tags.  A person 
		      can be eligible for only one prize. 
	            
	Result :  The name of the prize winners is printed.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[



]]></jsp:text>
<test:firstWinner winner="santosh" >
<jsp:text><![CDATA[
]]></jsp:text>
<test:secondWinner winner="santosh" />
<jsp:text><![CDATA[
]]></jsp:text>
<test:secondWinner winner="pierre" />
<jsp:text><![CDATA[
]]></jsp:text>
</test:firstWinner>
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>
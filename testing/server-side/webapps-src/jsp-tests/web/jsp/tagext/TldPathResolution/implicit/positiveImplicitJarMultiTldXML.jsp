
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:tldone="/tldone"
xmlns:tldtwo="/tldtwo"
>

<jsp:text><![CDATA[<html>
<title>positiveImplicitJarMultiTld</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveImplicitJarMultiTld
	Description : Verify that the container can correctly 
                  recognize multiple tlds in a jar file,
                  and using the <uri> element within the tld,
                  create the appropiate mapping for client access.
	            
	Result : The tags will send output to the client.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[



]]></jsp:text>
<tldone:tldtag uri="Implicit URI - tld_uri_one"/>
<jsp:text><![CDATA[
]]></jsp:text>
<tldtwo:tldtag uri="Implicit URI - tld_uri_two"/>
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>
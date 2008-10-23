
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
xmlns:direct="urn:jsptld:/WEB-INF/tlds/example-taglib.tld"
>

<jsp:text><![CDATA[<html>
<title>positiveDirectTldReference</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveDirectTldReference
	Description : Verify that a tag can be used when the 
                  TLD is directly referenced in the uri
                  attribute of the taglib directive.
	            
	Result : The tag should send output to the client.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>
<direct:test toBrowser="true" att1="att1">
<jsp:text><![CDATA[
Validated
]]></jsp:text>
</direct:test>
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>
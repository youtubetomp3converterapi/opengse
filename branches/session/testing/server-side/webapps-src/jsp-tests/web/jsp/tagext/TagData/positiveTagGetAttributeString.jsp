<html>
<title>positiveTagGetAttributeString</title>
<body>
<% 
	/** 	
	Name : positiveTagGetAttributeString
	Description : Create a TagData object by passing a hashtable
	              Using setAttribute() method set the attributes.
	              Check using the getAttributeString() .
	Result :     Should print the contents that are set.
	**/  
%>
<%

	java.util.Hashtable ht = new java.util.Hashtable();
	javax.servlet.jsp.tagext.TagData td = new javax.servlet.jsp.tagext.TagData(ht);
	
	td.setAttribute("Color1","red");
	td.setAttribute("Color2","green");
	td.setAttribute("Color3","yellow");
	td.setAttribute("Color4","orange");

	for(int i=1;i<=4;i++) {
		out.println("Colors are  " + td.getAttributeString("Color"+i));

%>
<br> <% }
%>


</body>
</html>

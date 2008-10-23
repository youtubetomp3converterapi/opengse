<html>
<title>positiveTagGetAttribute</title>
<body>
<% 
	/** 	
	Name : positiveTagGetAttribute
	Description : Create a TagData object by passing a hashtable.
	              Print the contents of the hashtable using the 
	              getAttribute() method of TagData.
	Result :    The contents should be printed.
	**/  
%>
<%
	java.util.Hashtable ht = new java.util.Hashtable();
	ht.put("Color1","red");
	ht.put("Color2","green");
	ht.put("Color3","yellow");
	ht.put("Color4","orange");

	javax.servlet.jsp.tagext.TagData td = new javax.servlet.jsp.tagext.TagData(ht);

	for(int i=1;i<=4;i++) {
		out.println("Colors are  " + td.getAttribute("Color"+i));

%>
<br> <% }
%>

</body>
</html>

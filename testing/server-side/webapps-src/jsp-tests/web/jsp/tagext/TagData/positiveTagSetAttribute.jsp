<html>
<title>positiveTagSetAttribute</title>
<body>
<% 
	/** 	
	Name : positiveTagSetAttribute
	Description : Create a TagData object by passing a hashtable
	              created using the setAttribute() method of
	              TagData.  Call the getAttribute() method and print 
	              the contents.
	Result :      The contents set should be printed.
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
		out.println("Colors are  " + td.getAttribute("Color"+i));

%>
<br> <% }
%>
</body>
</html>

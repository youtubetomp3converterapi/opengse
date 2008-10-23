<html>
<title>positiveRemoveValue</title>
<body>
<% 
	/** 	
	Name : positiveRemoveValue
	Description : Create a TagSupport object and set values
	              using the setValue() method.  Get the values 
	              set using the getValue() method.  Call the removeValue()
	              method and remove one value.  Print the rest.
	Result :   The values, except the one removed, should be printed.
	**/  
%>
<%

	javax.servlet.jsp.tagext.TagSupport ts = new javax.servlet.jsp.tagext.TagSupport();
	
	ts.setValue("Color1","red");
	ts.setValue("Color2","green");

	for(int i=1;i<=2;i++) {
		out.println("Colors are  " + ts.getValue("Color"+i));

%>
<br> <% }
%>

<br>
<%
ts.removeValue("Color1");
out.println( ts.getValue("Color1"));

%>
</body>
</html>

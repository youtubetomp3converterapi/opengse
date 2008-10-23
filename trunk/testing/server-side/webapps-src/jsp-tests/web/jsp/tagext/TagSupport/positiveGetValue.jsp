<html>
<title>positiveGetValue</title>
<body>
<%
    /*
    
    Name:  positiveGetValue
    Description: Create a TagSupport object and set values using
                  the setValue() method. Call the getValue() method
                  and print the contents.
     Result:     The values which were set should be printed.             
 */
 
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

</body>
</html>

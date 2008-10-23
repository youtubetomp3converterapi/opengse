<html>
<title>positiveGetValues</title>
<body>
<%
    /*
    
    Name:  positiveGetValues
    Description: Create a TagSupport object and set values using
                  the setValue() method.  Call the getValues() method
                  which returns an enumeration, and print the contents.
     Result:     The values which were set should be printed.             
 */
 
%>
<%

	javax.servlet.jsp.tagext.TagSupport ts = new javax.servlet.jsp.tagext.TagSupport();
	
	ts.setValue("Color1","red");
	ts.setValue("Color2","green");
%>
<!-- Returns only keys names -->
<%

	java.util.Enumeration e = ts.getValues();
	while (e.hasMoreElements()) {
        String v = (String)e.nextElement();
       	out.println(v);
    }
%>
</body>
</html>

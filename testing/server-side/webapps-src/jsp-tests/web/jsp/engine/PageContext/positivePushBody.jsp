<html>
<title>positivePushBody</title>
<body>
<%
/*
       Name: positivePushBody
       Description:Call the pushBody() method which returns a
                 new BodyJspWriter object, saves the current "out" 
                 JspWriter,and updates the value of the "out" attribute 
                 in the page scope attribute namespace of the PageContext.
                 Print the contents of the BodyContent object using the "out"
                 object.
       Result:   Should print the contents of the BodyContent Object
       
*/
%>                 



<%	
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	bc.println(".com");
	out.println(bc.getString());
	pageContext.popBody();
%>
</body>
</html>

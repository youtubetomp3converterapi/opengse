<html>
<title>positivePopBody</title>
<body>

<%
/*
     Name: positvePopBody
     Description:Call the pushBody() method which returns a
                 new BodyJspWriter object, saves the current "out" JspWriter,
                 and updates the value of the "out" attribute in the
                 page scope attribute namespace of the PageContext.Then call 
                 the popBody() which returns the previous JspWriter "out" saved
                 by the matching pushBody() and updates the value of the "out" 
                 attribute in the page scope attribute namespace of the PageConxtext
                 Invoke some method on the JspWriter object.
                 
      Result:    Should return the expected return type (depends on the method invoked).            
*/
%>

<%	
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	JspWriter jw = pageContext.popBody();
	jw.println("sun");
%>
</body>
</html>

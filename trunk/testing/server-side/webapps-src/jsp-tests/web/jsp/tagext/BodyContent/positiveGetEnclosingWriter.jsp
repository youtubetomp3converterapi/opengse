<html>
<title>positiveGetEnclosingWriter</title>
<body>
<% 
	/** 	
	Name : positiveGetEnclosingWriter
	Description : Try to get JSPWriter object called by getEnclosingWriter()
				method.
	Result : Expected to return JspWriter object. 
	**/  
%>	 


<!-- Using pageContext.pushBody() to create a BodyContent object -->
<%	
        javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
%>
<!-- checking for JspWriter object -->
<%= (bc.getEnclosingWriter()) instanceof javax.servlet.jsp.JspWriter %>
<%
        pageContext.popBody();
%>

</body>
</html>

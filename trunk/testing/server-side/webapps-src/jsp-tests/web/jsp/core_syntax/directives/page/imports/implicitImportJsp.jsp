<html>
<body>
<% /**	Name: implicitImportJsp
	Description: Use jsp page directive with language="java"
                 with no import statement.  Validate that
                 javax.servlet.jsp.* was imported implicitly
                 by creating an instance of JspFactory.
	Result:No error
**/ %>


<!-- language=java and we check if implicit import works -->

<%@ page language="java"  %>

<%

   JspFactory jfac=JspFactory.getDefaultFactory();

%>

<%= jfac instanceof javax.servlet.jsp.JspFactory %>


</body>
</html>

<html>
<title>positiveExtends</title>
<body>
<% /** 
       Name : positiveExtends
       Description: we use extends directive to point our own defined class
                    as super class to the jsp
       Result: we should get true to the expression after the directive
  */ %>
<%@ page extends="core_syntax.directives.page.extend.SuperPage" %>
<%= (this instanceof core_syntax.directives.page.extend.SuperPage ) %>
</body>
</html>

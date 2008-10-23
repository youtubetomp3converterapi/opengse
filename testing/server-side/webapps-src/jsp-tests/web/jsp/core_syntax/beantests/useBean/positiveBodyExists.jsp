<html>
<title>positiveBodyExists</title>
<body>
<% /**  Name : positiveBodyExists
    Description : Try to create a valid useBean tag in the JSP page with a
                      non-empty body and with a class name that has already
                      been created.
    Result :Should return the page contents except what is contained within
                the body.
**/ %>
<!-- Declaring the bean with body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean">
<jsp:setProperty name="myBean" property="name" value="APPLE" />
</jsp:useBean>
<jsp:getProperty name="myBean" property="name" />
</body>
</html> 

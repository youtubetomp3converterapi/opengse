<html>
<title>negativeGetPropObjectNotFoundException</title>
<body>
<% /** 	Name : negativeGetPropObjectNotFoundException
	Description : Access a property of a non-existant bean. An Exception
                  should be raised.
	Result : An Exception of Unknown type should be caught and a message should
             be displayed.
**/  %>
<% try { %>
<jsp:getProperty name="notFoundBean" property="name" />
<% } catch ( Exception e ) { 
          out.println( "Exception successfully caught: Test Status: PASS" );
   }
%>
</body>
</html>

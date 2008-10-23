<html>
<title>Precompilation Test</title>
<body>
<%  /**  Name:Precompilation 
         Description: Checks if the request is actually delivered
                      to this page or not if the request
                      parameter jsp_precompile is either not set
	              or set to "true" .
              
         Result: Will send the message "Got The request"  
                 if the jsp_precompile="false"
                 
                
**/ %>

got The request....The test parameter is
<%! String test; %>

<%
  test=request.getParameter("test"); 
  if(test==null) //just to remove the dependency on Java 
  test="null" ;
%>
<%= test %>

</body>
</html>



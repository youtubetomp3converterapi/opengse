<html>
<title>positiveForward</title>
<body>
<%
/*
 Name : positiveForward
 Description : Call the method forward() with the pageContext
 object, and pass a valid jsp page as an argument in
 the method.
*/
%>
<%
try{
        pageContext.forward("forward.jsp");
	return;
    }catch(Exception e){
        out.println(e);
    }
%>
</body>
</html>

<html>
<title>negativeInclude</title>
<body>
<%
/*
 Name : negativeInclude
*/
%>
<!-- this is to test if include() method works -->
<!-- using pageContext object to include -->
<!-- we trying call a non extisting file by include method, 
should throw IOException -->
<%
try{
    pageContext.include("/tests/engine/PageContext/Back.jsp");
    }catch(Exception e){
    out.println(e);
    
}
%>


</body>
</html>
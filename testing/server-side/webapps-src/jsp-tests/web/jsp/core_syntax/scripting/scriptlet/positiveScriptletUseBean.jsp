<html>
<title>positiveScriptletUseBean</title>
<body>
<% /**	Name:positiveScripletUseBean
		Description:Create a valid useBean action in the JSP.
				    After that,use a scriplet to change the 				
				    value of a specific bean property. Send the new 
				    value of the property to outstream.
		Result: The page contents with the new value of the bean 
				property
**/ %>
								
<!-- we use scriptlet to access the property of a bean -->
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.scriptlet.Counter" />

<!-- setCounter method is used in Bean and send to out stream -->
<%
	counter.setCounter(10);
	out.println(counter.getCounterValue());
%>

</body>
<html>

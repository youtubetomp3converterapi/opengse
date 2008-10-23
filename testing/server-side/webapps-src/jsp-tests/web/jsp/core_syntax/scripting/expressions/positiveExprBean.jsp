<html>
<title>positiveExprBean</title>
<body>
<% /**	Name:positiveExprBean
		Description: Use a bean with useBean action in the page.
	    			 Then try to retrieve one of its properties 
	    			 with an expression.
		Result: The HTML should contain the value of that property 
					 inserted in the page.
**/ %>					 	    			 
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.expressions.Counter" />

<!-- using scriptlet setCounter to set counter value 10 -->
<% counter.setCounter(10); %>
<!-- using getcounterValue method in bean -->
<%= counter.getCounterValue() %>

</body>
<html>
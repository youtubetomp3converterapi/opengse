<html>
<title>negativeExprBean</title>
<body>
<% /**	Name: negativeExprBean
		Description: Use a bean with the useBean action in the page.
			  	Then try to retrieve one of its properties with an 
			  	expression.
		Result: Should throw ClassCastException
**/ %>					  	
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.expressions.Counter" />

<!-- using expression to setCounter to set counter value 10 -->
<%= counter.setCounter(10) %>
 
<!-- using getcounterValue method in bean -->
<%= counter.getCounterValue() %>

</body>
<html>

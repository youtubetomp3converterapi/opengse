<html>
<title>positiveScriptlet</title>
<body>
<%  /**  Name:positiveScriptlet
         Description: Test a series of scriplets.
         Result: Output of all the scriplets in the order
                 they appear.
**/ %>

<!-- Java-style comments -->
<%
// simple java comments
/* multiline java comments
*/
%>

<!-- Multiline scriptlet -->
<%  int i=5;
    int j=10;
    if(j>i){
    %> 10 <% }
    else {
    %> 5 <% } %>

<!-- Another version of a Multiline scriplet -->
<%! int k=5; %>
<%! int l=10; %>
<% if(l>k){ %>10 <%} else { %>
5 <% } %>

<!-- A scriptlet accessing a bean -->
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.scriptlet.Counter" />
<%
    counter.setCounter(10);
    out.println(counter.getCounterValue());
%>

<!-- A scriplet with try/throws/finally blocks -->
<%
    int q = 0;
    try {
        q = ( 9 / 0 );
    } catch ( ArithmeticException ae ) {
        out.println( "ArithmeticException caught!" );
    } finally {
        q = 0;
    }
%>

</body>
</html>

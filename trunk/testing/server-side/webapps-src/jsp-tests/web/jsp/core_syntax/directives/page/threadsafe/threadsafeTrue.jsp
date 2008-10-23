<html>
<title>theadsafeTrue</title>
<body>
<!-- This is going to make any client to wait for an infinite amount of time -->
<!-- If 'isthreadsafe' is true, following condition should happen -->
<!-- JSP processor dispatches multiple outstanding requests simultaneously -->
<!-- Here we keep buffer as 'none' so that output goes directly to the stream -->
<%@ page isThreadSafe="true" buffer="none" autoFlush="true" %>
<%! int i=2; %>
<% out.println(i); %>
<% for(int j=1;j<20000; j++) { i++;  } %>
</body>
</html>

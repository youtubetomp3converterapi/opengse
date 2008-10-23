<html>
<title>theadsafeFalse</title>
<body>
<!-- This is going to make any client to wait for an infinite amount of time -->
<!-- If 'isthreadsafe' is false , following condition should happen -->
<!-- JSP processor dispatches multiple outstanding requests one at a time -->
<!-- Here we keep buffer as 'none' so that output goes directly to the stream -->
<%@ page isThreadSafe="false" buffer="none" %>
<%! int i=2; %>
<% out.println(i); %>
<% for(int j=1;j<i; j++) { i++;  } %>
</body>
</html>

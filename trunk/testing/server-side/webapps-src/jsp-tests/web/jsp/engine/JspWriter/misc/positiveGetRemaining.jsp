<%@ page buffer="5kb" %>
<% int bufferSize = out.getBufferSize(); 
out.flush();
out.print("hello:");
int remainingSize =  out.getRemaining();
if ( remainingSize == (bufferSize-6) ) 
	out.print("getRemaining test Passed");
else { 
	out.println("getRemaining Test Failed");
	out.println ("Expected Buffer Size = " + bufferSize);
	out.println("Remaining Buffer Size = " + remainingSize);
     }
%>

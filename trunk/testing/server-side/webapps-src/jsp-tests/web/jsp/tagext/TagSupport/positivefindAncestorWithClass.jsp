<html>
<title>positivefindAncestorWithClass</title>
<body>
<% 
	/** 	
	Name :positivefindAncestorWithClass
        Description : Test the coordination between nested tags.  A person 
		      can be eligible for only one prize. 
	            
	Result :  The name of the prize winners is printed.
	**/  
%>


<%@ taglib uri="/TestLib.tld" prefix="test"  %>
<test:firstWinner winner="santosh" >
<test:secondWinner winner="santosh" />
<test:secondWinner winner="pierre" />
</test:firstWinner>

</body>
</html>

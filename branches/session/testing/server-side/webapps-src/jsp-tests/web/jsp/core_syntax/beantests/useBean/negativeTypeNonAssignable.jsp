<html>
<title>negativeTypeNonAssignable</title>
<body>
<% /** 	Name : negativeTypeNonAssignable
	Description : we are using 'class' and 'type' together and 'class' 
	              is not assignable to 'type'.
	Result :
**/ %>	 
<!-- using 'class' and 'type' together and 'class' is not assignable to type -->
<% try { %>
<jsp:useBean id="ncounter"  class="core_syntax.beantests.useBean.NewCounter"
type="java.util.Properties" />
<% }catch(java.lang.ClassCastException cle) { out.println("class cast exception");
} %>

</body>
</html>

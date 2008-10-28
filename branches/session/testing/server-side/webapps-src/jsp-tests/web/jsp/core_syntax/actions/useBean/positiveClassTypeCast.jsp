<html>
<title>positiveClassTypeCast</title>
<body>
<% /** 	Name : positiveClassTypeCast
	Description : we are using 'class' and 'type' together and 'class' 
		      is assignable to 'type'. 
	Result :we should get the expected page without an error
**/ %>
<!-- we are using 'class' and 'type' together and 'class' is assignable to type-->
<jsp:useBean id="ncounter"  class="core_syntax.actions.useBean.NewCounter"
type="core_syntax.actions.useBean.Counter" />
<% out.println( ncounter.getCount());  %>
</body>
</html>

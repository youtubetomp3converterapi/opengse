<html>
<title>positiveTypeAssignable</title>
<body>
<% /** 	Name : positiveTypeAssignable
	Description : we are using 'class' and 'type' together and 'class' 
		      is assignable to 'type'. 
	Result :we should get the expected page without an error
**/ %>	 
<!-- we are using 'class' and 'type' together and 'class' is assignable to type-->
<jsp:useBean id="ncounter"  class="core_syntax.beantests.useBean.NewCounter"
type="core_syntax.beantests.useBean.Counter" />
<% out.println( ncounter.getCount());  %>
</body>
</html>

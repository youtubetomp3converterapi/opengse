<html>
<title>negativeDuplicateIDFatalTranslation</title>
<body>
<% /** 	Name : negativeDuplicateIDFatalTranslation
	Description : Verify that duplicate useBean id's
                  within the same translation unit yeild
                  a fatal translation error.
	Result : Fatal translation error
**/ %>
<!-- Declare two beans with the same id -->
<jsp:useBean id="myBean" scope="page" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:useBean id="myBean" scope="page" class="core_syntax.actions.useBean.String_IntBean" />
</body>
</html>

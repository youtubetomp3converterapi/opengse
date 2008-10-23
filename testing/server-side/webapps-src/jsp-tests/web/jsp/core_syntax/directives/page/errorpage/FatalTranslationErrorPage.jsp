<html>
<title>FatalTranslationErrorPage</title>
<body>
<% 
    // The code below should cause the container
    // to throw a fatal translation error.
    if ( exception == null ) {
        out.println( "Exception was null" );
    else {
        out.println( "Exception was not null" );
    }
%>
</body>
</html>

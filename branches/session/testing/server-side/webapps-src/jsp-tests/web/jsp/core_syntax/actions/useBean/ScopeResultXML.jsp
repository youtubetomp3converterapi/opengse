
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[]]></jsp:text>
<jsp:directive.page contentType="text/plain;charset=ISO-8859-1" />
<jsp:text><![CDATA[
]]></jsp:text>

<!--
    Simple JSP to valid scoped objects created with jsp:useBean
-->
<jsp:text><![CDATA[

]]></jsp:text>

<jsp:declaration>
<![CDATA[ 
     public void sendPass( HttpServletResponse resp ) {
        resp.addHeader( "status", "Test Status=PASSED" );
        return;
    }
]]>

</jsp:declaration>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:declaration>
<![CDATA[
    public void sendFail( HttpServletResponse resp, String msg ) {
        resp.addHeader( "status", "Test Status=FAILED - " + msg );
        return;
    }
]]>

</jsp:declaration>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    String _scope = request.getParameter( "scope" );
    String _objId = request.getParameter( "objId" );
   

    if ( _scope.equals( "application" ) ) {
        if ( ( application.getAttribute( _objId ) ) != null ) {
            sendPass( response );
        } else {
            sendFail( response, "Application scoped object not found in ServletContext" );
        }
    }

    if ( _scope.equals( "session" ) ) {
        if ( ( session.getAttribute( _objId ) ) != null ) {
            sendPass( response );
        } else {
            sendFail( response, "Session scoped object not found in HttpSession" );
        }
    }

    if ( _scope.equals( "request" ) ) {
        if ( ( request.getAttribute( _objId ) ) != null ) {
            sendPass( response );
        } else {
            sendFail( response, "Request scoped object not found in HttpServletResponse" );
        }
    }

    if ( _scope.equals( "page" ) ) {
        if ( ( pageContext.getAttribute( _objId ) ) == null ) {
            sendPass( response );
        } else {
            sendFail( response, "Page scoped object found in current page and should not be" );
        }
    }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>

</jsp:root>
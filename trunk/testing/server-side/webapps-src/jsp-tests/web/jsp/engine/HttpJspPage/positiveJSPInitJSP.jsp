<html>
<title>positiveJSPInitJSP</title>
<body>
<%  /**
       Name : positiveJSPInitJSP
       Description: we override the jspInit in the jsp itself
                    and check getServletConfig method works
       Result: we should get the expected string output
  */ %>
<!-- to test HttpJspPage implementation -->
<%! String result; %>
<%! public void jspInit() {
     javax.servlet.ServletConfig con=getServletConfig();
     if(con!=null) {
      result="it works";
     }else {
       result="ServletConfig is null";
    }
  }
%>
<% out.println(result); %>
</body>
</html>

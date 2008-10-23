<html>
<title>positiveSetIndexedProp</title>
<body>
<%
/**
 *  TestCase name : positiveSetIndexedProp
 *  Description   : Here, the setProperty tag is used to set the value for
 *                  an indexed property.An array is declared and defined in a
 *                  scriptlet and then assigned through the setProperty tag, with 
 *                  an expression.
 *  Result        : Expected to set the value of the array.
 */
 %>

<%
//Declaring the array
int[] iAry={24,25,26}; 
%>

<!-- Declaring the bean without body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="intAry" value="<%= iAry %>" />
<!-- Accessing the property through a scriptlet -->

<%
// Accessing the set property.
int[] ary=myBean.getIntAry();
int l=ary.length;
for(int j=0;j<l;j++)
out.println(ary[j]);
%>

</body>
</html> 
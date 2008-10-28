<%--
  Test for defining an int variable.
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<%! public static Integer increment(Integer i) {
         if (i != null)
            return new Integer(i.intValue()+1);
         return new Integer(0);
    }
%>
<%! public static int valueof(Integer i) {
         if (i != null)
            return i.intValue();
         return 0;
    }
%>
            

<x:define  id="i" scope="page" life="nested" >
(1) i was <%= i %>; <% i = increment(i); %> i is now <%= valueof(i) %>
</x:define>

<x:define  id="i" scope="page" life="nested" >
(2) i was <%= i %>; <% i = increment(i); %> i is now <%= valueof(i) %>
</x:define>

<x:define  id="i" scope="page" life="at_begin" >
(3) i was <%= i %>; <% i = increment(i); %> i is now <%= valueof(i) %>
</x:define>

(4) i was <%= i %>; <% i = increment(i); %> i is now <%= valueof(i) %>

<%-- Test that attribute setting is as wanted --%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:count>
<x:count att1="one">
one
</x:count>
<x:count att1="two">
<x:checkCount nested="2"/>
two
</x:count>
<x:checkCount nested="1"/>
</x:count>

<x:count>
<x:count att1="one"/>
<x:count att2="bye">
<x:count att2="three">
<x:checkCount nested="3"/>
</x:count>
</x:count>
</x:count>

<x:checkCount start="7"/>

<%--
  Test that attribute setting is as wanted
  Intermix some different tags
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:count>
<x:count att1="one">
<x:aTag att="foo">
one
<x:checkCount nested="2"/>
</x:aTag>
</x:count>
<x:bTag att="wombat">
<x:bTag att='wombat'>
<x:aTag att="bar"/>
</x:bTag>
</x:bTag>
<x:count att1="two">
<x:count att1="two">
<x:checkCount nested="3"/>
two
</x:count>
</x:count>
<x:checkCount nested="1"/>
</x:count>


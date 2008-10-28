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
<x:aTag att="bar"/>
<x:count att1="two">
<x:checkCount nested="2"/>
two
</x:count>
<x:checkCount nested="1"/>
</x:count>


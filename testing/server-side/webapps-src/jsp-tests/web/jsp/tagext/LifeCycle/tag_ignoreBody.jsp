<%--
 Test for translation-time verification.
 Test that a tag is passed (at translation time) correct TagInfo data.
 Body will be ignored.
--%>
<%@ taglib uri="/TestLib.tld" prefix="x" %>

<x:silly a='3' b="2" c="5" testLibInfo="true">
HI!
</x:silly>
tag_ignoreBody test PASSED

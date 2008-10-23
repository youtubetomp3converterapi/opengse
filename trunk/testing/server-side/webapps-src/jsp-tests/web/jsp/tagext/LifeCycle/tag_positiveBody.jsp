<%-- 
 Test for translation-time verification 
 Test that a tag is passed (at translation time) correct TagInfo data 
--%> 
<%@ taglib uri="/TestLib.tld" prefix="x" %> 
 
<x:silly a='3' b="2" c="5" testLibInfo="true"> 
HI! 
 <x:silly a='3' b="2" c="5" testLibInfo="true"/> 
</x:silly> 
tag_positiveBody test PASSED

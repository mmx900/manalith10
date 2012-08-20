<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<html:html>
	<head>
		<!--html:base /-->
		<base href="/">
		<title>SUB TITLE - <decorator:title default="TITLE" /></title>
		<decorator:head />
		<META HTTP-EQUIV="Content-Language" content="ko">
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">		
	</head>
	<body>
		<decorator:body />
	</body>
</html:html>
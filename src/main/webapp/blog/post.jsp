<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- 경로 설정 -->
<c:set var="templatePath" value="templates/${blog.template}" scope="request" />
<c:set var="uploadPath" value="${option.url}/blog/upload/article" scope="request" />

<!-- 이미지 설정 -->
<c:if test="${! empty blog.titleImage}">
	<c:set var="titleImage" value="upload/title/${param.id}_${blog.titleImage}" scope="request" />
</c:if>
<c:if test="${! empty blog.backgroundImage}">
	<c:set var="backgroundImage" value="upload/background/${param.id}_${blog.backgroundImage}" scope="request" />
</c:if>

<!-- 공통 JS 라이브러리 설정 -->
<c:set var="jscalendar_path" value="javascript/jscalendar-1.0" scope="request" />
<c:set var="jscalendar_target" value="blog.do?id=${param.id}&date=" scope="request" />
<!-- t:flatcal jsPath="${jscalendar_path}" targetURL="${jscalendar_target}" container="calendar" / -->

<c:set var="fckeditor_path" value="javascript/FCKeditor_2.0" scope="request" />
<!-- t:fckeditor jsPath="${fckeditor_path}" container="editor" / -->

<c:if test="${blog.template.hasPostPage}">
	<jsp:include page="templates/${blog.template}/post.jsp" flush="false" />
</c:if>
<c:if test="${! blog.template.hasPostPage}">
	<jsp:include page="templates/default/post.jsp" flush="false" />
</c:if>
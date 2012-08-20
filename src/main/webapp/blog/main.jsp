<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 현재 블로그의 URL 설정 -->
<c:url var="url_blog" value="blog.do" scope="request">
	<c:param name="id" value="${param.id}" />
	<!--c:param name="articleId" value="article.id" /-->
	<!--c:param name="category" value="category" /-->
	<!--c:param name="date" value="date" /-->
</c:url>
<c:set var="url_blog" value="${fn:escapeXml(url_blog)}" scope="request" />

<c:if test="${empty blog}">
	<!-- 블로그가 없을 경우 -->
	<p>${param.id}님은 가입하지 않았거나 블로그를 개설하지 않으셨습니다. <c:if
		test="${param.id == userId}">
		<c:url var="url_blog_create_form" value="blog/admin" scope="request">
			<c:param name="id" value="${param.id}" />
			<c:param name="method" value="createForm" />
		</c:url>
		<c:set var="url_blog_create_form"
			value="${fn:escapeXml(url_blog_create_form)}" scope="request" />

		<p><html:link action="${url_blog_create_form}">블로그 개설</html:link>
	</c:if>
</c:if>

<c:if test="${! empty blog}">
	<!-- 블로그가 있을 경우 -->
	<!-- URL 설정 -->
	<c:url var="url_admin" value="blog/admin.do">
		<c:param name="id" value="${param.id}" />
	</c:url>
	<c:set var="url_admin" value="${fn:escapeXml(url_admin)}"
		scope="request" />

	<c:url var="url_blog_rss" value="blog.do" scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="rss" />
	</c:url>
	<c:set var="url_blog_rss" value="${fn:escapeXml(url_blog_rss)}"
		scope="request" />

	<c:url var="url_article_create_form" value="blog/article.do"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="createForm" />
	</c:url>
	<c:set var="url_article_create_form"
		value="${fn:escapeXml(url_article_create_form)}" scope="request" />

	<c:url var="url_trackback_recieve"
		value="${option.url}/blog/trackback.do" scope="request">
		<c:param name="id" value="${param.id}" />
		<!--c:param name="articleId" value="article.id" /-->
	</c:url>
	<c:set var="url_trackback_recieve"
		value="${fn:escapeXml(url_trackback_recieve)}" scope="request" />

	<c:url var="url_trackback_delete" value="blog/trackbackDelete.do"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<!--c:param name="articleId" value="article.id" /-->
		<!--c:param name="trackbackId" value="trackback.id" /-->
	</c:url>
	<c:set var="url_trackback_delete"
		value="${fn:escapeXml(url_trackback_delete)}" scope="request" />

	<c:url var="url_article_update_form" value="blog/article.do"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="updateForm" />
		<!--c:param name="articleId" value="article.id" /-->
	</c:url>
	<c:set var="url_article_update_form"
		value="${fn:escapeXml(url_article_update_form)}" scope="request" />

	<c:url var="url_article_destroy" value="blog/article.do"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="destroy" />
		<!--c:param name="articleId" value="article.id" /-->
	</c:url>
	<c:set var="url_article_destroy"
		value="${fn:escapeXml(url_article_destroy)}" scope="request" />

	<c:url var="url_trackback_send_form" value="blog/trackbackPingForm.do"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<!--c:param name="articleId" value="article.id" /-->
	</c:url>
	<c:set var="url_trackback_send_form"
		value="${fn:escapeXml(url_trackback_send_form)}" scope="request" />

	<c:url var="url_blog_category" value="blog.do" scope="request">
		<c:param name="id" value="${param.id}" />
		<!--c:param name="category" value="categories.category" /-->
	</c:url>
	<c:set var="url_blog_category"
		value="${fn:escapeXml(url_blog_category)}" scope="request" />

	<c:url var="url_login" value="user.do" scope="request">
		<c:param name="method" value="login" />
	</c:url>
	<c:set var="url_login" value="${fn:escapeXml(url_login)}"
		scope="request" />

	<c:url var="url_logout" value="user.do" scope="request">
		<c:param name="method" value="logout" />
	</c:url>
	<c:set var="url_logout" value="${fn:escapeXml(url_logout)}"
		scope="request" />

	<c:url var="url_comment_create" value="blog/comment.do" scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="create" />
		<!--c:param name="articleId" value="article.id" /-->
	</c:url>
	<c:set var="url_comment_create"
		value="${fn:escapeXml(url_comment_create)}" scope="request" />

	<c:url var="url_comment_destroy_form" value="blog/comment"
		scope="request">
		<c:param name="id" value="${param.id}" />
		<c:param name="method" value="destroyForm" />
		<!--c:param name="articleId" value="article.id" /-->
		<!--c:param name="commentId" value="comment.id" /-->
	</c:url>
	<c:set var="url_comment_destroy_form"
		value="${fn:escapeXml(url_comment_destroy_form)}" scope="request" />

	<!-- 경로 설정 -->
	<c:set var="templatePath" value="blog/templates/${blog.template}"
		scope="request" />
	<c:set var="uploadPath" value="blog/upload/article" scope="request" />

	<!-- 이미지 설정 -->
	<c:if test="${! empty blog.titleImage}">
		<c:set var="titleImage"
			value="blog/upload/title/${param.id}_${blog.titleImage}"
			scope="request" />
	</c:if>
	<c:if test="${! empty blog.backgroundImage}">
		<c:set var="backgroundImage"
			value="blog/upload/background/${param.id}_${blog.backgroundImage}"
			scope="request" />
	</c:if>

	<!-- 공통 JS 라이브러리 설정 -->
	<c:set var="jscalendar_path" value="blog/javascript/jscalendar-1.0"
		scope="request" />
	<c:set var="jscalendar_target" value="blog.do?id=${param.id}&date="
		scope="request" />
	<!-- t:flatcal jsPath="${jscalendar_path}" targetURL="${jscalendar_target}" container="calendar" / -->

	<!-- 첨부 시작 -->
	<jsp:include page="templates/${blog.template}/main.jsp" flush="false" />
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:xhtml />
<fmt:setLocale value="ko_KR"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko-KR">
<head>
	<meta name="ROBOTS" content="NOINDEX, NOFOLLOW">
	<title>${blog.title}</title>
	<style type="text/css">
	@charset "utf-8";
	@import url('${templatePath}/main.css');
	</style>
</head>
<body>
	<div id="login_form">
		<c:if test="${empty userId}">
		<html:form action="${url_login}" focus="id" onsubmit="return validateUserForm(this)">
		<p>
			이름 : <html:text property="id" size="12" maxlength="12" />
			<br />암호 : <html:password property="password" size="12" maxlength="12" />
			<br /><html:submit value="확인" /> <html:link action="preregister">새로이 합류</html:link>
		</p>
		</html:form>
		
		<html:javascript formName="userForm" />
		
		</c:if>
		<c:if test="${! empty userId}">
		<p>${userId} / ${userName}
		<br />
			<c:if test="${blog.owner == userId}">
				<html:link action="${url_admin}">Admin</html:link>
				<html:link action="${url_article_create_form}">New Article</html:link>
			</c:if>
			<c:forEach var="author" items="${blog.authors}">
			<c:if test="${author == userId}">
				<html:link action="${url_article_create_form}">New Article</html:link>
			</c:if>
			</c:forEach>
		<br /><html:link action="${url_logout}">로그아웃</html:link>
		</p>
		</c:if>
	</div>
</body>
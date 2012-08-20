<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:xhtml />
<fmt:setLocale value="ko_KR"/>

<html>
<head>
</head>
<body>
<c:if test="${empty userId}">
로그인하세요.
<html:form action="user?method=login" focus="id">
	<html:text property="id" />
	<html:password property="password" />
	<html:submit value="확인" />
</html:form>
<c:if test="${option.allowRegister == true}">
	<html:link action="preregister">유저 등록</html:link>
</c:if>
</c:if>
<c:if test="${! empty userId}">
<p>${userId} 아이디로 로그인 되었습니다. ${userName}님, 환영합니다!
<html:link action="user?method=logout">로그아웃</html:link>
</c:if>

<p><html:link action="/admin">관리자</html:link>

<p>사용자 목록
<table>
	<c:if test="${empty userList}">
	<tr>
		<td align=center>현재 등록된 사용자가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="user" items="${userList}">
	<tr>
		<td><html:link action="blog?id=${user.id}">${user.id}</html:link></td>
	</tr>
	</c:forEach>
</table>

<p>Recent Entries
<table>
	<c:if test="${empty recentEntries}">
	<tr>
		<td align=center>등록된 블로그가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="entry" items="${recentEntries}">
	<tr>
		<td>
			<a href="${entry.itemLink}" target="_blank">${entry.itemTitle}</a> :
			<a href="${entry.channelLink}" target="_blank">${entry.channelTitle}</a> :
			<fmt:formatDate value="${entry.itemPubDate}" pattern="yyyy-MM-dd a hh:mm:ss"/>
		</td>
	</tr>	
	</c:forEach>
</table>

<p>카테고리 목록
<table>
	<c:if test="${empty categoryList}">
	<tr>
		<td align=center>카테고리가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="category" items="${categoryList}">
	<tr>
		<td><html:link action="list?method=category&id=${category.name}">${category.name} : ${category.description}</html:link></td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
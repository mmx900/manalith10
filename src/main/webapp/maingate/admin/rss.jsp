<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p>RSS 소스리스트 추가.</p>

<html:form action="rss?method=sourceAdd" enctype="multipart/form-data" focus="title">
	제목 : <html:text property="title" value="${source.title}"/><br />
	분류 : <input type="text" id="category" name="category" value="${source.category}"/>

	<select
		onChange="document.getElementById('category').value = this.value">
		<option value="">새로 만들기</option>
		<c:if test="${! empty categories}"><option value="">--</option></c:if>
		<c:forEach var="category" items="${categories}">
		<option>${category}</option>
		</c:forEach>
	</select>
	<br />
	
	설명 : <html:textarea property="description" value="${source.description}"/><br />
	홈페이지 경로 : <html:text property="webUrl" value="${source.webUrl}"/><br />
	RSS의 경로 : <html:text property="rssUrl" value="${source.rssUrl}"/><br />
	
	<p><html:submit value="확인"/></p>
</html:form>

<p>RSS 주소들</p>
<table>
	<c:if test="${empty sources}">
	<tr>
		<td align="center">현재 등록된 주소가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="source" items="${sources}">
	<tr>
		<td>${source.category}
			<a href="${source.webUrl}" target="_blank">${source.title} :
			${source.description}</a>
			<a href="${source.rssUrl}" target="_blank">[RSS]</a>
			<html:link action="rss?method=sourceDelete&sourceId=${source.id}">삭제</html:link></td>
	</tr>
	</c:forEach>
</table>
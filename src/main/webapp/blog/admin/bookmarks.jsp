<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p>바로가기 목록</p>

<table>
	<c:if test="${empty categories}">
	<tr>
		<td align=center>현재 등록된 바로가기가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="category" items="${categories}">
	<tr>
		<td>${category}</td>
	</tr>
		<c:forEach var="bookmark" items="${bookmarks}">
		<c:if test="${category eq bookmark.category}">
		<tr>
			<td>&nbsp;&nbsp;&nbsp;
				<a href="${bookmark.url}" target="_blank">${bookmark.title} : ${bookmark.url} : ${bookmark.description}</a>
				<c:if test="${! empty bookmark.rssUrl}"><a href="${bookmark.rssUrl}" target="_blank">[RSS]</a></c:if>
				<html:link action="/blog/admin/bookmark?method=destroy&id=${param.id}&bookmarkId=${bookmark.id}">[제거]</html:link>
				</td>
		</tr>
		</c:if>
		</c:forEach>
	</c:forEach>
</table>

<p>새로 추가하기 : *가 붙은 것은 필수 항목입니다.</p>

<html:form action="/blog/admin/bookmark?method=create&id=${param.id}" focus="title">
	<html:hidden property="bookmarkId" value="${bookmark.id}"/>
	<p>* Title : <html:text property="title" />
	<br />* category : 
	<input type="text" id="category" name="category" value="${bookmark.category}"/>
	<select
		onChange="document.getElementById('category').value = this.value">
		<option value="">새로 만들기</option>
		<c:if test="${! empty categories}"><option value="">--</option></c:if>
		<c:forEach var="category" items="${categories}">
		<option>${category}</option>
		</c:forEach>
	</select>
	<br />* URL : <html:text property="url" value="http://" />
	<br />Rss URL : <html:text property="rssUrl" />
	<br />Banner URL : <html:text property="imageUrl" />
	<br />Description : <html:text property="description" />
	<br /><html:submit value="살살" />
	</p>
</html:form>
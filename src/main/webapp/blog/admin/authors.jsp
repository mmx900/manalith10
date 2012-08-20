<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p>저자 목록

<html:form action="/blog/admin/author?method=search&id=${param.id}">
<input name="keyword" value="${param.keyword}" />
<html:submit value="검색" />
</html:form>

<c:if test="${param.method eq 'search'}">
검색된 유저들 :
<table>
	<c:if test="${empty users}">
	<tr>
		<td align=center>검색된 유저가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="user" items="${users}">
	<tr>
		<td><html:link action="blog?id=${user.id}">${user.id} : ${user.name}</html:link>
			<html:link action="blog?id=${user.id}">[BLOG]</html:link>
			<html:link action="/blog/admin/author?method=add&id=${param.id}&authorId=${user.id}">[저자로 등록]</html:link>
			</td>
	</tr>
	</c:forEach>
</table>
</c:if>

현재 등록된 저자들 :
<table>
	<c:if test="${empty authors}">
	<tr>
		<td align=center>현재 등록된 저자가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="user" items="${authors}">
	<tr>
		<td><html:link action="blog?id=${user.id}">${user.id} : ${user.name}</html:link>
			<html:link action="blog?id=${user.id}">[BLOG]</html:link>
			<html:link action="/blog/admin/author?method=delete&id=${param.id}&authorId=${user.id}">[저자 권한 제거]</html:link>
			</td>
	</tr>
	</c:forEach>
</table>
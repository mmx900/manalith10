<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p>사용자 목록
<table>
	<c:if test="${empty userList}">
	<tr>
		<td align=center>현재 등록된 사용자가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="user" items="${userList}">
	<tr>
		<td><html:link action="blog?id=${user.id}">${user.id} : ${user.name} : ${user.email}</html:link></td>
	</tr>
	</c:forEach>
</table>
<html:errors />
<p>사용자 추가
<html:form action="user?method=add" focus="id">
	아이디(*) : <html:text property="id" /><br />
	비밀번호(*) : <html:password property="password" /><br />
	이름 : <html:text property="name" /><br />
	email 주소 : <html:text property="email" /><br />
	<html:submit value="확인" />
</html:form>
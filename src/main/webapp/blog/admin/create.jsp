<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

${userId}님의 새로운 블로그를 생성합니다.
<!-- enctype="multipart/form-data" -->
<html:form action="blog?method=create&id=${param.id}" enctype="multipart/form-data" focus="title">
	<html:hidden property="owner" value="${userId}" />
	제목 : <html:text property="title" />
	<br />
	URL : <html:text property="url" value="${option.url}/blog.do?id=${userId}"/>
	<br />
	설명 : <html:textarea property="description" />
	<br />
	템플릿 : <html:select property="template">
		<c:forEach var="templateName" items="${templates}">
			<html:option value="${templateName}"/>
		</c:forEach>
	</html:select><br />
	페이지별로 보여줄 항목 수 : <html:text property="pageSize" size="2" />
	<br />
	RSS 허용 : <html:checkbox property="allowRSS" />
	<br />
	대문 이미지 : <html:file property="titleImage" />
	<br />
	배경 이미지 : <html:file property="backgroundImage" />
	<br />
	<html:submit value="확인"/>
</html:form>
<p>이미지는 템플릿에 따라 적용되지 않을 수도 있습니다.</p>
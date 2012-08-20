<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:url var="url_delete_image" value="blog/admin" scope="request">
  <c:param name="id" value="${param.id}" />
  <c:param name="method" value="options" />
</c:url>
<c:set var="url_delete_image" value="${fn:escapeXml(url_delete_image)}" />

<html:form action="/blog?method=update&id=${param.id}" enctype="multipart/form-data" focus="title">
	<html:hidden property="owner" value="${userId}"/>
	제목 : <html:text name="blog" property="title" />
	<br />
	URL : <html:text name="blog" property="url" />
	<br />
	설명 ; <html:textarea name="blog" property="description" />
	<br />
	템플릿 : <html:select name="blog" property="template">
		<c:forEach var="templateName" items="${templates}">
			<html:option value="${templateName}"/>
		</c:forEach>
	</html:select>
	<br />
	페이지별로 보여줄 항목 수 : <html:text name="blog" property="pageSize" size="2" />
	<br />
	RSS 허용 : <html:checkbox name="blog" property="allowRSS" />
	<br />
	대문 이미지 <c:if test="${! empty blog.template.titleImageWidth}">(${blog.template.titleImageWidth}x${blog.template.titleImageHeight})</c:if> : <html:file name="blog" property="titleImage" />
	<br />
	<c:if test="${! empty blog.titleImage}">
		현재 대문 이미지인 <a href="../blog/upload/title/${param.id}_${blog.titleImage}">${blog.titleImage}</a>을(를) 덮어씁니다.
		<html:link action="${url_delete_image}&amp;del=titleImage">[삭제]</html:link>
		<br />
	</c:if>
	배경 이미지 : <html:file name="blog" property="backgroundImage" />
	<br />
	<c:if test="${! empty blog.backgroundImage}">
		현재 배경 이미지인 <a href="../blog/upload/background/${param.id}_${blog.backgroundImage}">${blog.backgroundImage}</a>을(를) 덮어씁니다.
		<html:link action="${url_delete_image}&amp;del=backgroundImage">[삭제]</html:link>
		<br />
	</c:if>
	<html:submit value="확인"/>
</html:form>
<p>이미지는 템플릿에 따라 적용되지 않을 수도 있습니다.</p>
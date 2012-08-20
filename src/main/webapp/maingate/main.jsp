<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- URL 설정 -->
<c:url var="url_login" value="user.do" scope="request">
  <c:param name="method" value="login" />
</c:url>
<c:set var="url_login" value="${fn:escapeXml(url_login)}" scope="request" />

<c:url var="url_logout" value="user.do" scope="request">
  <c:param name="method" value="logout" />
</c:url>
<c:set var="url_logout" value="${fn:escapeXml(url_logout)}" scope="request" />

<!-- 템플릿 지정 -->
<c:set var="template" value="${option.template}" scope="request"/>

<!-- 템플릿 경로 지정 -->
<c:set var="templatePath" value="maingate/templates/${template}" scope="request"/>

<!-- 첨부 시작 -->
<jsp:include page="templates/${template}/main.jsp" flush="false" />
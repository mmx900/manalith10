<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/blog/comment?method=destroy&id=${param.id}" focus="password">
	<html:hidden property="commentId" value="${param.commentId}"/>
	<html:hidden property="articleId" value="${param.articleId}"/>
	<html:password property="password"/>
	<html:submit value="확인"/>
</html:form>
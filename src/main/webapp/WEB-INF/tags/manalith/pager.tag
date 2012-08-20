<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<common:pager 
	totalArticleCount="${blog.totalArticleCount}" 
	pageSize="${blog.pageSize}"
	targetUrl="${url_blog}&amp;page=%i" />
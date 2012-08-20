<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType = "text/xml; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="en_US"/>
<rss version="2.0">
	<channel>
		<title>${blog.owner}'s ${blog.title}</title>
		<link>${option.url}/blog.do?id=${param.id}</link>
		<description>${blog.description}</description>
		<copyright/>
		<generator>manalith 0.1</generator>
		<c:forEach var="article" items="${blog.articles}">
		<item>
			<title>${article.title}</title>
			<link>${option.url}/blog.do?id=${param.id}&amp;articleId=${article.id}</link>
			<description>${article.contents}</description>
			<pubDate><fmt:formatDate value="${article.date}" pattern="EEE, d MMM yyyy HH:mm:ss Z"/></pubDate>
			<category>${article.category}</category>
		</item>
		</c:forEach>
	</channel>
</rss>
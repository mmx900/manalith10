<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="ko_KR"/>

<html>
<body>
<c:if test="${empty userId}">
로그인하세요.
<html:form action="user?method=login" focus="id">
	<html:text property="id" />
	<html:password property="password" />
	<html:submit value="확인" />
</html:form>
<html:link action="preregister">유저 등록</html:link>
</c:if>
<c:if test="${! empty userId}">
<p>${userId} 아이디로 로그인 되었습니다. ${userName}님, 환영합니다!
<br><c:if test="${blog.owner == userId}">
	<html:link action="${url_admin}">Admin</html:link>
	<html:link action="${url_article_create_form}">New Article</html:link>
	</c:if>
</c:if>

<c:if test="${empty blog}">
<p>${param.id}님은 가입하지 않았거나 블로그를 개설하지 않으셨습니다.
<p><c:if test="${param.id == userId}"><html:link action="${url_blog_create_form}">블로그 개설</html:link></c:if>
</c:if>

<c:if test="${! empty blog}">
<p>${blog.owner} 님이 만든 ${blog.title} 블로그의 최근 게시물 ${blog.pageSize}/${blog.totalArticleCount}개
<html:link action="${url_blog_rss}">[RSS]</html:link>

<table>
	<c:if test="${empty blog.articles}">
	<tr>
		<td align=center>등록된 블로그가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="article" items="${blog.articles}">
	<tr><td><u>${article.title}</u></td></tr>
	<tr>
		<td>${article.id} : ${article.author} :
			<fmt:formatDate value="${article.date}" pattern="yyyy-MM-dd a hh:mm:ss"/></td>
	</tr>
	<tr>
		<td>${article.contents}</td>
	</tr>
	<c:if test="${!empty userId}">
	<tr>
		<td>
		<c:if test="${article.author == userId}">
			<html:link action="${url_article_update_form}&articleId=${article.id}">Edit</html:link>
			<html:link action="${url_trackback_send_form}&articleId=${article.id}">Ping</html:link>
			<html:link action="${url_article_destroy}&articleId=${article.id}">Delete</html:link>
		</c:if>
		</td>
	</tr>
	</c:if>
	<c:forEach var="file" items="${article.files}">
	<tr>
		<td>${file.id} : ${file.name} : ${file.size} Bytes</td>
	</tr>
	</c:forEach>
	<tr>
		<td>comments(${article.totalCommentCount})  trackback(${article.totalTrackbackCount})</td>
	</tr>
	<tr>
		<td>이 글의 트랙백 주소 : ${blog.url}trackback?id=${param.id}&amp;articleId=${article.id}</td>
	</tr>
	<c:forEach var="trackback" items="${article.trackbacks}">
	<tr>
		<td>${trackback.title} : ${trackback.url} : ${trackback.excerpt} :
			<fmt:formatDate value="${trackback.date}" pattern="yyyy-MM-dd a hh:mm:ss"/> :
			<html:link action="${url_trackback_delete}&articleId=${article.id}&trackbackId=${trackback.id}">[X]</html:link></td>
	</tr>
	</c:forEach>
	<c:forEach var="comment" items="${article.comments}">
	<tr>
		<td>${comment.name} : ${comment.homepage} : ${comment.contents} :
			<fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd a hh:mm:ss"/> :
			<html:link action="/blog/comment?method=destroyForm&id=${param.id}&articleId=${article.id}&commentId=${comment.id}">[X]</html:link></td>
	</tr>
	</c:forEach>
	<tr>
		<td>
		<html:form action="${url_comment_create}">
			<html:hidden property="articleId" value="${article.id}"/>
			이름 : <html:text property="name"/>
			비밀번호 : <html:password property="password"/>
			홈페이지 : <html:text property="homepage"/><br>
			<html:textarea property="contents"/>
			<html:submit value="확인"/>
		</html:form>
		</td>
	</tr>
	</c:forEach>
</table>

<p>저자 목록
<table>
	<c:if test="${empty blog.authors}">
	<tr>
		<td align=center>현재 등록된 저자가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="user" items="${blog.authors}">
	<tr>
		<td><html:link action="blog?id=${user.id}">${user.id} : ${user.name}</html:link>
			<html:link action="blog?id=${user.id}">[BLOG]</html:link></td>
	</tr>
	</c:forEach>
</table>

<p>카테고리 목록
<table>
	<c:if test="${empty blog.categories}">
	<tr>
		<td align=center>카테고리가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="category" items="${blog.categories}">
	<tr>
		<td><html:link action="${url_blog_category}&category=${category}">${category}</html:link></td>
	</tr>
	</c:forEach>
</table>

<p>북마크 목록
<table>
	<c:if test="${empty bookmarkList}">
	<tr>
		<td align=center>카테고리가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="bookmark" items="${bookmarkList}">
	<tr>
		<td><html:link action="${bookmark.url}">${bookmark.name} : ${category.description}</html:link></td>
	</tr>
	</c:forEach>
</table>
</c:if>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/tem9p" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/manalith" prefix="manalith" %>
<!-- html:xhtml -->
<!--
html:form 태그에 name이 없기 때문에 생기는 자바스크립트 문제들이 있으므로,
xhtml 사용은 스트러츠 다음 버전을 기약한다.
관련 : http://issues.apache.org/bugzilla/show_bug.cgi?id=35127
-->
<fmt:setLocale value="ko_KR"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko-KR">
<head>
	<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
	<title>${blog.title}</title>
	<style type="text/css">
	@charset "utf-8";
	@import url('${templatePath}/main.css');
	BODY{
		background-image:url('${backgroundImage}');
	}
	</style>
	<script type="text/javascript" src="${templatePath}/main.js"></script>
</head>

<body>
<table>
<tr>
	<td id="left">
	<div id="header">
		${blog.title} : ${blog.pageSize}/${blog.totalArticleCount}
		: <html:link action="${url_blog_rss}">RSS</html:link>
	</div>

	<c:if test="${empty blog.articles}">
		게시물이 없습니다.
	</c:if>

	<c:forEach var="article" varStatus="i" items="${blog.articles}">
	<div class="article">
		<div class="title">${article.title}</div>
		<div>${article.num} : ${article.author} : <fmt:formatDate value="${article.date}" pattern="yyyy-MM-dd a hh:mm:ss"/></div>
		<div class="contents">${article.contents}</div>
		
		<c:if test="${!empty userId}">
			<div class="author_menu">
			<c:if test="${article.author == userId}">
				<html:link action="${url_article_update_form}&amp;articleId=${article.id}">Edit</html:link>
				<html:link action="${url_trackback_send_form}&amp;articleId=${article.id}">Ping</html:link>
				<html:link action="${url_article_destroy}&amp;articleId=${article.id}">Delete</html:link>
			</c:if>
			</div>
		</c:if>
		
		<div class="counts">
			<a href="#" onclick="show_comments(${i.index})">comments(${article.totalCommentCount})</a>
			<a href="#" onclick="show_trackbacks(${i.index})">trackback(${article.totalTrackbackCount})</a>
			<a href="#" onclick="show_files(${i.index})">file(${fn:length(article.files)})</a>
		</div>
		
		<div class="files" id="files${i.index}">
			<c:forEach var="file" items="${article.files}">
				<div class="file"><a href="${uploadPath}/${file.name}">${file.name}</a> : ${file.size} Bytes</div>
			</c:forEach>
		</div>
		
		<div class="trackbacks" id="trackbacks${i.index}">
			<div class="url_trackback">이 글의 트랙백 주소 : ${url_trackback_recieve}&amp;articleId=${article.id}</div>
			<c:forEach var="trackback" items="${article.trackbacks}">
			<div class="trackback">${trackback.title} : ${trackback.url} : ${trackback.excerpt} :
					<fmt:formatDate value="${trackback.date}" pattern="yyyy-MM-dd a hh:mm:ss"/> :
					<html:link action="${url_trackback_delete}&amp;articleId=${article.id}&amp;trackbackId=${trackback.id}">[X]</html:link>
			</div>
			</c:forEach>
		</div>
		
		<div class="comments" id="comments${i.index}">
			<c:forEach var="comment" items="${article.comments}">
			<div class="comment">
				<a href="${comment.homepage}" target="_blank">${comment.name}</a> 님이 
					<fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd a hh:mm:ss"/> 분에 남기신 글 :
					<br /> ${comment.contents} :
				<html:link action="${url_comment_destroy_form}&amp;articleId=${article.id}&amp;commentId=${comment.id}">[X]</html:link>
			</div>
			</c:forEach>
			<div class="comment_form">
				<html:form action="${url_comment_create}">
					<p><html:hidden property="articleId" value="${article.id}"/>
					이름 : <html:text property="name"/>
					비밀번호 : <html:password property="password"/><br />
					홈페이지 : <html:text property="homepage"/><br />
					<html:textarea property="contents" rows="5" cols="30"/>
					<html:submit value="확인"/>
					</p>
				</html:form>
			</div>
		</div>
		
	</div>
	</c:forEach>
	
	<div id="pages">
	<manalith:pager />
	</div>

</td><td id="right">

	<div id="information">
		<p>
		<c:if test="${! empty titleImage}">
			<img id="titleImage" src="${titleImage}" alt="blog title image" /><br />
		</c:if>
		"${blog.description}"<br />
		by [${blog.owner}]
		
		</p>
	</div>
	
	<manalith:calendar container="blogCalendar" themePath="${templatePath}/calendar.css" displayWeekNumbers="false">
		<div id="blogCalendar"></div>
	</manalith:calendar>
	
	<div id="login_form">
		<c:if test="${empty userId}">
		<html:javascript formName="userForm"/>
		<html:form action="${url_login}" focus="id" onsubmit="return validateUserForm(this)">
		<p>
			이름 : <html:text property="id" size="12" maxlength="12" />
			<br />암호 : <html:password property="password" size="12" maxlength="12" />
			<br /><html:submit value="확인" />
			<c:if test="${option.allowRegister == true}">
				<html:link action="preregister">새로이 합류</html:link>
			</c:if>
		</p>
		</html:form>
		
		</c:if>
		<c:if test="${! empty userId}">
		<p>${userId} / ${userName}
		<br />
			<c:if test="${blog.owner == userId}">
				<html:link action="${url_admin}">Admin</html:link>
				<html:link action="${url_article_create_form}">New Article</html:link>
			</c:if>
			<c:forEach var="author" items="${blog.authors}">
			<c:if test="${author == userId}">
				<html:link action="${url_article_create_form}">New Article</html:link>
			</c:if>
			</c:forEach>
		<br /><html:link action="${url_logout}">로그아웃</html:link>
		</p>
		</c:if>
	</div>
	
	<div id="recent_comments">
		최근 댓글
		<div>
		<c:forEach var="comment" items="${recentComments}">
			<html:link action="${url_blog}&amp;articleId=${comment.articleId}">${fn:substring(comment.contents,0,10)}...</html:link>
			<br />
		</c:forEach>
		</div>
	</div>
	
	<div id="recent_trackbacks">
		최근 트랙백
		<div>
		<c:forEach var="trackback" items="${recentTrackbacks}">
			<html:link action="${url_blog_category}&amp;category=${category}">${fn:substring(trackback.url,0,10)}</html:link>
		</c:forEach>
		</div>
	</div>
	
	<c:if test="${! empty blog.authors}">
	<div id="authors">
		저자들
		<div>
		<c:forEach var="user" items="${blog.authors}">
			<html:link action="${url_blog}&amp;author=${user.id}">${user.id} : ${user.name}</html:link>
				<html:link action="blog?id=${user.id}">[BLOG]</html:link>
				<br />
		</c:forEach>
		</div>
	</div>
	</c:if>
	
	<div id="categories">
		분류
		<div>
		<html:link action="${url_blog_category}">전체</html:link>
		<br />
		<c:forEach var="category" items="${blog.categories}">
			<html:link action="${url_blog_category}&amp;category=${category}">${category}</html:link>
			<br />
		</c:forEach>
		</div>
	</div>
	
	
	<c:if test="${! empty bookmarkCategories}">
	<div id="bookmarks">
		즐겨찾기
		<c:forEach var="category" items="${bookmarkCategories}">
		<div class="bookmark">
			${category}
			<ul>
			<c:forEach var="bookmark" items="${blog.bookmarks}">
			<c:if test="${category eq bookmark.category}">
				<li><a href="${bookmark.url}" target="_blank">${bookmark.title}</a></li>
			</c:if>
			</c:forEach>
			</ul>
		</div>
		</c:forEach>
	</div>
	</c:if>
	
	<div id="banners">
	<p>
	  <a href="http://validator.w3.org/check?uri=referer"><img
	      src="${templatePath}/banner/xhtml10.png"
	      alt="Valid XHTML 1.0!" width="80" height="15" /></a>
	   <br /><a href="http://getfirefox.com/"
			title="Get Firefox - The Browser, Reloaded."><img
			src="${templatePath}/banner/firefox_80x15.png"
			width="80" height="15" alt="Get Firefox" /></a>
	   <br /><a href="http://dean.edwards.name/IE7/"
			title="IE7 {css2: auto;}"><img
			src="${templatePath}/banner/ie7.gif"
			width="80" height="15" alt="IE7 ENHANCED" /></a>
	   <br /><a href="${url_blog_rss}"
			title="RSS 2.0"><img
			src="${templatePath}/banner/rss20_logo.gif"
			width="80" height="15" alt="RSS 2.0" /></a>
	   <br /><a href="http://www.nosoftwarepatents.com"
			title="No Software Patents!"><img
			src="${templatePath}/banner/nswpat80x15.gif"
			width="80" height="15" alt="No Software Patents!" /></a>
		<br />
    </p>
</div>

</td>
</tr>
<tr>
<td colspan="2" id="copyright">
${blog.template.fullName}:TemplateBy<a href="${blog.template.authorURL}">${blog.template.author}</a>
</td>
</tr>
</table>

</body>
</html>
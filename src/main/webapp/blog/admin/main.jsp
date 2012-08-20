<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p>${param.id}님의 블로그 :
<p><html:link action="/blog/admin?id=${param.id}&method=options">Options</html:link>
<p><html:link action="/blog/admin/author?id=${param.id}">Authors</html:link>
<p><html:link action="/blog/admin/bookmark?id=${param.id}">Bookmarks</html:link>
<p><html:link action="/blog/admin?id=${param.id}&method=categories">Categories</html:link>
<p>Statistics
<p>Import / Export
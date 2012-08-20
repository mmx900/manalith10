<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<p>관리자 메뉴. <html:link action="adminAuth?method=logout">[관리자 로그아웃]</html:link>
<!--p><html:link action="admin?method=options">Options</html:link></p-->
<p><html:link action="admin?method=users">User</html:link>
<p><html:link action="admin?method=rss">RSS</html:link>
<p>Statistics
<p>Import / Export
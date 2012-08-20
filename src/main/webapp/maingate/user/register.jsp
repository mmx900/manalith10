<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<html:form action="user?method=register" focus="id">
	아이디 : <html:text property="id" /><br>
	비밀번호 : <html:password property="password" /><br>
	이름 : <html:text property="name" /><br>
	email 주소 : <html:text property="email" /><br>
	<html:submit value="확인" />
</html:form>
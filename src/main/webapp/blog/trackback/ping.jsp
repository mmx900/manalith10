<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/blog/ping?id=${param.id}" focus="url">
<html:hidden property="articleId" value="${param.articleId}"/>
<p>URL : <html:text property="url"/>

<p>인코딩 : <html:select property="encoding">
	<html:option value="UTF-8"/>
	<html:option value="CP949"/>
	<html:option value="ISO8859-1"/>
	<html:option value="MS949"/>
	<html:option value="KSC5601"/>
	<html:option value="euc-kr"/>
</html:select>
<br>Soojung은 UTF-8을, TatterTools는 CP949를 이용해 전송하세요.
<p><html:submit value="확인"/>
</html:form>
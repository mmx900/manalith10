<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="totalArticleCount" required="true" %>
<%@ attribute name="pageSize" required="true" %>
<%@ attribute name="targetUrl" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
int t = Integer.parseInt(totalArticleCount);
int p = Integer.parseInt(pageSize);
if(t>p && p != 0){
	int val = t / p;
	if(t % p != 0) val++;
	
	for(int i=1;i<=val;i++){
		request.setAttribute("i","" + i);
		if(targetUrl != null){
%>
	<a href="${fn:replace(targetUrl,'%i',i)}">${i}</a>
<%
		}else{
%>
	${i}
<%
		}
	}
}
%>

<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="test" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="ext" value="${fn:substring(test,fn:length(test)-3,fn:length(test))}" />
<c:set var="ext4" value="${fn:substring(test,fn:length(test)-4,fn:length(test))}" />
<c:if test="${(! (empty test)) && (ext eq 'png') || (ext eq 'PNG') || (ext eq 'jpg') || (ext eq 'JPG') || (ext eq 'gif') || (ext eq 'GIF') || (ext eq 'bmp') || (ext eq 'BMP') || (ext4 eq 'jpeg') || (ext4 eq 'JPEG')}">
<jsp:doBody />
</c:if>
<?xml version="1.0" encoding="iso-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<response>
<error>${error}</error>
<c:if test="${! empty msg}"><message>${msg}</message></c:if>
</response>
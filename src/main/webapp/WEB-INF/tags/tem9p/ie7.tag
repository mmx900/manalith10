<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="jsPath" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- compliance patch for microsoft browsers -->
<!--[if lt IE 7]>
<script src="${jsPath}/ie7-standard.js" type="text/javascript" />
<![endif]-->
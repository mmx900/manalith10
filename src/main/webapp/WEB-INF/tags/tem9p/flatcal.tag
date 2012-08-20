<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="jsPath" required="true" %>
<%@ attribute name="container" required="true" %>
<%@ attribute name="targetURL" required="true" %>
<%@ attribute name="callback" required="false" %>
<%@ attribute name="lang" required="false" %>
<%@ attribute name="theme" required="false" %>
<%@ attribute name="themePath" required="false" %>
<%@ attribute name="date" required="false" %>
<%@ attribute name="dateStatusHandler" required="false" %>
<%@ attribute name="displayArea" required="false" %>
<%@ attribute name="displayWeekNumbers" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:doBody />

<style type="text/css">
	<c:if test="${empty theme && empty themePath}">
		@import url(${jsPath}/calendar-win2k-1.css);
	</c:if>
	<c:if test="${! empty theme}">
		<c:if test="${theme eq 'aqua'}">
			@import url(${jsPath}/skins/aqua/theme.css);
		</c:if>
		<c:if test="${! (theme eq 'aqua')}">
			@import url(${jsPath}/calendar-${empty theme ? 'win2k-1' : theme}.css);
		</c:if>
	</c:if>
	<c:if test="${! empty themePath}">
		@import url(${themePath});
	</c:if>
</style>

<script type="text/javascript" src="${jsPath}/calendar.js"></script>
<script type="text/javascript" src="${jsPath}/lang/calendar-${empty lang ? 'ko-utf8' : lang}.js"></script>
<script type="text/javascript" src="${jsPath}/calendar-setup.js"></script>

<script type="text/javascript">
  function dateChanged(calendar) {
    // Beware that this function is called even if the end-user only
    // changed the month/year.  In order to determine if a date was
    // clicked you can use the dateClicked property of the calendar:
    if (calendar.dateClicked) {
      // OK, a date was clicked, redirect to /yyyy/mm/dd/index.php
      var y = calendar.date.getFullYear();
      var m = calendar.date.getMonth() + 1;     // integer, 1..12
      if(m < 10) m = "0" + m;					// string, 01..12
      var d = calendar.date.getDate();      // integer, 1..31
      if(d < 10) d = "0" + d;					// string, 01..12
      // redirect...
      window.location = "${targetURL}" + y + m + d;
    }
  };

	<c:if test="${! empty date}">
	var strCurDate = ${date} + "";
	var curDate = new Date(
		strCurDate.substring(0,4),
		strCurDate.substring(4,6) - 1,
		strCurDate.substring(6,8)
		);
	
	</c:if>

  Calendar.setup(
    {
      flat : "${container}" // ID of the parent element
		,flatCallback : ${empty callback ? 'dateChanged' : callback} // our callback function
		<c:if test="${! empty date}">
		,date : curDate //19700101...
		</c:if>
		<c:if test="${! empty displayArea}">
		,displayArea : ${displayArea}
		</c:if>
		<c:if test="${! empty displayWeekNumbers}">
		,weekNumbers : ${displayWeekNumbers}
		</c:if>
		<c:if test="${! empty dateStatusHandler}">
		,dateStatusFunc	: ${dateStatusHandler}
		</c:if>
    }
  );
</script>
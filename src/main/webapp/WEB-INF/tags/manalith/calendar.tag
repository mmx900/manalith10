<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="container" required="true" %>
<%@ attribute name="lang" required="false" %>
<%@ attribute name="theme" required="false" %>
<%@ attribute name="themePath" required="false" %>
<%@ attribute name="displayWeekNumbers" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags/tem9p" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
function disallowDate(date, y, m, d){
	var days = new Array(
	<c:if test="${! empty blog.calendar.years}">
		<c:forEach var="year" items="${blog.calendar.years}">
			<c:forEach var="month" items="${year.months}">
				<c:forEach var="day" items="${month.days}">
					new Date(${year}, ${month} -1, ${day}),
				</c:forEach>
			</c:forEach>
		</c:forEach>
		new Date()
	</c:if>
	);
	
	var result = true;
	for(var i=0;i<days.length;i++){
		if(days[i].getFullYear() == date.getFullYear()
			&& days[i].getMonth() == date.getMonth()
			&& days[i].getDate() == date.getDate()){
			result = false;
			break;
		}
	}
	
	return result;
}
</script>

<c:if test="${! empty theme}">
	<t:flatcal jsPath="${jscalendar_path}" 
		targetURL="${jscalendar_target}" 
		container="${container}" 
		lang="${empty lang ? 'ko-utf8' : lang}" 
		theme="${theme}"
		displayWeekNumbers="${displayWeekNumbers}" 
		dateStatusHandler="disallowDate"
		date="${param.date}">
		<jsp:doBody />
	</t:flatcal>
</c:if>

<c:if test="${! empty themePath}">
	<t:flatcal jsPath="${jscalendar_path}" 
		targetURL="${jscalendar_target}" 
		container="${container}" 
		lang="${empty lang ? 'ko-utf8' : lang}" 
		themePath="${themePath}" 
		displayWeekNumbers="${displayWeekNumbers}" 
		dateStatusHandler="disallowDate"
		date="${param.date}">
		<jsp:doBody />
	</t:flatcal>
</c:if>
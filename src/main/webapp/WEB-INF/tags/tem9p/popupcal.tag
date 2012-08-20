<%@ tag body-content="empty" pageEncoding="UTF-8" %>
<%@ attribute name="jsPath" required="true" %>
<%@ attribute name="targetURL" required="true" %>
<%@ attribute name="callback" required="false" %>
<%@ attribute name="lang" required="false" %>
<%@ attribute name="theme" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style type="text/css">@import url(${jsPath}/calendar-${empty theme ? 'win2k-1' : theme}.css);</style>
<script type="text/javascript" src="${jsPath}/calendar.js"></script>
<script type="text/javascript" src="${jsPath}/lang/calendar-${empty lang ? 'ko' : lang}.js"></script>
<script type="text/javascript" src="${jsPath}/calendar-setup.js"></script>
<script type="text/javascript">
  Calendar.setup(
    {
      inputField  : "data",         // ID of the input field
      ifFormat    : "%m %d, %Y",    // the date format
      button      : "trigger"       // ID of the button
    }
  );
</script>
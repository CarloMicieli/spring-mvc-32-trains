<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>						
						
<%@ attribute name="msg" required="true" rtexprvalue="true" type="com.trenako.web.controllers.ControllerMessage" %>

<c:if test="${not empty msg}">
<div class="alert alert-${msg.type}">
	<s:message code="${msg.message}" text="${msg.message}" arguments="${msg.args}"/>
</div>
</c:if>
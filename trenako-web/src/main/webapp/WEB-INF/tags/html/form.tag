<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<%@ attribute name="model" required="true" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="actionUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="method" required="true" rtexprvalue="true" %>

<form:form id="form" class="form-horizontal" method="${method}" action="${actionUrl}" modelAttribute="${model}" >
	<fieldset>
		<html:alert msg="${message}" />
		
		<jsp:doBody />
		
		<div class="form-actions">
			<html:saveButton/>
			<html:resetButton/>
		</div>
	</fieldset>
</form:form>
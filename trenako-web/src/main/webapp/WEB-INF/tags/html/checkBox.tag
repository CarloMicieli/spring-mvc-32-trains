<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="false" %>
<%@ attribute name="helpLabel" required="true" rtexprvalue="false" %>

<s:bind path="${bindContext}.${name}">
<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
	<form:label path="${name}" cssClass="control-label">
		<s:message code="${label}" />:
	</form:label>
	<div class="controls">
		<label class="checkbox">
			<form:checkbox path="${name}"/>
			<s:message code="${helpLabel}" /> 
		</label>
		<form:errors path="${name}" element="span" cssClass="help-inline"/>
	</div>
</div>
</s:bind>
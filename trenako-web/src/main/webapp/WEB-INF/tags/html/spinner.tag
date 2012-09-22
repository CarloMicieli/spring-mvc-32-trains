<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="step" required="true" rtexprvalue="true" %>
<%@ attribute name="min" required="true" rtexprvalue="true" %>
<%@ attribute name="max" required="true" rtexprvalue="true" %>
<%@ attribute name="isRequired" required="true" rtexprvalue="true" type="java.lang.Boolean" %>

<s:bind path="${name}">
	<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
		<form:label path="${name}" cssClass="control-label">
			<s:message code="${label}" />:
		</form:label>
		<div class="controls">
			<c:choose>
		      	<c:when test="${isRequired}">
		      		<form:input path="${name}" type="number" step="${step}" min="${min}" max="${max}" cssClass="input-xlarge focused" required="required"/>
		      	</c:when>
				<c:otherwise>
		      		<form:input path="${name}" type="number" step="${step}" min="${min}" max="${max}" cssClass="input-xlarge focused"/>
		      	</c:otherwise>
		    </c:choose>
			<form:errors path="${name}" element="span" cssClass="help-inline"/>
		</div>
	</div>
</s:bind>

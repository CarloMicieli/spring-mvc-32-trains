<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="isRequired" required="true" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="false" %>

<s:bind path="${bindContext}.${name}">
	<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
		<form:label path="${name}" cssClass="control-label">
			<s:message code="${label}" />:
		</form:label>
		<div class="controls">
			<c:choose>
		      	<c:when test="${isRequired}">
		      		<form:input path="${name}" cssClass="input-xlarge focused" placeholder="${placeholder}" required="required"/>
		      	</c:when>
				<c:otherwise>
		      		<form:input path="${name}" cssClass="input-xlarge focused" placeholder="${placeholder}"/>
		      	</c:otherwise>
		    </c:choose>
			<form:errors path="${name}" element="span" cssClass="help-inline"/>
		</div>
	</div>
</s:bind>
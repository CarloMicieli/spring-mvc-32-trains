<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="false" %>
<%@ attribute name="optionsLabel" required="true" rtexprvalue="false" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="itemLabel" required="false" rtexprvalue="false" type="java.lang.String" %>
<%@ attribute name="itemValue" required="false" rtexprvalue="false" type="java.lang.String" %>	
	
<s:bind path="${bindContext}.${name}">
<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
	<form:label path="${name}" cssClass="control-label">
		<s:message code="${label}" />:
	</form:label>
	<div class="controls">
		<form:select path="${name}">
			<s:message var="options" code="${optionsLabel}"/>
			<form:option value="" label="${options}"/>
				<c:choose>
					<c:when test="{itemLabel != && itemValue != null}">
						<form:options items="${countries}" itemLabel="${itemLabel}" itemValue="${itemValue}" />
					</c:when>
					<c:otherwise>
						<form:options items="${items}"/>	
					</c:otherwise>
				</c:choose>
		</form:select>
		<form:errors path="${name}" element="span" cssClass="help-inline"/>
	</div>
</div>
</s:bind>
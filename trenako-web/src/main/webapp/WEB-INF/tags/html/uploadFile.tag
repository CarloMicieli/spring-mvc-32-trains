<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="false" %>
<%@ attribute name="helpLabel" required="true" rtexprvalue="false" %>

<s:bind path="${bindContext}.${name}">
<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
	<label class="control-label" for="file">
		<s:message code="${label}" />:
	</label>
	<div class="controls">
		<input class="input-file" id="file" name="file" type="file">
		<form:errors path="${name}" element="span" cssClass="help-inline"/>
		<p class="help-block">
			<s:message code="${helpLabel}" />
		</p>
	</div>
</div>
</s:bind>
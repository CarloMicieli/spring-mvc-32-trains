<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.lang.Iterable" %>

<fieldset class="embedded ${name}">
	<legend><s:message code="${title}" text="${title}"/></legend>

	<div class="control-group">
		<label class="control-label" for="inlineCheckboxes">
			<s:message code="${label}" text="${label}" />:
		</label>
		<div class="controls">
			<c:forEach var="it" items="${items}">
				<label class="checkbox inline">
					<form:checkbox path="${name}" value="${it}"/>
					${it}
				</label>
			</c:forEach>
		</div>
	</div>
</fieldset>
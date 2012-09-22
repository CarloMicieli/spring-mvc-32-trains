<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>							

<%@ attribute name="editUrl" required="true" rtexprvalue="true" %>

<a href="${editUrl}" class="btn">
	<i class="icon-pencil"></i>
	<s:message code="button.edit.label" />
</a>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		<s:message code="scales.show.title.label" arguments="${scale.name}" />
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scales.header.title.label" />
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="scales.edit.title.label" arguments="${scale.name}" /></h1>
				</div>
    			<dl>
    				<dt><s:message code="scale.name.label" />:</dt>
    				<dd>${scale.name} (${scale.ratioText})</dd>
    				<dt><s:message code="scale.ratio.label" />:</dt>
    				<dd><s:eval expression="scale.ratio"/></dd>
    				<dt><s:message code="scale.gauge.label" />:</dt>
    				<dd><s:eval expression="scale.gauge"/></dd>
    				<dt><s:message code="scale.narrow.label" />:</dt>
    				<dd>${scale.narrow}</dd>
    				<dt><s:message code="scale.standards.label" />:</dt>
    				<dd>${scale.standardsList}</dd>
    				<dt><s:message code="scale.lastModified.label" />:</dt>
    				<dd>${scale.lastModified}</dd>    				
    			</dl>
				
				<s:url value="/admin/scales/{id}" var="scalesUrl">
					<s:param name="id" value="${scale.id}" />
				</s:url>
				<form:form action="${scalesUrl}" method="delete" modelAttribute="scale" >
					<form:hidden path="id"/>
					<div class="form-actions">
						<s:url var="editUrl" value="/admin/scales/{id}/edit">
				           	<s:param name="id" value="${scale.id}" />
						</s:url>
						<a href="${editUrl}" class="btn">
							<i class="icon-pencil"></i>
							<s:message code="button.edit.label" />
						</a>

						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<s:message code="button.delete.label" />
						</button>
					</div>
				</form:form>
    		</div>
    	</div>
	</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Scales | ${scale.name}
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scale.label" text="Scale"/>
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" text="Scale list"/>
							</a>
						</li>
					</ul>
				</div>
			</div>
			
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="edit.label" text="Edit" /> ${scale.name}</h1>
				</div>
    			<dl>
    				<dt><s:message code="scale.name.label" text="Name" />:</dt>
    				<dd>${scale.name}</dd>
    				<dt><s:message code="scale.ratio.label" text="Ratio" />:</dt>
    				<dd>${scale.ratio}</dd>
    				<dt><s:message code="scale.gauge.label" text="Gauge" />:</dt>
    				<dd>${scale.gauge}</dd>
    				<dt><s:message code="scale.narrow.label" text="Is narrow" />:</dt>
    				<dd>${scale.narrow}</dd>
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
							<s:message code="edit.label" text="Edit" />
						</a>

						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<s:message code="delete.label" text="Delete" />
						</button>
					</div>
				</form:form>
    		</div>
    	</div>
	</body>
</html>
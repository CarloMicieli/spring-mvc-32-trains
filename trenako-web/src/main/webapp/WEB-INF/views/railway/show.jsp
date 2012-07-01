<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Railways | ${railway.name}
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Railway</li>
						<li class="active">
							<a href="<s:url value="/admin/railways" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="railways.list.label" text="Railways" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/railways/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="create.railway.label" text="Create new railway" />
							</a>
						</li>						
					</ul>
				</div>
			</div>
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="edit.label" text="Edit" /> "${railway.name}"</h1>
				</div>
    			<dl>
    				<dt><s:message code="railway.name.label" text="Name" />:</dt>
    				<dd>${railway.name}</dd>
    				<dt><s:message code="railway.companyName.label" text="Company name" />:</dt>
    				<dd>${railway.companyName}</dd>
    				<dt><s:message code="railway.country.label" text="Country" />:</dt>
    				<dd>${railway.country}</dd>
    				<dt><s:message code="railway.operatingSince.label" text="Operating since" />:</dt>
    				<dd>${railway.operatingSince}</dd>
					<dt><s:message code="railway.operatingUntil.label" text="Operating until" />:</dt>
    				<dd>${railway.operatingUntil}</dd>
    				<dt><s:message code="railway.logo.label" text="Logo" />:</dt>
    				<dd>
    					<s:url value="/images/railway/{id}" var="logoUrl">
							<s:param name="id" value="${railway.id}" />
						</s:url>
						<img src="${logoUrl}" alt="Not found"/>
    				</dd>
    			</dl>
				
				<s:url value="/admin/railways/{id}" var="railwaysUrl">
					<s:param name="id" value="${railway.id}" />
				</s:url>
				<form:form action="${railwaysUrl}" method="delete" modelAttribute="railway" >
					<form:hidden path="id"/>
					<div class="form-actions">
						<s:url var="editUrl" value="/admin/railways/{id}/edit">
				           	<s:param name="id" value="${railway.id}" />
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
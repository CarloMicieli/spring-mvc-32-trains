<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Brands | ${brand.name}
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Brand</li>
						<li class="active">
							<a href="<s:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="brand.list.label" text="Brands list" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/brands/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="create.brand.label" text="Create new brand" />
							</a>
						</li>						
					</ul>
				</div>
			</div>
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="edit.label" text="Edit" /> ${brand.name}</h1>
				</div>
    			<dl>
    				<dt><s:message code="brand.name.label" text="Name" />:</dt>
    				<dd>${brand.name}</dd>
    				<dt><s:message code="brand.description.label" text="Description" />:</dt>
    				<dd>${brand.description}</dd>
    				<dt><s:message code="brand.emailAddress.label" text="Email Address" />:</dt>
    				<dd>${brand.emailAddress}</dd>
    				<dt><s:message code="brand.website.label" text="Website" />:</dt>
    				<dd>${brand.website}</dd>
					<dt><s:message code="brand.industrial.label" text="Industrial" />:</dt>
    				<dd>${brand.industrial}</dd>
    				<dt><s:message code="brand.logo.label" text="Logo" />:</dt>
    				<dd>
    					<s:url value="/images/brand/{id}" var="logoUrl">
							<s:param name="id" value="${brand.id}" />
						</s:url>
						<img src="${logoUrl}" alt="Not found"/>
    				</dd>
    			</dl>
				
				<s:url value="/admin/brands/{id}" var="brandsUrl">
					<s:param name="id" value="${brand.id}" />
				</s:url>
				<form:form action="${brandsUrl}" method="delete" modelAttribute="brand" >
					<form:hidden path="id"/>
					<div class="form-actions">
						<s:url var="editUrl" value="/admin/brands/{id}/edit">
				           	<s:param name="id" value="${brand.id}" />
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
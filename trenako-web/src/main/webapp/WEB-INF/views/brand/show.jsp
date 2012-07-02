<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		<s:message code="brands.show.title.label" arguments="${brand.name}" />
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="brands.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="brands.list.label" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/brands/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="brands.create.brand.label" />
							</a>
						</li>						
					</ul>
				</div>
			</div>
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="brand.show.title.label" arguments="${brand.name}" /> </h1>
				</div>
    			<dl>
    				<dt><s:message code="brand.name.label" />:</dt>
    				<dd>${brand.name}</dd>
    				<dt><s:message code="brand.description.label" />:</dt>
    				<dd>${brand.description}</dd>
    				<dt><s:message code="brand.emailAddress.label" />:</dt>
    				<dd>${brand.emailAddress}</dd>
    				<dt><s:message code="brand.website.label" />:</dt>
    				<dd>${brand.website}</dd>
					<dt><s:message code="brand.industrial.label" />:</dt>
    				<dd>${brand.industrial}</dd>
    				<dt><s:message code="brand.logo.label" />:</dt>
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
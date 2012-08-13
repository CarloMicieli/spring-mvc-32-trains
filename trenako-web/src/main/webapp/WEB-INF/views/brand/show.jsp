<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
	<head>
		<title>
			<s:message code="brands.show.title.label" arguments="${brand.name}" />
		</title>
	</head>
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
				
				<div class="row-fluid">
					<div class="span4">
						<div class="thumbnail">
							<img src="http://placehold.it/260x180" alt="">
							<div class="caption">
								<h5></h5>
								<p>Logo: ${brand.name}</p>
								<p><a href="#" class="btn btn-primary">Save</a> <a href="#" class="btn">Cancel</a></p>
							</div>
						</div>
					</div>
					<div class="span8">
		    			<dl>
		    				<dt><s:message code="brand.name.label" />:</dt>
		    				<dd>${brand.name}</dd>
		    				<dt><s:message code="brand.companyName.label" />:</dt>
		    				<dd>${brand.companyName}</dd>		    				
		    				<dt><s:message code="brand.emailAddress.label" />:</dt>
		    				<dd>${brand.emailAddress}</dd>
		    				<dt><s:message code="brand.website.label" />:</dt>
		    				<dd>${brand.website}</dd>
							<dt><s:message code="brand.industrial.label" />:</dt>
		    				<dd>${brand.industrial}</dd>
		    			</dl>
		    			
		    			<hr/>
		    			
		    			<h4><s:message code="brand.descriptions.label" /></h4>
		    			<dl>
		    				<c:forEach var="item" items="${brand.description}">
		    					<dt>${item.key}:</dt><dd>${item.value}</dd>
		    				</c:forEach>
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
    		</div>
    	</div>
	</body>
</html>
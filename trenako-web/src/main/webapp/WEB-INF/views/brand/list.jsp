<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>Brands</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Brand</li>
						<li class="active">
							<a href="<spring:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								Brands List
							</a>
						</li>
						<li>
							<a href="<spring:url value="/admin/brands/new" />" class="create">
								<i class="icon-plus"></i>
								Create brand
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><spring:message code="brand.list" text="Brands list" /></h1>
				</div>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><spring:message code="brand.name" text="Name" /></th>
							<th><spring:message code="brand.description" text="Description" /></th>
							<th><spring:message code="brand.website" text="Website" /></th>
							<th><spring:message code="brand.emailAddress" text="Email Address" /></th>
							<th><spring:message code="brand.industrial" text="Industrial" /></th>
							<th><spring:message code="brand.address" text="Address" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="brand" items="${brands}">
						<tr>
							<td>${brand.name}</td>
							<td>${brand.description}</td>
							<td>${brand.website}</td>
							<td>${brand.emailAddress}</td>
							<td>${brand.industrial}</td>
							<td>${brand.address}</td>

							<spring:url var="showUrl" value="/admin/brands/{id}">
				            	<spring:param name="id" value="${brand.id}" />
							</spring:url>
							<td class="link">
								<a href="${showUrl}" class="btn btn-small">Show &raquo;</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
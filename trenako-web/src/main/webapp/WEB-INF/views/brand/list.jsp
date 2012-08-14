<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="brands.list.title.label" />
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
					<h1><s:message code="brands.list.label" /></h1>
				</div>
				
				<c:if test="${not empty message}">
					<div id="message" class="info">${message}</div>
				</c:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="brand.name.label" /></th>
							<th><s:message code="brand.companyName.label" /></th>
							<th><s:message code="brand.website.label" /></th>
							<th><s:message code="brand.emailAddress.label" /></th>
							<th><s:message code="brand.industrial.label" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="brand" items="${brands.content}">
						<tr>
							<td>${brand.name}</td>
							<td>${brand.companyName}</td>
							<td>${brand.website}</td>
							<td>${brand.emailAddress}</td>
							<td>${brand.industrial}</td>

							<s:url var="showUrl" value="/admin/brands/{slug}">
				            	<s:param name="slug" value="${brand.slug}" />
							</s:url>
							<td class="link">
								<a href="${showUrl}" class="btn btn-small">
									<s:message code="button.show.label" />
								</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				<tk:pagination page="${brands}"/>
			</div>
		</div>
	</body>
</html>
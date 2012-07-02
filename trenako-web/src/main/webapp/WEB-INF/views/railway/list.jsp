<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title><s:message code="railways.label" text="Railways"/></title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="railway.label" text="Railway"/>
						</li>
						<li class="active">
							<a href="<s:url value="/admin/railways" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="railways.list.label" text="Railways"/>
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/railways/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="railway.create.label" text="Create railway"/>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="railways.list.label" text="Railways" /></h1>
				</div>
				
				<c:if test="${not empty message}">
					<div id="message" class="info">${message}</div>
				</c:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="railway.name" text="Name" /></th>
							<th><s:message code="railway.companyName" text="Company name" /></th>
							<th><s:message code="railway.country" text="Country" /></th>
							<th><s:message code="railway.operatingSince" text="Operating since" /></th>
							<th><s:message code="railway.operatingUntil" text="Operating until" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="railway" items="${railways.content}">
						<tr>
							<td>${railway.name}</td>
							<td>${railway.companyName}</td>
							<td>${railway.country}</td>
							<td>${railway.operatingSince}</td>
							<td>${railway.operatingUntil}</td>

							<s:url var="showUrl" value="/admin/railways/{id}">
				            	<s:param name="id" value="${railway.id}" />
							</s:url>
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
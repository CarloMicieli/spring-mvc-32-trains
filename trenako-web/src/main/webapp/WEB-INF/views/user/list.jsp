<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="users.list.title.label" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="users.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/users" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="users.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="users.list.label" /></h1>
				</div>
				
				<html:alert msg="${message}"></html:alert>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="user.slug.label" /></th>
							<th><s:message code="user.displayName.label" /></th>
							<th><s:message code="user.locked.label" /></th>
							<th><s:message code="user.enabled.label" /></th>
							<th><s:message code="user.roles.label" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="user" items="${users.content}">
						<tr>
							<td>${user.slug}</td>
							<td>${user.displayName}</td>
							<td>${user.locked}</td>
							<td>${user.enabled}</td>
							<td>${user.roles}</td>

							<s:url var="showUrl" value="/admin/users/{slug}/edit">
				            	<s:param name="slug" value="${user.slug}" />
							</s:url>
							<td class="link">
								<a href="${showUrl}" class="btn btn-small">
									<s:message code="button.edit.label" />
								</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				<tk:pagination page="${users}"/>
			</div>
		</div>
	</body>
</html>
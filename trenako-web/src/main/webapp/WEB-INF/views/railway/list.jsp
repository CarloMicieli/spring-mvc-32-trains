<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="railways.list.title.label"/></title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="railways.header.title.label" />
						</li>
						<li class="active">
							<a href="<s:url value="/admin/railways" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="railways.list.label" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/railways/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="railways.create.railway.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="railways.list.label" /></h1>
				</div>
				
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="railway.name.label" /></th>
							<th><s:message code="railway.companyName.label" /></th>
							<th><s:message code="railway.country.label" /></th>
							<th><s:message code="railway.operatingSince.label" /></th>
							<th><s:message code="railway.operatingUntil.label" /></th>
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

							<td class="link">
								<s:url var="showUrl" value="/admin/railways/{slug}">
					            	<s:param name="slug" value="${railway.slug}" />
								</s:url>
								<a href="${showUrl}" class="btn btn-small">
									<s:message code="button.show.label" />
								</a>
								
								<s:url var="editUrl" value="/admin/railways/{slug}/edit">
					            	<s:param name="slug" value="${railway.slug}" />
								</s:url>								
								<a href="${editUrl}" class="btn btn-small">
									<s:message code="button.edit.label" />
								</a>
							</td>							
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				<tk:pagination page="${railways}"/>
			</div>
		</div>
	</body>
</html>
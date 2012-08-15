<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="scales.list.title.label" /></title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scales.header.title.label" />
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/scales/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="scales.create.scale.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="scales.list.label" /></h1>
				</div>
				
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="scale.name.label" /></th>
							<th><s:message code="scale.ratio.label" /></th>
							<th><s:message code="scale.gauge.label" /></th>
							<th><s:message code="scale.narrow.label" /></th>
							<th><s:message code="scale.standards.label" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="scale" items="${scales.content}">
						<tr>
							<td>${scale.name}</td>
							<td><s:eval expression="scale.ratio"/></td>
							<td><s:eval expression="scale.gauge"/></td>
							<td>${scale.narrow}</td>
    						<td>${scale.standardsList}</td>

							<s:url var="showUrl" value="/admin/scales/{slug}">
				            	<s:param name="slug" value="${scale.slug}" />
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
				
				<tk:pagination page="${scales}"/>
			</div>
		</div>
	</body>
</html>
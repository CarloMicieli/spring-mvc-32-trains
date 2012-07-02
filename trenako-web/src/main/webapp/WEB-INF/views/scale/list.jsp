<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title><s:message code="scales.label" text="Scales"/></title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scale.label" text="Scale"/>
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" text="Scale list"/>
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/scales/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="scale.create.label" text="Create scale"/>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="scales.list.label" text="Scales list" /></h1>
				</div>
				
				<c:if test="${not empty message}">
					<div id="message" class="info">${message}</div>
				</c:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th><s:message code="scale.name" text="Name" /></th>
							<th><s:message code="scale.ratio" text="Ratio" /></th>
							<th><s:message code="scale.gauge" text="Gauge" /></th>
							<th><s:message code="scale.narrow" text="Is Narrow" /></th>
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

							<s:url var="showUrl" value="/admin/scales/{id}">
				            	<s:param name="id" value="${scale.id}" />
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
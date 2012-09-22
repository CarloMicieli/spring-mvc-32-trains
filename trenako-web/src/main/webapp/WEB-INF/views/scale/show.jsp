<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="scales.show.title.label" arguments="${scale.name}" />
		</title>
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
					</ul>
				</div>
			</div>
			
    		<div class="span9">
				<div class="page-header">
					<h1><s:message code="scales.show.title.label" arguments="${scale.name}" /></h1>
					<s:message code="scale.lastModified.label" /> <strong><tk:period since="${scale.lastModified}"/></strong>
				</div>
				
    			<dl class="dl-horizontal">
    				<dt><s:message code="scale.name.label" />:</dt>
    				<dd>${scale.name}</dd>
    				<dt><s:message code="scale.description.local.label" />:</dt>
    				<dd><tk:eval expression="${scale.description}"/></dd>				
    				<dt><s:message code="scale.ratio.label" />:</dt>
    				<dd><s:eval expression="scale.ratioText"/></dd>
    				<dt><s:message code="scale.gauge.label" />:</dt>
    				<dd><s:eval expression="scale.gauge"/> mm</dd>
    				<dt><s:message code="scale.narrow.label" />:</dt>
    				<dd>${scale.narrow}</dd>
    				<dt><s:message code="scale.standards.label" />:</dt>
    				<dd>${scale.standardsList}</dd>
    			</dl>
				
				<hr />
								
				<s:url value="/admin/scales/{slug}" var="deleteUrl">
					<s:param name="slug" value="${scale.slug}" />
				</s:url>
				<form:form action="${deleteUrl}" method="delete" modelAttribute="scale" >
					<form:hidden path="id"/>
					<div class="form-actions">
						<s:url var="editUrl" value="/admin/scales/{slug}/edit">
				           	<s:param name="slug" value="${scale.slug}" />
						</s:url>
						<html:editButton editUrl="${editUrl}"/>
						<html:deleteButton/>
					</div>
				</form:form>
    		</div>
    	</div>
	</body>
</html>
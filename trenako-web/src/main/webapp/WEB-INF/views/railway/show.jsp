<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="railways.show.title.label" arguments="${railway.name}" />
		</title>
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
					<h1>
						${railway.name}
						<small>(${railway.companyName})</small>
					</h1>
					<s:message code="brand.lastModified.label" /> <strong><tk:period since="${railway.lastModified}"/></strong>
				</div>
				
				<html:alert msg="${message}"/>
				
				<div class="row-fluid">
					<div class="span12">
						<dl class="dl-horizontal">
							<dt><s:message code="railway.description.label" />:</dt>
							<dd><tk:eval expression="${railway.description}" /></dd>
							<dt><s:message code="railway.country.label"/>:</dt>
							<dd>${railway.country}</dd>
							<dt><s:message code="railway.operatingSince.label" />:</dt>
							<dd>${railway.operatingSince}</dd>
							<dt><s:message code="railway.operatingUntil.label" />:</dt>
							<dd>${railway.operatingUntil}</dd>
		    			</dl>
		    		</div>
		    	</div>
		    	
		    	<div class="row-fluid">
					<s:url value="/admin/railways/{id}" var="deleteUrl">
						<s:param name="id" value="${railway.id}" />
					</s:url>
					<form:form action="${deleteUrl}" method="delete" modelAttribute="railway" >
						<form:hidden path="id"/>
						<div class="form-actions">
							<s:url var="editUrl" value="/admin/railways/{slug}/edit">
					           	<s:param name="slug" value="${railway.slug}" />
							</s:url>
						<html:editButton editUrl="${editUrl}"/>
						<html:deleteButton/>
						</div>
					</form:form>
				</div>
				
				<hr/>
				
		      	<s:url var="uploadUrl" value="/admin/railways/upload"/>
				<s:url value="/images/railway_{slug}" var="imgUrl">
					<s:param name="slug" value="${railway.slug}" />
				</s:url>
				<s:url value="/images/th_railway_{slug}" var="thumbUrl">
					<s:param name="slug" value="${railway.slug}" />
				</s:url>
				<html:upload uploadUrl="${uploadUrl}" name="${railway.name}" thumbUrl="${thumbUrl}" imgUrl="${imgUrl}"/>
    		</div>
    	</div>
	</body>
</html>
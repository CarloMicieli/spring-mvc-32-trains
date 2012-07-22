<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.brands.title" text="Brands"/></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.title" text="Browse"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="browse.brands.title" text="Brands"/></li>
	</ul>

	<div class="page-header">
		<h1><s:message code="browse.brands.header.title" text="Brands" /></h1>
		<small><s:message code="browse.brands.header.subtitle" text="Select a model railway brand" /></small>
	</div>

	<c:forEach var="brand" items="${brands}">
	<div class="row-fluid">
		<div class="span2"></div>
		<div class="span2">
			<s:url value="/images/brand/{id}" var="logoUrl">
			<s:param name="id" value="${brand.id}" />
			</s:url>
			<img src="${logoUrl}" alt="Not found"/>
		</div>
		<div class="span4">
			<h3>${brand.name}</h3>
			<dl class="dl-horizontal">
				<dt><s:message code="brand.description.label" /></dt>
				<dd>${brand.description}</dd>
				<dt><s:message code="brand.website.label" /></dt>
				<dd>${brand.website}</dd>
			</dl>
		</div>
		<div class="span4">
			<s:url var="brandUrl" value="/browse/brands/{slug}">
				<s:param name="slug" value="${brand.slug}"/>
			</s:url>
			<a class="btn btn-info" href="${brandUrl}">
				<s:message code="button.view.details.label" text="View details"/>
			</a>			
		</div>
	</div>
	<hr/>
	</c:forEach>
	
</body>
</html>
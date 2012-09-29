<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.era.title" arguments="${era.label}" /></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
		</li>
		<li>
			<s:url value="/browse/eras" var="erasUrl"/>
	    	<a href="${erasUrl}"><s:message code="browse.breadcrumb.eras.label"/></a> <span class="divider">/</span>
		</li>		
	  	<li class="active">${era.label}</li>
	</ul>

	<div class="page-header">
		<h1>${era.label} <small>(${era.description})</small></h1>
	</div>

	<div class="row-fluid">
		<div class="span3 offset1">
			<strong><s:message code="browse.era.brands.label"/></strong>
			<p><s:message code="browse.era.brands.subtitle" arguments="${era.label}"/></p>
		</div>
		<div class="span8">
			<ul class="unstyled">
			<c:forEach var="brand" items="${brands}">
				<s:url var="brandUrl" value="/rs/brand/{brand}/era/{era}">
					<s:param name="brand" value="${brand.slug}"/>
					<s:param name="era" value="${era.key}"/>
				</s:url>
				<li><a class="browse" href="${brandUrl}" title="${brand.label}">${brand.name}</a></li> 
			</c:forEach>
			</ul>
		</div>
	</div>

	<hr />
	
	<div class="row-fluid">
		<div class="span3 offset1">
			<strong><s:message code="browse.era.railways.label"/></strong>
			<p><s:message code="browse.era.railways.subtitle" arguments="${era.label}"/></p>
		</div>
		<div class="span8">
			<ul class="unstyled">
			<c:forEach var="railway" items="${railways}">
				<s:url var="railwayUrl" value="/rs/railway/{railway}/era/{era}">
					<s:param name="railway" value="${railway.slug}"/>
					<s:param name="era" value="${era.key}"/>
				</s:url>
				<li><a class="browse" href="${railwayUrl}" title="${railway.label}">${railway.name}</a></li> 
			</c:forEach>
			</ul>
		</div>
	</div>

</body>
</html>
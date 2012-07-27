<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.category.title" arguments="${category.label}" /></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
		</li>
		<li>
			<s:url value="/browse/categories" var="categoriesUrl"/>
	    	<a href="${categoriesUrl}"><s:message code="browse.breadcrumb.categories.label"/></a> <span class="divider">/</span>
		</li>		
	  	<li class="active">${category.label}</li>
	</ul>

	<div class="row-fluid">
		<div class="span12">
			<div class="page-header">
				<h1>${category.label}</h1>
				<small>${category.description}</small>
			</div>
		</div>
	</div>
	
	<hr/>
	
	<div class="row-fluid">
			<div class="span3"></div>
			<div class="span8">
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.category.brands.label"/></h3>
						<p><s:message code="browse.category.brands.subtitle" arguments="${category.label}"/></p>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="brand" items="${brands}">
							<s:url var="brandUrl" value="/rs/brand/{brand}/category/{category}">
								<s:param name="brand" value="${brand.slug}"/>
								<s:param name="category" value="${category.key}"/>
							</s:url>
							<li><a class="browse" href="${brandUrl}" title="${brand.label}">${brand.name}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.category.railways.label"/></h3>
						<p><s:message code="browse.category.railways.subtitle" arguments="${category.label}"/></p>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="railway" items="${railways}">
							<s:url var="railwayUrl" value="/rs/railway/{railway}/category/{category}">
								<s:param name="railway" value="${railway.slug}"/>
								<s:param name="category" value="${category.key}"/>
							</s:url>
							<li><a class="browse" href="${railwayUrl}" title="${railway.label}">${railway.name}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="span1"></div>
		</div>
		
		<p/>
	
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="browse.brand.title" arguments="${brand.name}"/></title>
		<meta name="browse" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/browse" var="browseUrl"/>
		    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/browse/brands" var="brandsUrl"/>
		    	<a href="${brandsUrl}"><s:message code="browse.breadcrumb.brands.label"/></a> <span class="divider">/</span>
			</li>		
		  	<li class="active">${brand.name}</li>
		</ul>
		
		<div class="page-header">
			<h1>${brand.name} <small>(
			<c:choose>
				<c:when test="${brand.industrial}">
					<s:message code="brand.industrial.label"/>
				</c:when>
				<c:otherwise>
					<s:message code="brand.brass.label"/>
				</c:otherwise>
			</c:choose>)</small></h1>
		</div>
	
		<div class="row-fluid">
			<div class="span2">
				<s:url value="/images/brand_{slug}" var="logoUrl">
					<s:param name="slug" value="${brand.slug}" />
				</s:url>
				<img src="${logoUrl}" alt="Not found"/>
			</div>
			<div class="span10">
				<h3>${brand.companyName}</h3>
				<tk:eval expression="${brand.description}"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row-fluid">
			<div class="span10 offset2">
				<tk:categoriesList brand="${brand}"/>
			</div>
		</div>
		
	</body>
</html>
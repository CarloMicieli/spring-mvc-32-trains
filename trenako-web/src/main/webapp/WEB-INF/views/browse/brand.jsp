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
	
		<div class="row-fluid">
			<div class="span2">
				<div class="page-header">
					<h1>${brand.name}</h1>
					<small>${brand.companyName}</small>
				</div>
			</div>
			<div class="span9">
				<div class="row-fluid">
					<div class="span3">
						<img src="http://placehold.it/250x120" alt="">
					</div>
					<div class="span9">
						${brand.description}
					</div>
				</div>
				<hr/>
			</div>
			<div class="span1"></div>
		</div>
	
		<div class="row-fluid">
			<div class="span2"></div>
			<div class="span9">
				<tk:categoriesList brand="${brand}"/>
			</div>
			<div class="span1"></div>
		</div>
	
		<p/>
		
	</body>
</html>
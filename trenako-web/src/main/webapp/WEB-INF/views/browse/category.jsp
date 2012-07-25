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
		<div class="span4">
			<div class="page-header">
				<h1>${category.label}</h1>
				<small></small>
			</div>
		</div>
		<div class="span7">
			<div class="row-fluid">
				<div class="span3">
				</div>
				<div class="span9">
					${category.description}
				</div>
			</div>
			<hr/>
		</div>
		<div class="span1"></div>
	</div>
	
</body>
</html>
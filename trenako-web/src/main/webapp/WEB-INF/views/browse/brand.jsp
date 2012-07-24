<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title>${brand.name}</title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.title" text="Browse"/></a> <span class="divider">/</span>
		</li>
		<li>
			<s:url value="/browse/brands" var="brandsUrl"/>
	    	<a href="${brandsUrl}"><s:message code="browse.brands.title" text="Brands"/></a> <span class="divider">/</span>
		</li>		
	  	<li class="active">${brand.name}</li>
	</ul>

	<div class="page-header">
		<h1>${brand.name}</h1>
		<small>Company name</small>
	</div>
	
	<div class="row-fluid">
		<div class="span1"></div>
		<div class="span10">
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
		<div class="span1"></div>
		<div class="span10">
			<div class="span4">
	        	<h2>H0</h2>
				<ul class="unstyled">
					<li>AC Steam locomotives</li>
					<li>AC Diesel locomotives</li>
					<li>AC Electric locomotives</li>
					<li>AC Electric locomotives</li>
				</ul>
			</div>
			
			<div class="span4">
	        	<h2>N</h2>
				<ul class="unstyled">
					<li>AC Steam locomotives</li>
					<li>AC Diesel locomotives</li>
					<li>AC Electric locomotives</li>
					<li>AC Electric locomotives</li>
				</ul>
			</div>
			
			<div class="span4">
	        	<h2>Z</h2>
				<ul class="unstyled">
					<li>AC Steam locomotives</li>
					<li>AC Diesel locomotives</li>
					<li>AC Electric locomotives</li>
					<li>AC Electric locomotives</li>
				</ul>
			</div>
		</div>
		<div class="span1"></div>
	</div>

	<p/>
	
</body>
</html>
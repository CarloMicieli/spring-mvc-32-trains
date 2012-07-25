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

	<div class="row-fluid">
		<div class="span2">
			<div class="page-header">
				<h1>${era.label}</h1>
				<small></small>
			</div>
		</div>
		<div class="span9">
			<div class="row-fluid">
				<div class="span3">
				</div>
				<div class="span9">
					${era.description}
				</div>
			</div>
			<hr/>
		</div>
		<div class="span1"></div>
	</div>
	
</body>
</html>
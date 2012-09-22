<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.categories.title" /></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="browse.breadcrumb.categories.label"/></li>
	</ul>

	<div class="page-header">
		<h1>
			<s:message code="browse.categories.header.title" />
			<small><s:message code="browse.categories.header.subtitle" /></small>
		</h1>
	</div>

	<c:forEach var="cat" items="${categories}">
	<div class="row-fluid">
		<div class="span3 offset2">
			<span style="font-family: verdana; font-size: 18px">${cat.label}</span>
		</div>
		<div class="span3">
			${cat.description}
		</div>
		<div class="span4">
			<s:url var="catUrl" value="/browse/categories/{slug}">
				<s:param name="slug" value="${cat.key}"/>
			</s:url>
			<a class="btn btn-info" href="${catUrl}">
				<s:message code="button.view.details.label" text="View details"/>
			</a>			
		</div>
	</div>
	<hr/>
	</c:forEach>
	
</body>
</html>
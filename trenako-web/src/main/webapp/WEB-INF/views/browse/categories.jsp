<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.categories.title" text="categories"/></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.title" text="Browse"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="browse.categories.title" text="categories"/></li>
	</ul>

	<div class="page-header">
		<h1><s:message code="browse.categories.header.title" text="categories" /></h1>
		<small><s:message code="browse.categories.header.subtitle" text="Select a model railway category" /></small>
	</div>

	<c:forEach var="cat" items="${categories}">
	<div class="row-fluid">
		<div class="span2"></div>
		<div class="span2">
			<span style="font-family: verdana; font-size: 18px">${cat}</span>
		</div>
		<div class="span4">
			<h3></h3>
			<dl class="dl-horizontal">
			</dl>
		</div>
		<div class="span4">
			<s:url var="catUrl" value="/browse/categories/{slug}">
				<s:param name="slug" value="${cat}"/>
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
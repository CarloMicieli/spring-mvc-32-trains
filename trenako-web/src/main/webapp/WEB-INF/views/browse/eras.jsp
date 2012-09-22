<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.eras.title" /></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="browse.breadcrumb.eras.label"/></li>
	</ul>

	<div class="page-header">
		<h1>
			<s:message code="browse.eras.header.title" />
			<small><s:message code="browse.eras.header.subtitle" /></small>
		</h1>
	</div>

	<c:forEach var="era" items="${eras}">
	<div class="row-fluid">
		<div class="span2 offset1">
			<span style="font-family: verdana; font-size: 36px">${era.label}</span>
		</div>
		<div class="span4">
			${era.description}
		</div>
		<div class="span5">
			<s:url var="eraUrl" value="/browse/eras/{slug}">
				<s:param name="slug" value="${era.key}"/>
			</s:url>
			<a class="btn btn-info" href="${eraUrl}">
				<s:message code="button.view.details.label" text="View details"/>
			</a>			
		</div>
	</div>
	<hr/>
	</c:forEach>
	
</body>
</html>
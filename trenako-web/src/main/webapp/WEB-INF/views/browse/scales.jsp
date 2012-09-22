<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="browse.scales.title"/></title>
	<meta name="browse" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/browse" var="browseUrl"/>
	    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="browse.breadcrumb.scales.label"/></li>
	</ul>

	<div class="page-header">
		<h1>
			<s:message code="browse.scales.header.title" />
			<small><s:message code="browse.scales.header.subtitle" /></small>
		</h1>
	</div>

	<c:forEach var="scale" items="${scales}">
	<div class="row-fluid">
		<div class="span2"></div>
		<div class="span2">
			<span style="font-family: verdana; font-size: 36px">${scale.name}</span>
		</div>
		<div class="span4">
			<h3></h3>
			<dl class="dl-horizontal">
				<dt><s:message code="scale.ratio.label" /></dt>
				<dd>1:<s:eval expression="scale.ratio" /></dd>
				<dt><s:message code="scale.gauge.label" /></dt>
				<dd><s:eval expression="scale.gauge" /> mm</dd>
			</dl>
		</div>
		<div class="span4">
			<s:url var="scaleUrl" value="/browse/scales/{slug}">
				<s:param name="slug" value="${scale.slug}"/>
			</s:url>
			<a class="btn btn-info" href="${scaleUrl}">
				<s:message code="button.view.details.label" text="View details"/>
			</a>			
		</div>
	</div>
	<hr/>
	</c:forEach>
	
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="browse.railways.title"/></title>
		<meta name="browse" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/browse" var="browseUrl"/>
		    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active"><s:message code="browse.breadcrumb.railways.label"/></li>
		</ul>
	
		<div class="page-header">
			<h1><s:message code="browse.railways.header.title" />
			<small><s:message code="browse.railways.header.subtitle" /></small></h1>
		</div>
	
		<c:forEach var="railway" items="${railways}">
		<div class="row-fluid">
			<div class="span2"></div>
			<div class="span2">
				<s:url value="/images/th_railway_{slug}" var="logoUrl">
				<s:param name="slug" value="${railway.slug}" />
				</s:url>
				<img src="${logoUrl}" alt="Not found"/>
			</div>
			<div class="span4">
				<h3>${railway.name}</h3>
				<dl class="dl-horizontal">
					<dt><s:message code="railway.companyName.label" /></dt>
					<dd>${railway.companyName}</dd>
					<dt><s:message code="railway.description.label" /></dt>
					<dd><tk:eval expression="${railway.description}" maxLength="250" /></dd>					
					<dt><s:message code="railway.country.label" /></dt>
					<dd>
						<s:url var="flagUrl" value="/resources/img/flags/32/{iso}.png">
							<s:param name="iso" value="${railway.country}"></s:param>
						</s:url>
						<img src="${flagUrl}" alt="${railway.country}"/>
					</dd>
				</dl>
			</div>
			<div class="span4">
				<s:url var="railwayUrl" value="/browse/railways/{slug}">
					<s:param name="slug" value="${railway.slug}"/>
				</s:url>
				
				<a class="btn btn-info" href="${railwayUrl}">
					<s:message code="button.view.details.label" />
				</a>
			</div>
		</div>
		<hr/>
		</c:forEach>
		
	</body>
</html>
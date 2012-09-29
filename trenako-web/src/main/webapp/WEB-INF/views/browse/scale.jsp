<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="browse.scale.title" arguments="${scale.name}"/></title>
		<meta name="browse" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/browse" var="browseUrl"/>
		    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/browse/scales" var="scalesUrl"/>
		    	<a href="${scalesUrl}"><s:message code="browse.breadcrumb.scales.label"/></a> <span class="divider">/</span>
			</li>		
		  	<li class="active">${scale.name}</li>
		</ul>

		<div class="page-header">
			<h1>${scale.name}
			<small>(1:<s:eval expression="scale.ratio"/>)</small></h1>
		</div>
	
		<div class="row-fluid">
			<div class="span4">
				<dl class="dl-horizontal">
					<dt><s:message code="scale.ratio.label" /></dt>
					<dd>(1:<s:eval expression="scale.ratio" />)</dd>
					<dt><s:message code="scale.gauge.label" /></dt>
					<dd><s:eval expression="scale.gauge" /> mm</dd>
					<dt></dt>
					<dd>
						(<c:choose>
							<c:when test="${scale.narrow}">
								<s:message code="scale.narrow.gauge.label" />
							</c:when>
							<c:otherwise>
								<s:message code="scale.standard.gauge.label" />
							</c:otherwise>
						</c:choose>)
					</dd>
				</dl>
			</div>
			<div class="span8">
				<tk:eval expression="${scale.description}"/>
			</div>
		</div>
	
		<hr/>
	
		<div class="row-fluid">
			<div class="span3 offset1">
				<strong><s:message code="browse.scale.eras.label"/></strong>
				<p><s:message code="browse.scale.eras.subtitle" arguments="${scale.name}"/></p>
			</div>
			<div class="span8">
				<ul class="unstyled">
				<c:forEach var="era" items="${eras}">
					<s:url var="eraUrl" value="/rs/scale/{scale}/era/{era}">
						<s:param name="scale" value="${scale.slug}"/>
						<s:param name="era" value="${era.key}"/>
					</s:url>
					<li><a class="browse" href="${eraUrl}" title="${era.description}">${era.label}</a></li> 
				</c:forEach>
				</ul>
			</div>
		</div>

		<hr />

		<div class="row-fluid">
			<div class="span3 offset1">
				<strong><s:message code="browse.scale.categories.label"/></strong>
				<p><s:message code="browse.scale.categories.subtitle" arguments="${scale.name}"/></p>
			</div>
			<div class="span8">
				<ul class="unstyled">
				<c:forEach var="cat" items="${categories}">
					<s:url var="categoryUrl" value="/rs/scale/{scale}/category/{category}">
						<s:param name="scale" value="${scale.slug}"/>
						<s:param name="category" value="${cat.key}"/>
					</s:url>
					<li><a class="browse" href="${categoryUrl}" title="${cat.description}">${cat.label}</a></li> 
				</c:forEach>
				</ul>
			</div>
		</div>
		
	</body>
</html>
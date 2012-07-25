<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="browse.railway.title" arguments="${railway.name}"/></title>
		<meta name="browse" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/browse" var="browseUrl"/>
		    	<a href="${browseUrl}"><s:message code="browse.breadcrumb.browse.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/browse/railways" var="railwaysUrl"/>
		    	<a href="${railwaysUrl}"><s:message code="browse.breadcrumb.railways.label"/></a> <span class="divider">/</span>
			</li>		
		  	<li class="active">${railway.name}</li>
		</ul>
	
		<div class="row-fluid">
			<div class="span2">
				<div class="page-header">
					<h1>
						${railway.name}
						<s:url var="flagUrl" value="/resources/img/flags/32/{iso}.png">
							<s:param name="iso" value="${railway.country}"></s:param>
						</s:url>
						<img src="${flagUrl}" alt="${railway.country}"/>
					</h1>
					<small>${railway.companyName}</small>
				</div>
			</div>
			<div class="span9">
				<div class="row-fluid">
					<div class="span3">
						<img src="http://placehold.it/250x120" alt="">
					</div>
					<div class="span9">
						${railway.description}
					</div>
				</div>
				<hr/>
			</div>
			<div class="span1"></div>
		</div>
	
		<div class="row-fluid">
			<div class="span2"></div>
			<div class="span9">
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.railway.eras.label"/></h3>
						<p><s:message code="browse.railway.eras.subtitle" arguments="${railway.name}"/></p>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="era" items="${eras}">
							<s:url var="eraUrl" value="/rs/railway/{railway}/era/{era}">
								<s:param name="railway" value="${railway.slug}"/>
								<s:param name="era" value="${era.key}"/>
							</s:url>
							<li><a class="browse" href="${eraUrl}" title="${era.description}">${era.label}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.railway.categories.label"/></h3>
						<p><s:message code="browse.railway.categories.subtitle" arguments="${railway.name}"/></p>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="cat" items="${categories}">
							<s:url var="categoryUrl" value="/rs/railway/{railway}/category/{category}">
								<s:param name="railway" value="${railway.slug}"/>
								<s:param name="category" value="${cat.key}"/>
							</s:url>
							<li><a class="browse" href="${categoryUrl}" title="${cat.description}">${cat.label}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="span1"></div>
		</div>
	
		<p/>
		
	</body>
</html>
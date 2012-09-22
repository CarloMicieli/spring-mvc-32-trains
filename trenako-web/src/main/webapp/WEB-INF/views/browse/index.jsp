<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
	<head>
		<title><s:message code="browse.index.title"/></title>
		<meta name="browse" content="active"/>
		<style type="text/css">
			a.browse {
				font-size: 16px;	
			}
		</style>
	</head>
	
	<body>
		<ul class="breadcrumb">
			<li class="active">
		    	<s:message code="browse.breadcrumb.browse.label"/>
			</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>
					<s:message code="browse.index.header.title"/>
					<small>(<s:message code="browse.index.header.subtitle"/>)</small>
				</h1>
			</div>

			<div class="span11 offset1">
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.index.brands.label"/></h3>
						<s:message code="browse.index.brands.description"/>
						<a href="<s:url value="/browse/brands"/>">
							<s:message code="button.view.all.label"/>
						</a>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="brand" items="${brands}">
							<s:url var="brandUrl" value="/browse/brands/{slug}">
								<s:param name="slug" value="${brand.slug}"/>
							</s:url>
							<li><a class="browse" href="${brandUrl}" title="${brand.label}">${brand.name}</a></li>
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.index.railways.label"/></h3>
						<s:message code="browse.index.railways.description"/>
						<a href="<s:url value="/browse/railways"/>">
							<s:message code="button.view.all.label"/>
						</a>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="r" items="${railways}">
							<s:url var="railwayUrl" value="/browse/railways/{slug}">
								<s:param name="slug" value="${r.slug}"/>
							</s:url>
							<li><a class="browse" href="${railwayUrl}" title="${r.label}">${r.name}</a></li>
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.index.scales.label"/></h3>
						<s:message code="browse.index.scales.description"/>
						<a href="<s:url value="/browse/scales"/>">
							<s:message code="button.view.all.label"/>
						</a>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="s" items="${scales}">
							<s:url var="scaleUrl" value="/browse/scales/{slug}">
								<s:param name="slug" value="${s.slug}"/>
							</s:url>
							<li><a class="browse" href="${scaleUrl}" title="${s.label}">${s.name}</a></li>
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.index.eras.label"/></h3>
						<s:message code="browse.index.eras.description"/>
						<a href="<s:url value="/browse/eras"/>">
							<s:message code="button.view.all.label"/>
						</a>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="era" items="${eras}">
							<s:url var="eraUrl" value="/browse/eras/{slug}">
								<s:param name="slug" value="${era.key}"/>
							</s:url>
							<li><a class="browse" href="${eraUrl}" title="${era.description}">${era.label}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
				<hr />
				<div class="row-fluid">
					<div class="span4">
						<h3><s:message code="browse.index.categories.label"/></h3>
						<s:message code="browse.index.categories.description"/>
						<a href="<s:url value="/browse/categories"/>">
							<s:message code="button.view.all.label"/>
						</a>
					</div>
					<div class="span8">
						<ul class="unstyled">
						<c:forEach var="cat" items="${categories}">
							<s:url var="catUrl" value="/browse/categories/{slug}">
								<s:param name="slug" value="${cat.key}"/>
							</s:url>
							<li><a class="browse" href="${catUrl}" title="${cat.description}">${cat.label}</a></li> 
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="span1"></div>
		</div>
	</body>
</html>
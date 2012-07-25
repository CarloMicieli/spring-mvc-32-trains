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
	
		<div class="row-fluid">
			<div class="span2">
				<div class="page-header">
					<h1>${scale.name}</h1>
					<small>(1:<s:eval expression="scale.ratio"/>)</small>
				</div>
			</div>
			<div class="span9">
				<div class="row-fluid">
					<div class="span4">
						<dl class="dl-horizontal">
							<dt><s:message code="scale.ratio.label" /></dt>
							<dd>(1:<s:eval expression="scale.ratio" />)</dd>
							<dt><s:message code="scale.gauge.label" /></dt>
							<dd><s:eval expression="scale.gauge" /> mm</dd>
							<dt></dt>
							<dd>
								<c:choose>
									<c:when test="${scale.narrow}">
										<s:message code="scale.narrow.gauge.label" />
									</c:when>
									<c:otherwise>
										<s:message code="scale.standard.gauge.label" />
									</c:otherwise>
								</c:choose>
							</dd>
						</dl>
					</div>
					<div class="span8">
						${scale.description}
					</div>
				</div>
				<hr/>
			</div>
			<div class="span1"></div>
		</div>
	
		<p/>
		
	</body>
</html>
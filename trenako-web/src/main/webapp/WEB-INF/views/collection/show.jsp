<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="collection.name.label" arguments="${owner.displayName}"/>
		</title>
		<meta name="you" content="active"/>
	</head>
	<body>
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="nav.home.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/you" var="youUrl"/>
		    	<a href="${youUrl}"><s:message code="nav.you.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active">
		  		<s:message code="collection.name.label" arguments="${owner.displayName}"/>
		  	</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>
					<s:message code="collection.name.label" arguments="${owner.displayName}"/> 
					<small>(<tk:evalValue type="Visibility" expression="${collection.visibility}"/>)</small>
				</h1>
			</div>
		</div>
		<div class="row-fluid" style="min-height: 450px">
			<div class="span3" style="padding: 0 20px 0 15px">
				<h4><s:message code="collection.content.label"/></h4>
				<ul class="unstyled">
					<li>
						<s:message code="you.count.steam.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.steamLocomotives}</span>
					</li>
					<li>
						<s:message code="you.count.diesel.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.dieselLocomotives}</span>
					</li> 
					<li>
						<s:message code="you.count.electric.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.electricLocomotives}</span>
					</li>
					<li>
						<s:message code="you.count.railcars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.railcars}</span>
					</li>
					<li>
						<s:message code="you.count.electric.multiple.unit.label"/>
						<span class="badge badge-info pull-right">${collection.categories.electricMultipleUnit}</span>
					</li>
					<li>
						<s:message code="you.count.passenger.cars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.passengerCars}</span>
					</li>
					<li>
						<s:message code="you.count.freight.cars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.freightCars}</span>
					</li>
					<li>
						<s:message code="you.count.train.sets.label"/>
						<span class="badge badge-info pull-right">${collection.categories.trainSets}</span>
					</li>
					<li>
						<s:message code="you.count.starter.sets.label"/>
						<span class="badge badge-info pull-right">${collection.categories.starterSets}</span>
					</li>					
				</ul>
			</div>
			<div class="span9">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				<div class="row-fluid">
					<div class="span7">
						<dl class="dl-horizontal">
							<dt><s:message code="collection.visibility.label"/>:</dt>
							<dd><tk:evalValue type="Visibility" expression="${collection.visibility}"/></dd>
							<dt><s:message code="collection.numberOfItems.label"/>:</dt>
							<dd>${collection.categories.totalCount}</dd>
							<dt><s:message code="collection.notes.label"/>:</dt>
							<dd>${collection.notes}</dd>
						</dl>
					</div>
					<div class="span5">
						<p>
							<s:message code="collection.lastModified.label"/> <strong><tk:period since="${collection.lastModified}"/></strong>
						</p>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span12">
						<hr>
						
						<c:if test="${empty collection.items}">
							<h3><s:message code="collection.empty.label"/></h3>
						</c:if>
						
						<c:forEach var="item" items="${collection.items}">
							<s:url var="rsUrl" value="/rollingstocks/{slug}">
								<s:param name="slug" value="${item.rollingStock.slug}" />
							</s:url>
							<div class="row-fluid">
								<div class="span3">
									<s:url var="imgUrl" value="/images/th_rollingstock_{slug}">
										<s:param name="slug" value="${item.rollingStock.slug}" />
									</s:url>
									<a href="${rsUrl}" class="thumbnail"><img src="${imgUrl}" alt="Not found"></a>
								</div>
								<div class="span9">
									<a href="${rsUrl}"><strong>${item.rollingStock.label}</strong></a>
									
									<dl>
										<dt><s:message code="collection.addedAt.label"/>:</dt>
										<dd>${item.addedDay}</dd>
										<dt><s:message code="collection.price.label"/>:</dt>
										<dd>${item.price}</dd>
										<dt><s:message code="collection.condition.label"/>:</dt>
										<dd><tk:evalValue type="Condition" expression="${item.condition}"/></dd>
										<dt><s:message code="collection.notes.label"/>:</dt>
										<dd>${item.notes}</dd>
										
									</dl>	
								</div>
							</div>
							<hr/>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
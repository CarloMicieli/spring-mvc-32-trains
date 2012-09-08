<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title><s:message code="you.page.title"/></title>
		<meta name="you" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="nav.home.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active"><s:message code="nav.you.label"/></li>
		</ul>
		
		<div class="row-fluid">
			<div class="span9">
				<tk:avatar user="${user.account.slug}" size="64" showGravatarLink="true"/>
				<div class="page-header">
					<h1>${user.username} <small>(${user.displayName})</small></h1>
				</div>
			</div>
			<div class="span3">
				<div class="pull-right">
					<a href="#" class="btn btn-info"><s:message code="button.edit.profile.label"/></a>
				</div>
			</div>
			<hr />
		</div>
		
		<div class="row-fluid">
			<div class="span11 offset1">
				<div class="row-fluid">
					<div class="span11 offset1">
						<h3><s:message code="you.count.collection.title"/>:</h3>
						<hr>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span1 offset1">
						<span style="font-size:24px;">${info.categoriesCount.steamLocomotives}</span> 
						<p><s:message code="you.count.steam.locomotives.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.dieselLocomotives}</span> 
						<p><s:message code="you.count.diesel.locomotives.label"/></p>
					</div>					
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.electricLocomotives}</span> 
						<p><s:message code="you.count.electric.locomotives.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.railcars}</span> 
						<p><s:message code="you.count.railcars.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.electricMultipleUnit}</span> 
						<p><s:message code="you.count.electric.multiple.unit.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.passengerCars}</span> 
						<p><s:message code="you.count.passenger.cars.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.freightCars}</span> 
						<p><s:message code="you.count.freight.cars.label"/></p>
					</div>					
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.trainSets}</span> 
						<p><s:message code="you.count.train.sets.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px;">${info.categoriesCount.starterSets}</span> 
						<p><s:message code="you.count.starter.sets.label"/></p>
					</div>
					<div class="span3"></div>
				</div>
				<div class="row-fluid">
					<div class="span2">
					</div>
					<div class="span10">
						<hr>
					</div>
				</div>			
							
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span11">
				<div class="row-fluid">
					<div class="span4">
						<h2><s:message code="you.recent.activity.title"/></h2>
						<p>
							<c:forEach var="act" items="${info.userActivity}">
								<div class="row-fluid" style="border-left: thick solid ${act.color};">
									<div class="span3 offset1">
										<tk:avatar user="${act.actor}" size="48" showName="true"/>
									</div>
									<div class="span8">
										<tk:activity activity="${act}"/> 
									</div>
								</div>
								<hr/>
							</c:forEach>
						</p>			
					</div>				
					<div class="span4">
						<h2><s:message code="you.from.collection.title"/></h2>
						<p>
							<a href="#" class="btn btn-danger">
								<i class="icon-tags icon-white"></i> <s:message code="you.manage.collection.button"/>
							</a>
						</p>
						<ul class="unstyled">
						<c:forEach var="item" items="${info.collectionItems}">
							<li>${item.itemSlug} ${item.itemName} ${item.addedAt}</li>
						</c:forEach>
						</ul>

					</div>
					<div class="span4">
						<h2><s:message code="you.from.wish.lists.title"/></h2>
						<p>
							<s:url var="wishListsUrl" value="/wishlists/owner/{slug}">
								<s:param name="slug" value="${user.account.slug}"/>
							</s:url>
							<a href="${wishListsUrl}" class="btn btn-primary">
								<i class="icon-gift icon-white"></i> <s:message code="you.manage.wish.lists.button"/>
							</a>
						</p>

						<ul class="unstyled">
						<c:forEach var="item" items="${info.wishListItems}">
							<li>${item.itemSlug} ${item.itemName} ${item.addedAt}</li>
						</c:forEach>
						</ul>
					</div>		
				</div>
			</div>
		</div>
		
		<p />

	</body>
</html>
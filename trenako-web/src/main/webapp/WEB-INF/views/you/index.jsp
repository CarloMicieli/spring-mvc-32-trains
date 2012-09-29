<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

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
			<tk:avatar user="${user.slug}" size="64" showGravatarLink="true"/>
			<div class="page-header">
				<h1>${user.emailAddress} <small>(${user.displayName})</small></h1>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span11 offset1">
				<div class="row-fluid">
					<div class="span11 offset1">
						<h3><s:message code="you.count.collection.title"/>:</h3>
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
			</div>
		</div>
		
		<hr/>
		
		<div class="row-fluid">
			<div class="span4">
				<h2><s:message code="you.recent.activity.title"/></h2>
				<p>
					<c:forEach var="act" items="${info.userActivity}">
					<div class="row-fluid" style="border-left: thick solid ${act.color};">
						<div class="span4">
							<html:thumb slug="${act.object.slug}"/>
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
				<s:url var="collUrl" value="/you/collection"/>
				<h2><s:message code="you.from.collection.title"/></h2>
				<p>
					<a href="${collUrl}" class="btn btn-primary">
						<i class="icon-tags icon-white"></i> <s:message code="you.manage.collection.button"/>
					</a>
				</p>
				
				<hr/>
				<h4><s:message code="you.collection.title.label"/></h4>						
				
				<c:forEach var="item" items="${info.collectionItems}">
				<div class="row-fluid" style="margin-bottom: 15px;">
					<div class="span5 offset1">
						<s:url value="/rollingstocks/{slug}" var="showUrl">
							<s:param name="slug" value="${item.itemSlug}" />
						</s:url>
						<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
							<s:param name="slug" value="${item.itemSlug}" />
						</s:url>
						<a href="${showUrl}" class="thumbnail">
							<img src="${imgUrl}" alt="Not found" width="200px">
					    </a>
					</div>
					<div class="span6">
						<h5>${item.itemName}</h5>
						
						<s:message code="you.added.to.label"/> <a href="${collUrl}">${item.listName}</a>.
						<br/>
						<strong><tk:period since="${item.addedAt}"/></strong>
					</div>
				</div>
				</c:forEach>
			</div>
			<div class="span4">
				<h2><s:message code="you.from.wish.lists.title"/></h2>
				<p>
					<s:url var="wishListsUrl" value="/you/wishlists"/>
					<a href="${wishListsUrl}" class="btn btn-primary">
						<i class="icon-gift icon-white"></i> <s:message code="you.manage.wish.lists.button"/>
					</a>
				</p>
	
				<hr/>
				<h4><s:message code="you.wishlist.title.label"/></h4>						
				
				<c:forEach var="item" items="${info.wishListItems}">
				<div class="row-fluid" style="margin-bottom: 15px;">
					<div class="span5 offset1">
						<s:url value="/rollingstocks/{slug}" var="showUrl">
							<s:param name="slug" value="${item.itemSlug}" />
						</s:url>
						<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
							<s:param name="slug" value="${item.itemSlug}" />
						</s:url>
						<a href="${showUrl}" class="thumbnail">
							<img src="${imgUrl}" alt="Not found" width="200px">
					    </a>
					</div>
					<div class="span6">
						<h5>${item.itemName}</h5>
						<s:url var="listUrl" value="/you/wishlists/{slug}">
							<s:param name="slug" value="${item.listSlug}"></s:param>
						</s:url>
						
						<s:message code="you.added.to.label"/> <a href="${listUrl}">${item.listName}</a>.
						<br/>
						<strong><tk:period since="${item.addedAt}"/></strong>
					</div>
				</div>
				</c:forEach>
			</div>		
		</div>
		
		<p />

	</body>
</html>
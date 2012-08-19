<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

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
			<div class="span1">
				<a href="http://gravatar.com/emails/">
					<img height="64" width="64" src="https://secure.gravatar.com/avatar/540b91c4528151f2bc8c3c0f5e16e889?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png">
				</a>
			</div>
			<div class="span5">
				<div class="page-header">
					<h1>${user.username}</h1>
					<small style="color:silver">(${user.displayName})</small>
				</div>
			</div>
			<div class="span6">
				<div class="pull-right">
					<a href="#" class="btn btn-info"><s:message code="button.edit.profile.label"/></a>
				</div>
			</div>
			<hr />
		</div>
		
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span11">
				<hr />
				
				<div class="row-fluid">
					<div class="span2"><h3><s:message code="you.count.collection.title"/>:</h3></div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.steamLocomotives}</span> 
						<p><s:message code="you.count.steam.locomotives.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.dieselLocomotives}</span> 
						<p><s:message code="you.count.diesel.locomotives.label"/></p>
					</div>					
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.electricLocomotives}</span> 
						<p><s:message code="you.count.electric.locomotives.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.railcars}</span> 
						<p><s:message code="you.count.railcars.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.electricMultipleUnit}</span> 
						<p><s:message code="you.count.electric.multiple.unit.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.passengerCars}</span> 
						<p><s:message code="you.count.passenger.cars.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.freightCars}</span> 
						<p><s:message code="you.count.freight.cars.label"/></p>
					</div>					
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.trainSets}</span> 
						<p><s:message code="you.count.train.sets.label"/></p>
					</div>
					<div class="span1">
						<span style="font-size:24px; color:yellow">${info.categoriesCount.starterSets}</span> 
						<p><s:message code="you.count.starter.sets.label"/></p>
					</div>
					<div class="span3"></div>
				</div>
			
				<hr />				
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span11">
				<div class="row-fluid">
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
							<a href="#" class="btn btn-primary">
								<i class="icon-gift icon-white"></i> <s:message code="you.manage.wish.lists.button"/>
							</a>
						</p>

						<ul class="unstyled">
						<c:forEach var="item" items="${info.wishListItems}">
							<li>${item.itemSlug} ${item.itemName} ${item.addedAt}</li>
						</c:forEach>
						</ul>
					</div>
					<div class="span4">
						<h2>Your recent activity</h2>
						<p>
							Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, 
							egestas eget quam. Vestibulum id ligula porta felis euismod semper. 
							Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum 
							nibh, ut fermentum massa justo sit amet risus.
						</p>
						<p>
							<a href="#" class="btn btn-danger">View all</a>
						</p>				
					</div>			
				</div>
			</div>
		</div>
		
		<p />

	</body>
</html>
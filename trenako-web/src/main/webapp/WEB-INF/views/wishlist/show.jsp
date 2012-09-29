<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="wishlist.page.show.title" arguments="${wishList.name}"/>
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
				<s:url value="/wishlists" var="wishlistsUrl"/>
		    	<a href="${wishlistsUrl}"><s:message code="nav.wishlists.label" text="Wish lists"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active">${owner.displayName}: ${wishList.name}</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>${wishList.name} <small>(<tk:evalValue type="Visibility" expression="${wishList.visibility}"/>)</small></h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span10 offset2">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				<div class="row-fluid">
					<div class="span8">
						<dl class="dl-horizontal">
							<dt><s:message code="wishlist.name.label"/>:</dt>
							<dd>${wishList.name}</dd>
							<dt><s:message code="wishlist.numberOfItems.label"/>:</dt>
							<dd>${wishList.numberOfItems}</dd>
							<dt><s:message code="wishlist.visibility.label"/>:</dt>
							<dd><tk:evalValue type="Visibility" expression="${wishList.visibility}"/></dd>
							<dt><s:message code="wishlist.notes.label"/>:</dt>
							<dd>${wishList.notes}</dd>
							<dt><s:message code="wishlist.budget.label"/>:</dt>
							<dd>${wishList.budget}</dd>
						</dl>
					</div>
					<div class="span4">
						<p>
							<s:message code="wishlist.lastModified.label"/> <strong><tk:period since="${wishList.lastModified}"/></strong>
						</p>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<hr>
						
						<c:if test="${wishList.items.size() == 0}">
							<div class="well">
								<h4><s:message code="wishlist.empty.list.message"/></h4> 
							</div>
						</c:if>
						
						<c:forEach var="item" items="${wishList.items}">
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
									<p>
										<small><em><s:message code="wishlist.item.added.label"/></em> <strong><tk:period since="${item.addedAt}"/></strong></small>
									</p>
									
									<dl>
										<dt><s:message code="wishlist.item.priority.label"/>:</dt>
										<dd><tk:evalValue type="Priority" expression="${item.priority}"/></dd>
										<dt><s:message code="wishlist.item.price.label"/> [<a class="priceHelp" href="#" rel="tooltip" data-placement="right">?</a>]:</dt>
										<dd>${item.price}</dd>
										<dt><s:message code="wishlist.item.notes.label"/>:</dt>
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
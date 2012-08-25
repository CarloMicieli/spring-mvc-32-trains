<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="wishlist.page.new.title"/>
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
			<li>
				<s:url value="/wishlists/owner/{owner}" var="wishlistsUrl">
					<s:param name="owner" value="${wishList.owner}"></s:param>
				</s:url>
		    	<a href="${wishlistsUrl}"><s:message code="nav.wish.lists.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active">${wishList.name}</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>${wishList.name}
				</h1>
			</div>
		</div>
		<div class="row-fluid">
			
			<div class="span2">
				<p>
					<s:url var="backUrl" value="/wishlists/owner/{slug}">
						<s:param name="slug" value="${wishList.owner}" />
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
				</p>
			
				<s:message code="wishlist.lastModified.label"/>
				<br/>
				<tk:period since="${wishList.lastModified}"/>
			</div>
			<div class="span6">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
			
				<dl class="dl-horizontal">
					<dt><s:message code="wishlist.name.label"/>:</dt>
					<dd>${wishList.name}</dd>
					<dt><s:message code="wishlist.numberOfItems.label"/>:</dt>
					<dd>${wishList.numberOfItems}</dd>
					<dt><s:message code="wishlist.visibility.label"/>:</dt>
					<dd>${wishList.visibility}</dd>
					<dt><s:message code="wishlist.notes.label"/>:</dt>
					<dd>${wishList.notes}</dd>
				</dl>
			</div>
			<div class="span4">
				<s:url var="deleteUrl" value="/wishlists" />
				<form:form cssClass="form-inline" method="DELETE" action="${deleteUrl}">
					<input type="hidden" id="id" name="id" value="${wishList.id}"/>
					<s:url var="editUrl" value="/wishlists/{slug}/edit">
						<s:param name="slug" value="${wishList.slug}"/>
					</s:url>
					<a href="${editUrl}" class="btn btn-warning">Edit</a>
					<button type="submit" class="btn btn-danger">Delete</button>
				</form:form>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span2"></div>
			<div class="span8">
				<hr>
				
				<c:if test="${wishList.items.size() == 0}">
					<div class="well">
					<h4><s:message code="wishlist.empty.list.message"/></h4> 
					
					<s:url var="addItemUrl" value="/wishlists/items"/>
					<form:form cssClass="form-inline" method="POST" action="${addItemUrl}">
						<select class="input-large">
							<option>--brand--</option>
							<option>ACME</option>
							<option>Roco</option>
						</select>
						
						<input type="text" class="input-medium" maxlength="10" placeholder="Item number" required="required">
						
						<button type="submit" class="btn btn-primary">Add</button>
					</form:form>
					</div>
				</c:if>
				
				<c:forEach var="item" items="${wishList.items}">
					<div class="row-fluid">
						<div class="span4">
							<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
								<s:param name="slug" value="${item.rollingStock.slug}" />
							</s:url>
							<img src="${imgUrl}" alt="Not found">
						</div>
						<div class="span8">
							<h5>${item.rollingStock.label}</h5> (<a href="${item.rollingStock.slug}">View details...</a>)
							<dl class="dl-horizontal">
								<dt>priority</dt>
								<dd>${item.priority}</dd>
								<dt>updated</dt>
								<dd><tk:period since="${item.addedAt}"/></dd>
							</dl>	
							
							
							<s:url var="deleteItemUrl" value="/wishlists/items"/>
							<form:form method="DELETE" action="${deleteItemUrl}">
								<input type="hidden" id="slug" name="slug" value="${wishList.slug}"/>
								<input type="hidden" id="rsSlug" name="rsSlug" value="${item.rollingStock.slug}"/>
								<input type="hidden" id="itemId" name="itemId" value="${item.itemId}"/>
								<button type="submit" class="btn btn-danger">Delete</button>
							</form:form>
						</div>
					</div>
					<hr/>
				</c:forEach>


			</div>
			<div class="span2"></div>
		</div>
	</body>
</html>
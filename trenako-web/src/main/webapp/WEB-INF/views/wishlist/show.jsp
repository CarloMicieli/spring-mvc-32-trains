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
				<s:message code="wishlist.lastModified.label"/>
				<br/>
				<tk:period since="${wishList.lastModified}"/>
			</div>
			<div class="span6">
				<dl class="dl-horizontal">
					<dt><s:message code="wishlist.name.label"/>:</dt>
					<dd>${wishList.name}</dd>
					<dt><s:message code="wishlist.notes.label"/>:</dt>
					<dd>${wishList.notes}</dd>
					<dt><s:message code="wishlist.numberOfItems.label"/>:</dt>
					<dd>${wishList.numberOfItems}</dd>
					<dt><s:message code="wishlist.visibility.label"/>:</dt>
					<dd>${wishList.visibility}</dd>
				</dl>
			</div>
			<div class="span4">
				<a href="#" class="btn btn-warning">Edit</a>
				<a href="#" class="btn btn-danger">Delete</a>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span2"></div>
			<div class="span10">
				<hr>
				
				<c:if test="${wishList.items.size() == 0}">
					<p>
						It seems your list is currently empty. 
					</p>
					
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
				</c:if>
				
				<hr>
			</div>
		</div>
	</body>
</html>
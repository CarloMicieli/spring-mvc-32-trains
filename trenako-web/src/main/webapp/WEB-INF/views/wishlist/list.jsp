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
		  	<li class="active"><s:message code="nav.wish.lists.label"/></li>
		</ul>
		
		<div class="page-header">
        	<h1>Wish lists <small>${slug}</small></h1>
        </div>
		<div class="row-fluid">
			<div class="span2">
				<s:url var="createUrl" value="/wishlists/new"/>
				<a href="${createUrl}" class="btn btn-info">Create new</a>
			</div>
			<div class="span10">
				<ul class="unstyled">
				<c:forEach var="wishList" items="${results}">
					<li>
						[<tk:evalValue type="Visibility" expression="${wishList.visibility}"/>] <strong>${wishList.name}</strong>
						<span class="badge badge-info">${wishList.numberOfItems} items</span>
						<br/> 
						<em><small>updated <tk:period since="${wishList.lastModified}"/></small></em>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>
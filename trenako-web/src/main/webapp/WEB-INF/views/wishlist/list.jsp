<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="wishlist.page.list.label" arguments="${owner.displayName}"/>
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
		  	<li class="active"><s:message code="nav.you.wish.lists.label" arguments="${owner.displayName}"/></li>
		</ul>
		
		<div class="page-header">
        	<h1><s:message code="wishlist.you.title.label"/></h1>
        </div>
		<div class="row-fluid">
			<div class="span2">
				<tk:avatar user="${owner}" size="64"/>
			</div>
			<div class="span10">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
			
				<p>
					<s:url var="createUrl" value="/wishlists/new"/>
					<a href="${createUrl}" class="btn btn-info">
						<i class="icon-file icon-white"></i> <s:message code="wishlist.new.button.label"/>
					</a>
				</p>
				
				<hr/>

				<ul class="unstyled">
				<c:forEach var="wishList" items="${results}">
					<li>
						<p>
							<span class="badge badge-info"><s:message code="wishlist.item.count.label" arguments="${wishList.numberOfItems}"/></span>
							[<tk:evalValue type="Visibility" expression="${wishList.visibility}"/>] 
							
							<s:url var="viewUrl" value="/you/wishlists/{slug}">
								<s:param name="slug" value="${wishList.slug}"></s:param>
							</s:url>
							<strong><a href="${viewUrl}">${wishList.name}</a></strong>
							<br/> 
							<s:message code="wishlist.lastModified.label"/> <strong><tk:period since="${wishList.lastModified}"/></strong>
						</p>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>
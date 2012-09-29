<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="home.explore.title"/></title>
	<meta name="home" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/home" var="homeUrl"/>
	    	<a href="${homeUrl}"><s:message code="home.title" text="Home"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="home.explore.title"/></li>
	</ul>

	<div class="page-header">
		<h1>
			<s:message code="home.explore.header.title"/>
			<small><s:message code="home.explore.header.subtitle" text="Trenako's features" /></small>
		</h1>
	</div>

	<div class="row-fluid">
		<div class="span5 offset1">
			<c:url var="homeUrl" value="/resources/img/shots/home.png"/>
			<a href="#" class="fancybox"><img src="${homeUrl}" alt="Homepage"></a>
		</div>
		<div class="span6">
			<h4>Model railways 2.0</h4>
			<p>
				A web site for model railways rolling stocks collectors. 
			</p>
		</div>
	</div>
	<hr/>
	<div class="row-fluid">
		<div class="span5 offset1">
			<c:url var="rsUrl" value="/resources/img/shots/rs.png"/>
			<a href="#" class="fancybox"><img src="${rsUrl}" alt="Rolling stocks"></a>
		</div>
		<div class="span6">
			<h4>Rolling stocks</h4>
			<p>
				How many rolling stocks do you want? All you ever wanted (and more)! 
			</p>
		</div>
	</div>
	<hr/>
	<div class="row-fluid">
		<div class="span5 offset1">
			<c:url var="youUrl" value="/resources/img/shots/you.png"/>
			<a href="#" class="fancybox"><img src="${youUrl}" alt="Your page"></a>
		</div>
		<div class="span6">
			<h4>Everything under control</h4>
			<p>
				Everything is always under control in your profile page.
			</p>
		</div>
	</div>
	<hr/>
	<div class="row-fluid">
		<div class="span5 offset1">
			<c:url var="youUrl" value="/resources/img/shots/collection.png"/>
			<a href="#" class="fancybox"><img src="${youUrl}" alt="Your page"></a>
		</div>
		<div class="span6">
			<h4>Your collection</h4>
			<p>
				Your collection? It will be always in order.
			</p>
		</div>
	</div>
	<hr/>
	<div class="row-fluid">
		<div class="span5 offset1">
			<c:url var="rsUrl" value="/resources/img/shots/wishlist.png"/>
			<a href="#" class="fancybox"><img src="${rsUrl}" alt="Rolling stocks"></a>
		</div>
		<div class="span6">		
			<h4>Wish lists</h4>
			<p>
				What is the best rolling stock in your collection? The one you didn't purchase yet!
			</p>
		</div>
	</div>
	
	<hr/>
	
	<div class="row-fluid">
		<div class="span12 well">
			<h3>
				<s:url var="signupUrl" value="/auth/signup"/>
				Ready to start? Sign up <a href="${signupUrl}">here</a> (it is easy and free!)	
			</h3>
		</div>
	</div>
</body>
</html>
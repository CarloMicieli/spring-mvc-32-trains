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
			<small><s:message code="home.explore.header.subtitle" text="Discover trenako.com" /></small>
		</h1>
	</div>

	<div class="span12">
		<div class="row-fluid">
			<div class="span10 offset1">
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
				<br>
			</div>
		</div>

		<div class="row-fluid">
			<h3>
				Features
			</h3>
			<hr>
			<div class="span3 offset1">
				<a href="#" class="fancybox"><img src="http://placehold.it/300x200" alt="img1"></a>
				<h4>Rolling stocks</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
			<div class="span3">
				<a href="#" class="fancybox"><img src="http://placehold.it/300x200" alt="im2"></a>
				<h4>Share your opinion</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
			<div class="span3">
				<a href="#" class="fancybox"><img src="http://placehold.it/300x200" alt="img3"></a>
				<h4>Collections 2.0</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
		</div>
	
		<div class="row-fluid"style="margin-top: 15px;">
			<h3>
				Start now
				<small>how it works</small>
			</h3>
			<hr>
		
			<div class="span3 offset1">
				<h4><span><img src="http://placehold.it/35x35" alt="iii"></span> Browse</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
			<div class="span3">
				<h4><span><img src="http://placehold.it/35x35" alt="iii"></span> Browse</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
			<div class="span3">
				<h4><span><img src="http://placehold.it/35x35" alt="iii"></span> Browse</h4>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim lacus vel turpis accumsan luctus. Sed eleifend justo eu velit iaculis bibendum. Maecenas scelerisque euismod nisi, eget dictum ligula imperdiet id. Fusce tristique dapibus sodales. Cras vestibulum lorem eget leo euismod adipiscing. Nullam quam risus, egestas at sollicitudin eget, luctus.
				</p>
			</div>
		</div>
		
		<br>
		<div class="well">
			<h3>
				<s:url var="signupUrl" value="/auth/signup"/>
				Ready to start? Sign up <a href="${signupUrl}">here</a> (it is easy and free!)	
			</h3>
		</div>
	</div>
</body>
</html>
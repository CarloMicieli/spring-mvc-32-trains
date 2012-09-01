<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="home.developers.title" text="Developers"/></title>
	<meta name="home" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/home" var="homeUrl"/>
	    	<a href="${homeUrl}"><s:message code="home.title" text="Home"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="home.developers.title" text="Developers"/></li>
	</ul>

	<div class="page-header">
		<h1>
			<s:message code="home.developers.header.title" text="Developers" />
			<small><s:message code="home.developers.header.subtitle" text="How trenako.com is build" /></small>
		</h1>
	</div>
	
	<div class="row-fluid offset1">
		<ul>
			<li>Java/Groovy web application;</li>
			<li>Application built with Gradle;</li>
			<li>Spring MVC;</li>
			<li>Spring Framework;</li>
			<li>Spring data for MongoDB;</li>
			<li>Spring security;</li>
			<li>Source code stored in a GitHub repository;</li>
			<li>Testing with JUnit, Mockito and Spock framework.</li>
		</ul>
		
		<a href="https://github.com/CarloMicieli/trenako">Source code</a> released under the <strong>Apache 2.0 license</strong>.
	</div>
	
	<hr/>
	
	<div class="row-fluid offset1">
		<div class="row-fluid">
		<h4>
			Spring Framework 3.1
			<small>The Java enterprise framework</small>
		</h4>
		</div>
		<div class="row-fluid">
			<div class="span1">
			</div>
			<div class="span11">
				<iframe width="444" height="250" src="http://www.youtube.com/embed/CkjRvBMwzo4?feature=player_embedded" frameborder="0" allowfullscreen></iframe>
			</div>
		</div>			
	</div>
	
	<hr/>
	
	<div class="row-fluid offset1">
		<div class="row-fluid">
		<h4>
			MongoDB 2.2
			<small>The noSQL database</small>
		</h4>
		</div>
		<div class="row-fluid">
			<div class="span1">
			</div>
			<div class="span11">
				<iframe width="444" height="250" src="http://www.youtube.com/embed/CvIr-2lMLsk?feature=player_embedded" frameborder="0" allowfullscreen></iframe>
			</div>
		</div>			
	</div>	
</body>
</html>

	
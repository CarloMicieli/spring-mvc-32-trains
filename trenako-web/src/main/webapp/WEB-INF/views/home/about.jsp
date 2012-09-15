<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
	<head>
		<title><s:message code="home.about.title"/></title>
		<meta name="home" content="active"/>
	</head>

	<body>
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="home.title" text="Home"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active"><s:message code="home.about.title"/></li>
		</ul>
	
		<div class="page-header">
			<h1>
				Trenako.com
				<small><s:message code="home.about.header.subtitle"/></small>
			</h1>
		</div>

		<div class="row-fluid" style="min-height: 450px">
			<div class="span6 offset1">
				<div class="tabbable">
				  	<ul class="nav nav-pills">
				    	<li class="active">
				    		<a href="#tab1" data-toggle="tab"><s:message code="home.about.about.tab.label"/></a>
				    	</li>
				    	<li><a href="#tab2" data-toggle="tab"><s:message code="home.about.contact.tab.label"/></a></li>
				  	</ul>
				  	<div class="tab-content">
				    	<div class="tab-pane active" id="tab1">
				    		<h2><s:message code="home.about.about.tab.label"/></h2>
				      		<p>
				      			Based in <strong>Milan - Italy - Planet Earth</strong>, Trenako.com encourages model railways
				      			enthusiasts worldwide to share their passion and rolling stock collections with peers,
				      			friend and total strangers. 
				      		</p>
				      		<p>
				      			Trenako.com is a gathering place where enthusiasts can connect with each other in a fun 
				      			and engaging way with the following options:
				      		</p>
				      		<ul>
				      			<li>discover new rolling stocks, browsing a growing database;</li>
				      			<li>build and manage virtual depots in form of rolling stocks collections;</li>
				      			<li>planning the future purchasing with wish lists;</li>
				      			<li>influence other members with rolling stock comments and reviews.</li>
				      		</ul>
				      		
				      		<p>
				      			Trenako.com, still in the <span class="label label-warning">BETA</span> stage, 
				      			was launched during the 2012 autumn season.
				      		</p>
				    	</div>
				    	<div class="tab-pane" id="tab2">
				    		<h2><s:message code="home.about.contact.tab.label"/></h2>
				      		<p>
								<strong>Trenako.com</strong> is still under heavy development; if you found issues
								or you would like to see new functionalities implemented please take the time
								to let us know creating a new issue 
								<a href="https://github.com/CarloMicieli/trenako/issues" target="_blank">here</a>.
							</p>
							<hr/>
							<p>
								You can contact the administrator at the following email address:
							</p>
							
							<address>
								<strong>Webmaster</strong><br>
								<a href="mailto:admin@trenako.com">admin@trenako.com</a>
							</address> 
				   	 	</div>
				  	</div>
				</div>				
			</div>
			<div class="span5"></div>
		</div>
	</body>
</html>
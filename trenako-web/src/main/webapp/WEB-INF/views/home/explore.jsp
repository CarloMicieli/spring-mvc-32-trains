<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title><s:message code="home.explore.title" text="explore"/></title>
	<meta name="home" content="active"/>
</head>

<body>

	<ul class="breadcrumb">
		<li>
			<s:url value="/home" var="homeUrl"/>
	    	<a href="${homeUrl}"><s:message code="home.title" text="Home"/></a> <span class="divider">/</span>
		</li>
	  	<li class="active"><s:message code="home.explore.title" text="explore"/></li>
	</ul>

	<div class="page-header">
		<h1><s:message code="home.explore.header.title" text="explore trenako" /></h1>
		<small><s:message code="home.explore.header.subtitle" text="Discover" /></small>
	</div>
	
	<div class="row-fluid">
    	<div class="span6">
    		<h2>Discover new rolling stocks</h2>
    		<p>discover</p>
    		<ul class="thumbnails">
        		<li>
		        	<div class="thumbnail">
			            <img src="http://placehold.it/260x180" alt="">
			            <div class="caption">
			            	<h4>Browse by brand</h4>
			              	<p>description goes here</p>
			              	<p><a href="#" class="btn btn-primary">See in action</a></p>
			            </div>
		          	</div>
		      	</li>
		      	<li>
		        	<div class="thumbnail">
			            <img src="http://placehold.it/260x180" alt="">
			            <div class="caption">
			            	<h4>Browse by railway</h4>
			              	<p>description goes here</p>
			              	<p><a href="#" class="btn btn-primary">See in action</a></p>
			            </div>
		          	</div>
		      	</li>
		      	<li>
		      			        		<h3>Browse by railway</h3>
		        		<p>
		        			Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus 
		        			ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo 
		        			sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.
						</p>
		        	<div class="thumbnail">
			            <img src="http://placehold.it/260x180" alt="">
			            <div class="caption">
			            </div>
		          	</div>
		      	</li>		      	
			</ul>
      	</div>
      	<div class="span6">
      		<h2>Create and manage your collection</h2>
      	</div>
  	</div>

</body>
</html>
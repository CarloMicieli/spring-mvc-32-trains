<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
	<head>
		<title><s:message code="home.explore.title" text="explore"/></title>
		<meta name="you" content="active"/>
	</head>
	
	<body>
	
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="home.title" text="Home"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active"><s:message code="home.explore.title" text="You"/></li>
		</ul>
		
		<div class="row-fluid">
			<div class="span1">
				<a href="http://gravatar.com/emails/">
					<img height="64" width="64" src="https://secure.gravatar.com/avatar/540b91c4528151f2bc8c3c0f5e16e889?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png">
				</a>
			</div>
			<div class="span5">
				<div class="page-header">
					<h1>${user.username}</h1>
					<small style="color:silver">(${user.displayName})</small>
				</div>
			</div>
			<div class="span6">
				<div class="pull-right">
					<a href="#" class="btn btn-info">Edit your profile</a>
				</div>
			</div>
			<hr />
		</div>
		
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span11">
				<hr />
				
				<div class="row-fluid">
					<div class="span2"><h3>In your collection:</h3></div>
					<div class="span1"><span style="font-size:24px; color:yellow">10</span> <p>steam locomotives</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">10</span> <p>diesel locomotives</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">10</span> <p>electric locomotives</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">20</span> <p>passenger cars</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">20</span> <p>freight cars</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">20</span> <p>electic multiple units</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">20</span> <p>railcars</p></div>
					<div class="span1"><span style="font-size:24px; color:yellow">20</span> <p>starter sets</p></div>
					<div class="span4"></div>
				</div>
			
				<hr />				
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span11">
				<div class="row-fluid">
					<div class="span4">
						<h2>From your collection</h2>
						<p>
							Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, 
							egestas eget quam. Vestibulum id ligula porta felis euismod semper. 
							Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum 
							nibh, ut fermentum massa justo sit amet risus.
						</p>
						<p>
							<a href="#" class="btn btn-success">View all</a>
						</p>
					</div>
					<div class="span4">
						<h2>From your wish lists</h2>
						<p>
							Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, 
							egestas eget quam. Vestibulum id ligula porta felis euismod semper. 
							Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum 
							nibh, ut fermentum massa justo sit amet risus.
						</p>
						<p>
							<a href="#" class="btn btn-warning">View all</a>
						</p>
					</div>
					<div class="span4">
						<h2>Your recent activity</h2>
						<p>
							Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, 
							egestas eget quam. Vestibulum id ligula porta felis euismod semper. 
							Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum 
							nibh, ut fermentum massa justo sit amet risus.
						</p>
						<p>
							<a href="#" class="btn btn-danger">View all</a>
						</p>				
					</div>			
				</div>
			</div>
		</div>
		
		<p />

	</body>
</html>
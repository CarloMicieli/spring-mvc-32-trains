<%@ page language="Java" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title><decorator:title default="Trenako"/></title>	
		<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet" />
		<style>
      	body {
        	padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      	}
    	</style>
		<link href="<c:url value="/resources/css/bootstrap-responsive.css" />" rel="stylesheet" />
		
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<decorator:head/>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
		   <div class="navbar-inner">
		     <div class="container">
		       <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
		         <span class="icon-bar"></span>
		         <span class="icon-bar"></span>
		         <span class="icon-bar"></span>
		       </a>
		       <a class="brand" href="#">Trenako</a>
		       <div class="nav-collapse">
		         <ul class="nav">
		         	<li class="<decorator:getProperty property="meta.home"/>">
						<a href="<c:url value="/home" />"><s:message code="home" text="Home"/></a></li>
		         	<li class="<decorator:getProperty property="meta.you"/>">
						<a href="<c:url value="/you" />"><s:message code="you" text="You"/></a></li>
		         	<li class="<decorator:getProperty property="meta.browse"/>">
						<a href="<c:url value="/browse" />"><s:message code="browse" text="Browse"/></a></li>		        
						<li class="dropdown" id="menu1">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#menu1">
      Admin
      <b class="caret"></b>
    </a>
					<ul class="dropdown-menu">
      					<li><a href="<c:url value="/admin/brands" />">Brands</a></li>
      					<li><a href="#">Another action</a></li>
      					<li><a href="#">Something else here</a></li>
      					<li class="divider"></li>
      					<li><a href="#">Separated link</a></li>
   		 			</ul>	
   		 			</li>
				</ul>
		       </div>
		    </div>
		  </div>
		</div>
	
		<div class="container">
			<decorator:body/>
			
			<footer>
        		<p>&copy; Trenako.com 2012</p>
      		</footer>
    	</div>
	  	
	  	<!-- Placed at the end of the document so the pages load faster -->
	  	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script>
    	
    	<script type="text/javascript">
    	$(document).ready(function() {
    		$('.menu').dropdown()
    	}
    	</script>
    	
	</body>
</html>
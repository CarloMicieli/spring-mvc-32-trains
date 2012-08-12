<%@ page language="Java" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title><decorator:title default="Trenako"/></title>	
		<link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
		<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet" />
		<style>
      	body {
        	padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      	}
      	.brand {
      		color: orange;
      		font: normal normal normal 24px/24px Lobster,Georgia,Times,'Times New Roman',serif;
      	}
    	</style>
		<link href="<c:url value="/resources/css/bootstrap-responsive.css" />" rel="stylesheet" />
		
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script>
    	
    	<script type="text/javascript">
    	$(document).ready(function() {
    		$('.menu').dropdown()
    	});
    	</script>
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
					<a class="brand" href="<c:url value="/" />">Trenako</a>
					<div class="btn-group pull-right">
					<sec:authorize access="authenticated" var="authenticated" />
						<c:choose>
							<c:when test="${authenticated}">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-user"></i> <sec:authentication property="principal.username" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#">Profile</a></li>
								<li class="divider"></li>
								<li><a href="<c:url value="/auth/logout" />">Sign Out</a></li>
							</ul>
						</c:when>
						<c:otherwise>
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="icon-user"></i> Starts here
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/auth/login" />">Login</a></li>
								<li class="divider"></li>
								<li><a href="<c:url value="/auth/signup" />">Sign up</a></li>
							</ul>
						</c:otherwise>
					</c:choose>

					</div>
					<div class="nav-collapse">
  						<ul class="nav">
  							<li class="<decorator:getProperty property="meta.home"/>">
								<a href="<c:url value="/home" />"><s:message code="menu.home.label"/></a>
							</li>
							<li class="<decorator:getProperty property="meta.you"/>">
								<a href="<c:url value="/you" />"><s:message code="menu.you.label"/></a>
							</li>
							<li class="<decorator:getProperty property="meta.rs"/>">
								<a href="<c:url value="/rs" />"><s:message code="menu.rollingStocks.label"/></a>
							</li>						
							<li class="<decorator:getProperty property="meta.browse"/>">
								<a href="<c:url value="/browse" />"><s:message code="menu.browse.label"/></a>
							</li>
							<sec:authorize url="/admin">        
							<li class="dropdown" id="menu1">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#menu1">
									<s:message code="menu.administration.label"/>
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="<c:url value="/admin/brands" />"><s:message code="menu.brands.label"/></a></li>
	     							<li><a href="<c:url value="/admin/railways" />"><s:message code="menu.railways.label"/></a></li>
	     							<li><a href="<c:url value="/admin/scales" />"><s:message code="menu.scales.label"/></a></li>
	     							<li class="divider"></li>
	     							<li><a href="#"><s:message code="menu.users.label"/></a></li>
	  		 					</ul>
	  		 				</li>
	  		 				</sec:authorize>
						</ul>
	       			</div>
	    		</div>
			</div>
		</div>

		<div class="container">
			<decorator:body/>
    	</div>

		<footer class="footer">
			<div class="container">
				<p></p>
				<p class="pull-right"><a href="#top"><s:message code="footer.back.top.label"/></a></p>
				<p>
					<s:message code="footer.designed.built.label"/> <a href="https://twitter.com/#!/chuckmiskyes" target="_blank">@chuckmiskyes</a>.
					<s:message code="footer.source.code.label"/> <a href="https://github.com/CarloMicieli/trenako" target="_blank">GitHub</a>.
				</p>
				<p>Theme based upon <strong>Twitter Bootstrap</strong> and <strong>Bootswatch.com's Amelia</strong>.</p>
			</div>
		</footer>
	</body>
</html>
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
		<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
		<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/bootstrap-responsive.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/trenako.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/jquery-ui-custom.css" />" rel="stylesheet" />
		
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
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
					<sec:authorize access="isAuthenticated()">
						<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
							<i class="icon-user"></i> <sec:authentication property="principal.username" />
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#">Profile</a></li>
							<li class="divider"></li>
							<li><a href="<c:url value="/auth/logout" />">Sign Out</a></li>
						</ul>
					</sec:authorize>
					</div>
					<div class="nav-collapse">
  						<ul class="nav">
  							<li class="<decorator:getProperty property="meta.home"/>">
								<a href="<c:url value="/home" />"><s:message code="menu.home.label"/></a>
							</li>
							<sec:authorize url="/you">
							<li class="<decorator:getProperty property="meta.you"/>">
								<a href="<c:url value="/you" />"><s:message code="menu.you.label"/></a>
							</li>
							</sec:authorize>
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
						<sec:authorize access="!isAuthenticated()">
						<s:url var="loginUrl" value="/j_spring_security_check" />
						<form class="navbar-form pull-right" method="POST" action="${loginUrl}">
							<input type="text" id="j_username" name="j_username" class="span2" placeholder="<s:message code="account.emailAddress.label" />" required="required"/>
							<input type="password" id="j_password" name="j_password" class="span2" placeholder="<s:message code="account.password.label" />" required="required"/>
			              	<button type="submit" class="btn btn-primary">
			              		<i class="icon-check icon-white"></i> <s:message code="button.login.label" />
			              	</button>
			            </form>
			            </sec:authorize>
	       			</div>
	    		</div>
			</div>
		</div>

		<div class="container">
			<decorator:body/>
    	</div>

		<footer class="footer">
			<div class="container">
				<div class="row-fluid">
					<div class="span6">
						<p>
							<s:url var="aboutUrl" value="/home/explore"/>
							<s:url var="devUrl" value="/home/developers"/>
							<s:url var="privacyUrl" value="/home/privacy"/>
							<s:url var="termsUrl" value="/home/terms"/>
							<a href="${aboutUrl}">About us</a> | <a href="${devUrl}">Developers</a> | <a href="${termsUrl}">Terms of use</a> | <a href="${privacyUrl}">Privacy</a>
							<br/>
							<s:message code="footer.designed.built.label"/> <a href="https://twitter.com/#!/chuckmiskyes" target="_blank">@chuckmiskyes</a>.
							<s:message code="footer.source.code.label"/> <a href="https://github.com/CarloMicieli/trenako" target="_blank">GitHub</a>.
							<br>
							Theme based upon <strong>Twitter Bootstrap</strong>.
						</p>
					</div>
					<div class="span3">
					<small>
						<a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/deed.it">
							<img alt="Licenza Creative Commons" 
								style="border-width: 0" 
								src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
						</a>
						<br />
						Except where otherwise
						<a class="subfoot" href="${termsUrl}">noted</a>,
						content on
						<strong>this site</strong> 
						is licensed under a 
						<a rel="license" href="http://creativecommons.org/licenses/by/3.0/" class="subfoot">
						Creative Commons Attribution 3.0 License
						</a>.
					</small>
					</div>
					<div class="span3">
						<p class="pull-right"><a href="#top"><s:message code="footer.back.top.label"/></a></p>
					</div>
				</div>
			</div>
		</footer>
	</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>
			<s:message code="login.title" />
		</title>
		<meta name="home" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span6">
				<div class="page-header">
					<h1>Sign in with Trenako.com...</h1>
				</div>
				<s:url var="loginUrl" value="/j_spring_security_check" />
				<form:form id="form" class="form-horizontal" method="POST" action="${loginUrl}" >
				    <div class="control-group">
						<label for="j_username" class="control-label">
							<s:message code="account.emailAddress.label" />:
						</label>
						<div class="controls">
							<input type="text" id="j_username" name="j_username" class="input-xlarge focused" required="required"/>
						</div>
					</div>
					
				    <div class="control-group">
						<label for="j_password" class="control-label">
							<s:message code="account.password.label" />:
						</label>
						<div class="controls">
							<input type="password" id="j_password" name="j_password" class="input-xlarge focused" required="required"/>
						</div>
					</div>
					
					<div class="controls">
						<label class="checkbox">
							<input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me">
							Remember me
					</label>
					</div>
					
					<c:if test="${param.login_error != null}">
					<div class="alert alert-error">
						<s:message code="login.error.message"></s:message>
					</div>
					</c:if>
					
					<div class="form-actions">
						<button type="submit" class="btn btn-primary" name="_action_save">
							<i class="icon-check icon-white"></i>
							<s:message code="button.login.label" />
						</button>
					
						<button class="btn" type="reset" name="_action_reset">
							<i class="icon-repeat icon-black"></i>
							<s:message code="button.reset.label" />
						</button>
					</div>
				</form:form>
			</div>
			<div class="span6">
				<div class="page-header pull-right">
					<h1>...or use your account for one of these sites</h1>
				</div>
				<div class="row-fluid">
					<div class="span12">
						Do you already have an account on one of these sites? Click the logo to log in with it here:
					</div>
				</div>
				<div>
					<div class="span2"></div>
					<div class="span10">
						<ul class="unstyled">
							<li>
								<a href="#">
									<img width="64" height="64" src="<s:url value="/resources/img/social/google.png"/>" />
									Sign in with Google
								</a>
							</li>
							<li>
								<a href="#">
									<img width="64" height="64" src="<s:url value="/resources/img/social/yahoo.png"/>" />
									Sign in with Yahoo
								</a>
							</li>
							<li>
								<a href="#">
									<img width="64" height="64" src="<s:url value="/resources/img/social/twitter.png"/>" />
									Sign in with Twitter
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>		
		</div>
	</body>
</html>
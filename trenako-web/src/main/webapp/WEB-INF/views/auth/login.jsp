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
		<div class="page-header">
			<h1><s:message code="login.title.label"/></h1>
		</div>

		<div class="row-fluid">
			<div class="span8 offset1 well">
				<h3>
					<s:url var="signupUrl" value="/auth/signup" />
					<s:message code="signup.new.user.label"/> 
					<a href="${signupUrl}"><s:message code="signup.sign.up.here.label"/></a>
					<s:message code="signup.easy.free.label"/>
				</h3>
			</div>		
		</div>

		<div class="row-fluid">
			<div class="span8 offset1">
				<s:url var="loginUrl" value="/j_spring_security_check" />
				<form:form id="form" class="form-horizontal" method="POST" action="${loginUrl}" >
					<c:if test="${param.login_error != null}">
					<div class="alert alert-error">
						<s:message code="login.error.message"></s:message>
					</div>
					</c:if>
				
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
							<s:message code="account.remember.me.label"/>
					</label>
					</div>
					
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
		</div>
	</body>
</html>
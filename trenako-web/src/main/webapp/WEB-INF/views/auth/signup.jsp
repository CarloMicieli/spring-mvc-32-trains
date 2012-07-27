<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>
			<s:message code="signup.title" />
		</title>
		<meta name="home" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
			</div>
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="signup.title.label" /></h1>
				</div>
	
				<s:url var="signupUrl" value="/auth/signup" />
				<form:form id="form" class="form-horizontal" method="POST" action="${signupUrl}" modelAttribute="account">
				    
   					<s:bind path="account.emailAddress">
   					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<form:label path="emailAddress" cssClass="control-label">
							<s:message code="account.emailAddress.label" />:
						</form:label>
						<div class="controls">
							<form:input path="emailAddress" cssClass="input-xlarge focused" required="required"/>
							<form:errors path="emailAddress" element="span" cssClass="help-inline"/>
						</div>
					</div>
					</s:bind>
				    
				    <s:bind path="account.displayName">
				    <div class="control-group">
						<form:label path="displayName" cssClass="control-label">
							<s:message code="account.displayName.label" />:
						</form:label>
						<div class="controls">
							<form:input path="displayName" cssClass="input-xlarge focused" required="required"/>
							<form:errors path="displayName" element="span" cssClass="help-inline"/>
						</div>
					</div>
					</s:bind>

				    <s:bind path="account.password">
				    <div class="control-group">
						<form:label path="password" cssClass="control-label">
							<s:message code="account.password.label" />:
						</form:label>
						<div class="controls">
							<form:password path="password" cssClass="input-xlarge focused" required="required"/>
							<form:errors path="password" element="span" cssClass="help-inline"/>
						</div>
					</div>
					</s:bind>				

					<div class="form-actions">
						<form:button class="btn btn-primary" type="submit" name="_action_save">
							<i class="icon-user icon-white"></i>
							<s:message code="button.signup.label" />
						</form:button>
					
						<form:button class="btn" type="reset" name="_action_reset">
							<i class="icon-repeat icon-black"></i>
							<s:message code="button.reset.label" />
						</form:button>
					</div>					
					
				</form:form>
			</div>
		</div>
	</body>
</html>
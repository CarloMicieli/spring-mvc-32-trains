<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		<s:message code="login.title" />
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
			</div>
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="login.title.label" /></h1>
				</div>
				<s:url var="loginUrl" value="/j_spring_security_check" />
				<form:form id="form" class="form-horizontal" method="POST" action="${loginUrl}" >
				    
				    <div class="control-group">
						<label for="j_username" class="control-label">
							<s:message code="account.username.label" />:
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
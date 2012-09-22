<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
				
<form id="form" class="form-vertical" action="<s:url value="/auth/signup"/>" method="POST">
	<fieldset>
		<legend><s:message code="signup.title.label" /></legend>
	</fieldset>

	<div class="control-group">
		<label for="emailAddress" class="control-label">
			<s:message code="account.emailAddress.label" />:
		</label>
		<div class="controls">
			<s:message var="email" code="account.emailAddress.help.label" />
			<input id="emailAddress" name="emailAddress" class="input-xlarge focused" required="required" type="text" value="" placeholder="${email}"/>
		</div>
	</div>

	<div class="control-group">
		<label for="displayName" class="control-label">
			<s:message code="account.displayName.label" />:
		</label>
		<div class="controls">
			<s:message var="name" code="account.displayName.help.label" />
			<input id="displayName" name="displayName" class="input-xlarge focused" required="required" type="text" value="" placeholder="${name}"/>
		</div>
	</div>

	<div class="control-group">
		<label for="password" class="control-label">
			<s:message code="account.password.label" />:
		</label>
		<div class="controls">
			<s:message var="pwd" code="account.password.help.label" text="At least 6 characters"/>
			<input id="password" name="password" data-typetoggle="#show-password" class="input-xlarge focused" required="required" type="password" value="" placeholder="${pwd}"/>
			<label class="checkbox">
				<input id="show-password" type="checkbox"> <s:message code="account.showPassword.label" />
			</label>
		</div>
	</div>

	<div class="form-actions">
		<button id="_action_save" name="_action_save" class="btn btn-primary" type="submit" type="submit" value="Submit">
			<i class="icon-user icon-white"></i>
			<s:message code="button.signup.label" />
		</button>

		<button id="_action_reset" name="_action_reset" class="btn" type="reset" type="submit" value="Submit">
			<i class="icon-repeat icon-black"></i>
			<s:message code="button.reset.label" />
		</button>
	</div>
</form>
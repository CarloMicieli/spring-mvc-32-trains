<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="options.new.title.label" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="options.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/options" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="options.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="option.new.title.label" /></h1>
				</div>
				<s:url var="createUrl" value="/admin/options" />
				<form:form id="form" class="form-horizontal" method="POST" action="${createUrl}" modelAttribute="newForm" enctype="multipart/form-data">

					<fieldset>    				
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>

    					<s:bind path="newForm.option.name">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="option.name" cssClass="control-label">
								<s:message code="option.name.label" />:
							</form:label>
							<div class="controls">
								<form:input path="option.name" cssClass="input-xlarge focused" required="required"/>
								<form:errors path="option.name" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="newForm.option.family">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="option.family" cssClass="control-label">
									<s:message code="option.family.label" />:
								</form:label>
								<div class="controls">
									<form:select path="option.family">
										<form:option value="" label="--families--"/>
										<form:options items="${familiesList}" itemLabel="label" itemValue="key"/>
									</form:select>
									<form:errors path="option.family" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>
						
						<div class="control-group">
							<label class="control-label" for="file">
								<s:message code="option.logo.label" />:
							</label>
							<div class="controls">
								<input class="input-file" id="file" name="file" type="file">
								<form:errors element="span" cssClass="help-inline"/>
								<p class="help-block">
									<s:message code="option.logo.help.label" />
								</p>
							</div>
						</div>
						
						<div class="form-actions">
							<form:button class="btn btn-primary" type="submit" name="_action_save">
								<i class="icon-check icon-white"></i>
								<s:message code="button.save.label" />
							</form:button>

							<form:button class="btn" type="reset" name="_action_reset">
								<i class="icon-repeat icon-black"></i>
								<s:message code="button.reset.label" />
							</form:button>						
						</div>				
					</fieldset>
				</form:form>
			</div>
		</div>	
	</body>
</html>
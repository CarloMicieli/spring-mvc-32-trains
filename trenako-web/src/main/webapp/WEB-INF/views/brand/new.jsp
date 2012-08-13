<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="brands.new.title.label" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="brands.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="brands.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="brand.new.title.label" /></h1>
				</div>
				<s:url var="createUrl" value="/admin/brands" />
				<form:form id="form" class="form-horizontal" method="POST" action="${createUrl}" modelAttribute="brand" enctype="multipart/form-data">

					<fieldset>    				
    					<c:if test="${not empty message}">
							<div id="message" class="info">${message}</div>
						</c:if>

    					<s:bind path="brand.name">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="name" cssClass="control-label">
								<s:message code="brand.name.label" />:
							</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused" required="required"/>
								<form:errors path="name" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="brand.companyName">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="companyName" cssClass="control-label">
								<s:message code="brand.companyName.label" />:
							</form:label>
							<div class="controls">
								<form:input path="companyName" cssClass="input-xlarge focused"/>
								<form:errors path="companyName" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

						<tk:localizedTextArea path="description" rows="4"/>

						<s:bind path="brand.website">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="website" cssClass="control-label">
								<s:message code="brand.website.label" />:
							</form:label>
							<div class="controls">
								<form:input path="website" type="url" cssClass="input-xlarge focused"/>
								<form:errors path="website" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

						<s:bind path="brand.emailAddress">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="emailAddress" cssClass="control-label">
								<s:message code="brand.emailAddress.label" />:
							</form:label>
							<div class="controls">
								<div class="input-prepend">
									<span class="add-on">@</span><form:input type="email" path="emailAddress" cssClass="input-xlarge focused"/>
								</div>
								<form:errors path="emailAddress" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

						<div class="control-group">
							<form:label path="industrial" cssClass="control-label">
								<s:message code="brand.industrial.label" />:
							</form:label>
							<div class="controls">
								<label class="checkbox">
									<form:checkbox path="industrial"/>
									<s:message code="brand.industrial.help.label" /> 
								</label>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="file">
								<s:message code="brand.logo.label" />:
							</label>
							<div class="controls">
								<input class="input-file" id="file" name="file" type="file">
								<form:errors element="span" cssClass="help-inline"/>
								<p class="help-block">
									<s:message code="brand.logo.help.label" />
								</p>
							</div>
						</div>

						<fieldset class="embedded address">
							<legend><s:message code="brand.address.label" /></legend>

	    					<s:bind path="brand.address.streetAddress">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="address.streetAddress" cssClass="control-label">
									<s:message code="brand.address.streetAddress.label" />:
								</form:label>
								<div class="controls">
									<form:input path="address.streetAddress" cssClass="input-xlarge focused"/>
									<form:errors path="address.streetAddress" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="brand.address.postalCode">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="address.postalCode" cssClass="control-label">
									<s:message code="brand.address.postalCode.label" />:
								</form:label>
								<div class="controls">
									<form:input path="address.postalCode" cssClass="input-xlarge focused"/>
									<form:errors path="address.postalCode" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="brand.address.city">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="address.city" cssClass="control-label">
									<s:message code="brand.address.city.label" />:
								</form:label>
								<div class="controls">
									<form:input path="address.city" cssClass="input-xlarge focused"/>
									<form:errors path="address.city" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="brand.address.locality">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="address.locality" cssClass="control-label">
									<s:message code="brand.address.locality.label" />:
								</form:label>
								<div class="controls">
									<form:input path="address.locality" cssClass="input-xlarge focused"/>
									<form:errors path="address.locality" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="brand.address.country">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="address.country" cssClass="control-label">
									<s:message code="brand.address.country.label" />:
								</form:label>
								<div class="controls">
								<form:select path="address.country">
									<form:option value="" label="--countries--"/>
									<form:options items="${countries}"/>
								</form:select>
									<form:errors path="address.country" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>
						</fieldset>

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
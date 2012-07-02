<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		<s:message code="railways.edit.title.label" arguments="${railway.name}" />
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="railways.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/railways" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="railways.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="railway.edit.title.label" arguments="${railway.name}" /></h1>
				</div>
				<s:url var="editUrl" value="/admin/railways" />
				<form:form id="form" class="form-horizontal" method="PUT" action="${editUrl}" modelAttribute="railway">

					<form:hidden path="id"/>

					<fieldset>
    					<c:if test="${not empty message}">
							<div id="message" class="info">${message}</div>
						</c:if>
 
    					<s:bind path="railway.name">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="name" cssClass="control-label">
								<s:message code="railway.name.label" />:
							</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused" required="required"/>
								<form:errors path="name" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="railway.companyName">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="companyName" cssClass="control-label">
								<s:message code="railway.companyName.label" />:
							</form:label>
							<div class="controls">
								<form:input path="companyName" cssClass="input-xlarge focused"/>
								<form:errors path="companyName" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="railway.country">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="country" cssClass="control-label">
								<s:message code="railway.country.label" />:
							</form:label>
							<div class="controls">
							<form:select path="country">
								<form:option value="" label="--countries--"/>
								<form:options items="${countries}"/>
							</form:select>
							<form:errors path="country" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

						<s:bind path="railway.operatingSince">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="operatingSince" cssClass="control-label">
								<s:message code="railway.operatingSince.label" />:
							</form:label>
							<div class="controls">
								<form:input path="operatingSince" type="date" cssClass="input-xlarge focused"/>
								<form:errors path="operatingSince" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

						<s:bind path="railway.operatingUntil">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="operatingUntil" cssClass="control-label">
								<s:message code="railway.operatingUntil.label" />:
							</form:label>
							<div class="controls">
								<form:input path="operatingUntil" type="date" cssClass="input-xlarge focused"/>
								<form:errors path="operatingUntil" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>

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
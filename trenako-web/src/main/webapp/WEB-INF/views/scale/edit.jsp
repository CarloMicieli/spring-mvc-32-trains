<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="scales.edit.title.label" arguments="${scale.name}" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scales.header.title.label" />
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="scales.edit.title.label" arguments="${scale.name}" /></h1>
				</div>
				<s:url var="editUrl" value="/admin/scales" />
				<form:form id="form" class="form-horizontal" method="PUT" action="${editUrl}" modelAttribute="scale">
					
					<form:hidden path="id"/>
					<form:hidden path="slug"/>
					
					<fieldset>
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
    				
    					<s:bind path="scale.name">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="name" cssClass="control-label">
								<s:message code="scale.name.label" />:
							</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused" required="required"/>
								<form:errors path="name" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<tk:localizedTextArea path="description" rows="4"/>
						
						<s:bind path="scale.ratio">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="ratio" cssClass="control-label">
								<s:message code="scale.ratio.label" />:
							</form:label>
							<div class="controls">
								<form:input path="ratio" type="number" min="8" max="220" step="0.1" cssClass="input-xlarge focused"/>
								<form:errors path="ratio" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="scale.gauge">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="gauge" cssClass="control-label">
								<s:message code="scale.gauge.label" />:
							</form:label>
							<div class="controls">
								<form:input path="gauge" type="number" min="0" max="200" step="0.01" cssClass="input-xlarge focused"/>
								<form:errors path="gauge" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<div class="control-group">
							<form:label path="narrow" cssClass="control-label">
								<s:message code="scale.narrow.label" />:
							</form:label>
							<div class="controls">
								<label class="checkbox">
									<form:checkbox path="narrow"/>
									<s:message code="scale.narrow.help.label" /> 
								</label>
							</div>
						</div>
						
						<fieldset class="embedded standards">
							<legend><s:message code="scale.standards.label" /></legend>
						
							<div class="control-group">
								<label class="control-label" for="inlineCheckboxes">
									<s:message code="scale.standards.label" />:
								</label>
								<div class="controls">
									<c:forEach var="st" items="${standards}">
										<label class="checkbox inline">
											<form:checkbox path="standards" value="${st}"/>
											${st}
										</label>
									</c:forEach>
								</div>
							</div>
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
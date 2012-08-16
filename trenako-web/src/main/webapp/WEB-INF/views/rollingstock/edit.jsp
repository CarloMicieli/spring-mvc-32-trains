<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="rollingStock.page.new.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
			</div>
			
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="rollingStock.edit.title.label" /></h1>
				</div>
				<s:url var="saveUrl" value="/rollingstocks" />
				<form:form id="form" class="form-horizontal" method="PUT" action="${saveUrl}" modelAttribute="rollingStock">
					
					<fieldset>
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
						
						<form:hidden path="id"/>
						<form:hidden path="slug"/>
						<form:hidden path="brand"/>
						<form:hidden path="itemNumber"/>
						
						<s:bind path="rollingStock.railway">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="railway" cssClass="control-label">
								<s:message code="rollingStock.railway.label" />:
							</form:label>
							<div class="controls">
							<form:select path="railway" required="required">
								<form:option value="" label="--railways--"/>
								<form:options items="${railways}" itemValue="slug" itemLabel="name" />
							</form:select>
							<form:errors path="railway" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStock.scale">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="scale" cssClass="control-label">
								<s:message code="rollingStock.scale.label" />:
							</form:label>
							<div class="controls">
							<form:select path="scale" required="required">
								<form:option value="" label="--scales--"/>
								<form:options items="${scales}" itemValue="slug"/>
							</form:select>
							<form:errors path="scale" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStock.era">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="era" cssClass="control-label">
								<s:message code="rollingStock.era.label" />:
							</form:label>
							<div class="controls">
							<form:select path="era">
								<form:option value="" label="--eras--"/>
								<form:options items="${eras}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="era" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStock.category">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="category" cssClass="control-label">
								<s:message code="rollingStock.category.label" />:
							</form:label>
							<div class="controls">
							<form:select path="category" required="required">
								<form:option value="" label="--categories--"/>
								<form:options items="${categories}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="category" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
					 	<s:bind path="rollingStock.powerMethod">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="powerMethod" cssClass="control-label">
								<s:message code="rollingStock.powerMethod.label" />:
							</form:label>
							<div class="controls">
							<form:select path="powerMethod">
								<form:option value="" label="--power methods--"/>
								<form:options items="${powerMethods}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="powerMethod" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<fieldset class="embedded description">
							<legend>
								<s:message code="rollingStock.descriptions.title.label" />
							</legend>
							<p>
								This is the default description. Please, take the time to enter
								a short description in English.
							</p>
								
							<tk:localizedTextArea path="description" rows="3"/>
							
							<tk:localizedTextArea path="details" rows="6"/>
						</fieldset>
						
						<fieldset class="embedded options">
							<legend><s:message code="rollingStock.descriptions.options.label" /></legend>
						
							<s:bind path="rollingStock.totalLength">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="totalLength" cssClass="control-label">
									<s:message code="rollingStock.totalLength.label" />:
								</form:label>
								<div class="controls">
									<form:input type="number" path="totalLength" cssClass="input-xlarge focused"/>
									<form:errors path="totalLength" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>
							
							<s:bind path="rollingStock.deliveryDate">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="deliveryDate" cssClass="control-label">
									<s:message code="rollingStock.deliveryDate.label" />:
								</form:label>
								<div class="controls">
								<form:select path="deliveryDate">
									<form:option value="" label="--delivery date--"/>
									<form:options items="${deliveryDates}"/>
								</form:select>
								<form:errors path="deliveryDate" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>
							
							<s:bind path="rollingStock.upcCode">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="upcCode" cssClass="control-label">
									<s:message code="rollingStock.upcCode.label" />:
								</form:label>
								<div class="controls">
									<form:input path="upcCode" cssClass="input-xlarge focused"/>
									<form:errors path="upcCode" element="span" cssClass="help-inline"/>
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
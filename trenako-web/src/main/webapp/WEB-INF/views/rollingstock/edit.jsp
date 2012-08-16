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
				<form:form id="form" class="form-horizontal" method="PUT" action="${saveUrl}" modelAttribute="rollingStockForm">
					<fieldset>
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
						
						<form:hidden path="rs.id"/>
						<form:hidden path="rs.slug"/>
						<form:hidden path="rs.brand"/>
						<form:hidden path="rs.itemNumber"/>
						
						<s:bind path="rollingStockForm.rs.railway">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="rs.railway" cssClass="control-label">
								<s:message code="rollingStock.railway.label" />:
							</form:label>
							<div class="controls">
							<form:select path="rs.railway" required="required">
								<s:message code="rollingStock.railways.list.label" var="railwaysLabel"/>
								<form:option value="" label="${railwaysLabel}"/>
								<form:options items="${rollingStockForm.railwaysList}" itemValue="slug" itemLabel="name" />
							</form:select>
							<form:errors path="rs.railway" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStockForm.rs.scale">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="rs.scale" cssClass="control-label">
								<s:message code="rollingStock.scale.label" />:
							</form:label>
							<div class="controls">
							<form:select path="rs.scale" required="required">
								<s:message code="rollingStock.scales.list.label" var="scalesLabel"/>
								<form:option value="" label="${scalesLabel}"/>
								<form:options items="${rollingStockForm.scalesList}" itemValue="slug"/>
							</form:select>
							<form:errors path="rs.scale" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStockForm.rs.era">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="rs.era" cssClass="control-label">
								<s:message code="rollingStock.era.label" />:
							</form:label>
							<div class="controls">
							<form:select path="rs.era">
								<s:message code="rollingStock.eras.list.label" var="erasLabel"/>
								<form:option value="" label="${erasLabel}"/>
								<form:options items="${rollingStockForm.erasList}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="rs.era" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="rollingStockForm.rs.category">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="rs.category" cssClass="control-label">
								<s:message code="rollingStock.category.label" />:
							</form:label>
							<div class="controls">
							<form:select path="rs.category" required="required">
								<s:message code="rollingStock.categories.list.label" var="categoriesLabel"/>
								<form:option value="" label="${categoriesLabel}"/>
								<form:options items="${rollingStockForm.categoriesList}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="rs.category" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
					 	<s:bind path="rollingStockForm.rs.powerMethod">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="rs.powerMethod" cssClass="control-label">
								<s:message code="rollingStock.powerMethod.label" />:
							</form:label>
							<div class="controls">
							<form:select path="rs.powerMethod">
								<s:message code="rollingStock.powerMethods.list.label" var="powerMethodsLabel"/>
								<form:option value="" label="${powerMethodsLabel}"/>
								<form:options items="${rollingStockForm.powerMethodsList}" itemLabel="label" itemValue="key"/>
							</form:select>
							<form:errors path="rs.powerMethod" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<fieldset class="embedded description">
							<legend>
								<s:message code="rollingStock.descriptions.title.label" />
							</legend>
							<p>
								<s:message code="rollingStock.descriptions.help.label" />
							</p>
								
							<tk:localizedTextArea path="rs.description" rows="3"/>
							
							<tk:localizedTextArea path="rs.details" rows="6"/>
						</fieldset>
					
						<fieldset class="embedded options">
							<legend><s:message code="rollingStock.descriptions.options.label" /></legend>
						
							<s:bind path="rollingStockForm.rs.totalLength">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="rs.totalLength" cssClass="control-label">
									<s:message code="rollingStock.totalLength.label" />:
								</form:label>
								<div class="controls">
									<form:input type="number" path="rs.totalLength" cssClass="input-xlarge focused"/>
									<form:errors path="rs.totalLength" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>
							
							<s:bind path="rollingStockForm.rs.deliveryDate">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="rs.deliveryDate" cssClass="control-label">
									<s:message code="rollingStock.deliveryDate.label" />:
								</form:label>
								<div class="controls">
								<form:select path="rs.deliveryDate">
									<s:message code="rollingStock.deliveryDates.list.label" var="deliveryDatesLabel"/>
									<form:option value="" label="${deliveryDatesLabel}"/>								
									<form:options items="${deliveryDates}"/>
								</form:select>
								<form:errors path="rs.deliveryDate" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>
							
							<s:bind path="rollingStockForm.rs.upcCode">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="rs.upcCode" cssClass="control-label">
									<s:message code="rollingStock.upcCode.label" />:
								</form:label>
								<div class="controls">
									<form:input path="rs.upcCode" cssClass="input-xlarge focused"/>
									<form:errors path="rs.upcCode" element="span" cssClass="help-inline"/>
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
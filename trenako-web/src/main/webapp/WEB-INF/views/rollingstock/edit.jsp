<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="rollingStock.page.edit.title" />
		</title>
		<meta name="rs" content="active"/>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.tagsinput.js" />"></script>
		<link href="<c:url value="/resources/css/jquery.tagsinput.css" />" rel="stylesheet" />
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
				<html:form model="rollingStockForm" actionUrl="${saveUrl}" method="PUT">
					<form:hidden path="rs.id"/>
					<form:hidden path="rs.slug"/>
					<form:hidden path="rs.brand"/>
					<form:hidden path="rs.itemNumber"/>

					<html:dropdownList items="${rollingStockForm.railwaysList}" 
						bindContext="rollingStockForm"
						name="rs.railway" 
						label="rollingStock.railway.label" 
						optionsLabel="rollingStock.railways.list.label"
						itemLabel="name" itemValue="slug"/>
						
					<html:dropdownList items="${rollingStockForm.scalesList}" 
						bindContext="rollingStockForm"
						name="rs.scale" 
						label="rollingStock.scale.label" 
						optionsLabel="rollingStock.scales.list.label"
						itemValue="slug" itemLabel="label"/>
						
					<html:dropdownList items="${rollingStockForm.erasList}" 
						bindContext="rollingStockForm"
						name="rs.era" 
						label="rollingStock.era.label" 
						optionsLabel="rollingStock.eras.list.label" 
						itemLabel="label" itemValue="key"/>

					<html:dropdownList items="${rollingStockForm.categoriesList}" 
						bindContext="rollingStockForm"
						name="rs.category" 
						label="rollingStock.category.label" 
						optionsLabel="rollingStock.categories.list.label" 
						itemLabel="label" itemValue="key"/>
					
					<html:dropdownList items="${rollingStockForm.powerMethodsList}" 
						bindContext="rollingStockForm"
						name="rs.powerMethod" 
						label="rollingStock.powerMethod.label" 
						optionsLabel="rollingStock.powerMethods.list.label" 
						itemLabel="label" itemValue="key"/>
						
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

					<html:uploadFile label="rollingstock.picture.label" 
						bindContext="rollingStockForm" 
						name="file" 
						helpLabel="rollingstock.picture.help.label"/>
						
					<s:bind path="rollingStockForm.tags">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<form:label path="tags" cssClass="control-label">
							<s:message code="rollingStock.tags.label" />:
						</form:label>
						<div class="controls">
							<form:input path="tags" cssClass="input-xlarge focused tags"/>
							<form:errors path="tags" element="span" cssClass="help-inline"/>
						</div>
					</div>
					</s:bind>
					
					<fieldset class="embedded options">
						<legend><s:message code="rollingStock.descriptions.options.label" /></legend>
						
						<html:spinner label="rollingStock.totalLength.label" 
							bindContext="rollingStockForm" 
							name="rs.totalLength" 
							min="0" max="9999" step="0.1" 
							isRequired="false"/>
						
						<html:dropdownList items="${rollingStockForm.deliveryDates}" 
							bindContext="rollingStockForm"
							name="rs.deliveryDate" 
							label="rollingStock.deliveryDate.label" 
							optionsLabel="rollingStock.deliveryDates.list.label"/>

						<html:textBox label="rollingStock.upcCode.label" 
							bindContext="rollingStockForm" 
							name="rs.upcCode" 
							isRequired="false"/>							
					</fieldset>
				</html:form>
			</div>
		</div>
		
		<script type="text/javascript">
    	$(document).ready(function() {
    		$('#tags').tagsInput();
    	});
    	</script>
	</body>
</html>
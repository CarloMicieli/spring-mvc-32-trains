<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<%@ attribute name="bindContext" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="titleLabel" required="true" rtexprvalue="false" %>
<%@ attribute name="countries" required="true" rtexprvalue="true" type="java.util.SortedMap" %>

<fieldset class="embedded address">
	<legend><s:message code="${titleLabel}" /></legend>

	<html:textBox label="address.streetAddress.label"
		name="${name}.streetAddress" 
		bindContext="${bindContext}" 
		isRequired="false" />

	<html:textBox label="address.postalCode.label"
		name="${name}.postalCode" 
		bindContext="${bindContext}" 
		isRequired="false" />
		
	<html:textBox label="address.city.label"
		name="${name}.city" 
		bindContext="${bindContext}" 
		isRequired="false" />
		
	<html:textBox label="address.locality.label"
		name="${name}.locality" 
		bindContext="${bindContext}" 
		isRequired="false" />
	
	<html:dropdownList 
 		items="${countries}" 
 		label="address.country.label" 
 		optionsLabel="address.countries.label" 
 		bindContext="brand"  
 		name="address.country" /> 

</fieldset>
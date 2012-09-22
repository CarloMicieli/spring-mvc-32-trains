<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="railways.edit.title.label" arguments="${railwayForm.railway.name}" />
		</title>
	</head>
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
					<h1><s:message code="railway.edit.title.label" arguments="${railwayForm.railway.name}" /></h1>
				</div>
				<s:url var="editUrl" value="/admin/railways" />
				<html:form model="railwayForm" actionUrl="${editUrl}" method="PUT">

					<form:hidden path="railway.id"/>
					<form:hidden path="railway.slug"/>
					
					<html:textBox bindContext="railwayForm" 
						name="railway.name" 
						label="railway.name.label" 
						isRequired="true"/>
				
					<html:textBox bindContext="railwayForm" 
						name="railway.companyName" 
						label="railway.companyName.label" 
						isRequired="false"/>

					<tk:localizedTextArea path="railway.description" rows="4"/>

					<html:dropdownList items="${countries}" 
						label="railway.country.label"
						bindContext="railwayForm"  
						name="railway.country" 
						optionsLabel="railway.countries.label"/>
					
					<html:textBox bindContext="railwayForm" 
						name="railway.operatingSince" 
						label="railway.operatingSince.label" 
						isRequired="false"/>

					<html:textBox bindContext="railwayForm" 
						name="railway.operatingUntil" 
						label="railway.operatingUntil.label" 
						isRequired="false"/>
					
				</html:form>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#railway\\.operatingSince").datepicker({ dateFormat: "yy-mm-dd" });
				$("#railway\\.operatingUntil").datepicker({ dateFormat: "yy-mm-dd" });
			});
		</script>	
	</body>
</html>
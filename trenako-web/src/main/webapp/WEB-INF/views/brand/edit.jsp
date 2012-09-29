<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="brands.edit.title.label" arguments="${brandForm.brand.name}" />
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
					<h1><s:message code="brand.edit.title.label" arguments="${brandForm.brand.name}" /></h1>
				</div>
				<s:url var="editUrl" value="/admin/brands"/>
				
				<html:form actionUrl="${editUrl}" model="brandForm" method="PUT">
					<form:hidden path="brand.id"/>
					<form:hidden path="brand.slug"/>
					<form:hidden path="brand.name"/>
										
					<html:textBox bindContext="brandForm" 
						name="brand.companyName" 
						label="brand.companyName.label" 
						isRequired="false"/>

					<tk:localizedTextArea path="brand.description" rows="4"/>

					<html:url bindContext="brandForm" name="brand.website" label="brand.website.label"/>
					<html:email bindContext="brandForm" name="brand.emailAddress" label="brand.emailAddress.label"/>

					<html:checkBox bindContext="brandForm" name="brand.industrial" 
						label="brand.industrial.label"
						helpLabel="brand.industrial.help.label" />

					<html:multiCheckBox items="${brandForm.scalesList}" 
						label="brand.scales.label" 
						name="brand.scales" 
						title="brand.scales.label"/>

					<html:address countries="${countries}" 
						bindContext="brandForm"
						name="brand.address" 
						titleLabel="brand.address.label"/>
				</html:form>
			</div>
		</div>	
	</body>
</html>
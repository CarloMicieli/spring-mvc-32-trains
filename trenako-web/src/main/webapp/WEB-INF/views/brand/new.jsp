<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

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
				
				<html:uploadForm actionUrl="${createUrl}" model="brandForm">
			
					<html:textBox bindContext="brandForm" 
						name="brand.name" 
						label="brand.name.label" 
						isRequired="true"/>
								
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

					<html:uploadFile bindContext="brandForm" name="file" 
						label="brand.logo.label"
						helpLabel="brand.logo.help.label" />

					<html:multiCheckBox items="${brandForm.scalesList}" 
						label="brand.scales.label" 
						name="brand.scales" 
						title="brand.scales.label"/>

					<html:address countries="${countries}" 
						bindContext="brandForm"
						name="brand.address" 
						titleLabel="brand.address.label"/>
				</html:uploadForm>
			</div>
		</div>	
	</body>
</html>
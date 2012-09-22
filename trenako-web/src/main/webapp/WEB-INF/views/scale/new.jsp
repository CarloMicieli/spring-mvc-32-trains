<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title><s:message code="scales.new.title.label" /></title>
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
					<h1><s:message code="scales.new.title.label" /></h1>
				</div>
				<s:url var="createUrl" value="/admin/scales" />
				<html:form actionUrl="${createUrl}" model="scale" method="POST">
					<html:textBox label="scale.name.label" name="name" bindContext="scale" isRequired="true"/>
					<tk:localizedTextArea path="description" rows="4"/>
					
					<html:spinner label="scale.ratio.label" 
						bindContext="scale" 
						name="ratio"
						min="8" max="220" step="0.1"				
						isRequired="true"/>
					<html:spinner label="scale.gauge.label" 
						bindContext="scale"
						min="0" max="1000" step="0.01"
						name="gauge"
						isRequired="false"/>
					
					<html:checkBox label="scale.narrow.label" name="narrow" bindContext="scale" helpLabel="scale.narrow.help.label"/>	
						
					<html:multiCheckBox items="${standards}"
						name="standards"  
						title="scale.standards.label"
						label="scale.standards.label"/>
				</html:form>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var input = document.createElement('input');
			    input.setAttribute('type', 'number');

			    if (input.type == 'text') {
			    	$("#ratio").spinner({min: 8, max: 220, step: 0.1});
			    	$("#gauge").spinner({min: 0, max: 200, step: 0.01});	
			    }
			}); 
		</script>
	</body>
</html>
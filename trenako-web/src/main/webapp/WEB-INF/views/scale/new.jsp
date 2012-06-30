<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Scales | new
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="scale.label" text="Scale"/>
						</li>
						<li class="active">
							<a href="<s:url value="/admin/scales" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="scales.list.label" text="Scale list"/>
							</a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="create.scale.label" text="New scale" /></h1>
				</div>
				<s:url var="createUrl" value="/admin/scales" />
				<form:form id="form" class="form-horizontal" method="POST" action="${createUrl}" modelAttribute="scale" enctype="multipart/form-data">
					
					<fieldset>    				
    					<c:if test="${not empty message}">
							<div id="message" class="${message.type}">${message.message}</div>
						</c:if>
    				
    					<s:bind path="scale.name">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="name" cssClass="control-label">
								<s:message code="scale.name.label" text="Name"/>:
							</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused" required="required"/>
								<form:errors path="name" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="scale.ratio">
    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="ratio" cssClass="control-label">
								<s:message code="scale.ratio.label" text="Ratio"/>:
							</form:label>							
							<div class="controls">
								<form:input path="ratio" type="number" cssClass="input-xlarge focused"/>
								<form:errors path="ratio" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<s:bind path="scale.gauge">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<form:label path="gauge" cssClass="control-label">
								<s:message code="scale.gauge.label" text="Gauge"/>:
							</form:label>
							<div class="controls">
								<form:input path="gauge" type="number" cssClass="input-xlarge focused"/>
								<form:errors path="gauge" element="span" cssClass="help-inline"/>
							</div>
						</div>
						</s:bind>
						
						<div class="control-group">
							<form:label path="narrow" cssClass="control-label">
								<s:message code="scale.narrow.label" text="Is narrow"/>:
							</form:label>
							<div class="controls">
								<label class="checkbox">
									<form:checkbox path="narrow"/>
									<s:message code="scale.narrow.help.label" text="narrow gauge" /> 
								</label>
							</div>
						</div>
						
						<div class="form-actions">
							<form:button class="btn btn-primary" type="submit" name="_action_save">
								<i class="icon-check icon-white"></i>
								<s:message code="save.label" text="Save" />
							</form:button>
						
							<form:button class="btn" type="reset" name="_action_reset">
								<i class="icon-repeat icon-black"></i>
								<s:message code="reset.label" text="Reset" />
							</form:button>						
						</div>				
					</fieldset>
				</form:form>
			</div>
		</div>	
	</body>
</html>
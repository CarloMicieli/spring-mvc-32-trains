<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Brands | new
	</title>
	<body>
		<div class="row">
        	<div class="span4">
        		Crea un nuovo brand...
			</div>
			<div class="span8">
				<c:url value="/brands" var="brandsUrl" />
				<form:form id="form" class="form-horizontal" method="post" action="${brandsUrl}" modelAttribute="brand" >
					<fieldset>
    					<legend>New brand</legend>
    					<div class="alert alert-error">
    						<form:errors path="*"/>
    					</div>
    					
    					<div class="control-group">
							<form:label path="name" cssClass="control-label">Name:</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused"/>
							</div>
						</div>
    					<div class="control-group">
							<form:label path="description" cssClass="control-label">Description:</form:label>
							<div class="controls">
								<form:textarea path="description" cssClass="input-xlarge focused"/>
							</div>
						</div>
						<div class="form-actions">
							<form:button type="submit" class="btn btn-primary">Save</form:button>
							<form:button type="reset" class="btn">Reset</form:button>
						</div>				
					</fieldset>
				</form:form>
			</div>
		</div>	
	</body>
</html>
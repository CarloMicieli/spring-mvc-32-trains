<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Brands | new
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Brand</li>
						<li class="active">
							<a href="<s:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								Brands List
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span9">
				<c:url value="/admin/brands" var="brandsUrl" />
				<form:form id="form" class="form-horizontal" method="post" action="${brandsUrl}" modelAttribute="brand" >
					
					<form:hidden path="id"/>
					
					<fieldset>
    					<legend>Brand</legend>
    					
    					<div class="control-group">
							<form:label path="name" cssClass="control-label">Name:</form:label>
							<div class="controls">
								<form:input path="name" cssClass="input-xlarge focused"/>
								<form:errors path="name" element="span" cssClass="help-inline"/>
							</div>
						</div>
    					<div class="control-group">
							<form:label path="description" cssClass="control-label">Description:</form:label>
							<div class="controls">
								<form:textarea path="description" cssClass="input-xlarge focused"/>
							</div>
						</div>
						<div class="control-group">
							<form:label path="website" cssClass="control-label">Website:</form:label>
							<div class="controls">
								<form:input path="website" cssClass="input-xlarge focused"/>
							</div>
						</div>
    					<div class="control-group">
							<form:label path="emailAddress" cssClass="control-label">Email address:</form:label>
							<div class="controls">
								<div class="input-prepend">
									<span class="add-on">@</span><form:input type="email" path="emailAddress" cssClass="input-xlarge focused"/>
								</div>
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
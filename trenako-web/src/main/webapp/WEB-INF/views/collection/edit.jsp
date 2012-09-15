<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="collection.page.edit.title" arguments="${owner.slug}"/>
		</title>
		<meta name="you" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="page-header">
				<h1><s:message code="collection.edit.title.label"/></h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				<p>
					<s:url var="backUrl" value="/collections/{slug}">
						<s:param name="slug" value="${owner.slug}" />
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
				</p>
			</div>
			<div class="span10">
				<s:url var="createUrl" value="/collections" />
				<form:form class="form-horizontal" method="PUT" action="${createUrl}" modelAttribute="collection">
					<fieldset>
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
						
						<form:hidden path="id" />
						<input type="hidden" id="owner" name="owner" value="${owner.slug}" />
			
						<s:bind path="collection.notes">
	    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="notes" cssClass="control-label">
									<s:message code="collection.notes.label" />:
								</form:label>
								<div class="controls">
									<form:textarea path="notes" maxlength="250" rows="5" cssClass="input-xlarge focused"/>
									<form:errors path="notes" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>
						
						<div class="control-group">
							<div class="controls">
								<c:forEach var="vis" items="${visibilities}"> 
								<label class="radio">
	  								<input type="radio" name="visibility" id="${vis.key.key}" value="${vis.key.key}" ${vis.value}>
	  								${vis.key.label} - ${vis.key.description}
								</label>
								</c:forEach>
							</div>
						</div>
					
					</fieldset>
					
					<div class="form-actions">
						<form:button class="btn btn-primary" type="submit" name="_action_save">
							<i class="icon-check icon-white"></i>
							<s:message code="button.save.label"/>
						</form:button>
						<form:button class="btn" type="reset" name="_action_reset">
							<i class="icon-repeat icon-black"></i>
							<s:message code="button.reset.label"/>
						</form:button>
					</div>
				</form:form>
			</div>
		</div>
	</body>
</html>
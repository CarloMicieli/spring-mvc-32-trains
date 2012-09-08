<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="wishlist.page.new.title"/>
		</title>
		<meta name="you" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="page-header">
				<h1><s:message code="wishlist.new.title.label"/></h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span2">
				<p>
					<s:url var="backUrl" value="/wishlists/owner/{slug}">
						<s:param name="slug" value="${wishList.owner}" />
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
				</p>
			
				<h4><s:message code="wishlist.why.title.label"/></h4>
				<p>
					<s:message code="wishlist.why.text"/>
				</p>
			</div>
			<div class="span10">
				<s:url var="createUrl" value="/wishlists" />
				<form:form class="form-horizontal" method="POST" action="${createUrl}" modelAttribute="newForm">
					<fieldset>
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
						
						<input type="hidden" id="wishList.owner" name="wishList.owner" value="user" />
						
						 <s:bind path="newForm.wishList.name">
	    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="wishList.name" cssClass="control-label">
									<s:message code="wishlist.name.label" />:
								</form:label>
								<div class="controls">
									<form:input path="wishList.name" maxlength="25" cssClass="input-xlarge focused" required="required"/>
									<form:errors path="wishList.name" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>
					
						 <s:bind path="newForm.wishList.notes">
	    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="wishList.notes" cssClass="control-label">
									<s:message code="wishlist.notes.label" />:
								</form:label>
								<div class="controls">
									<form:textarea path="wishList.notes" maxlength="250" rows="5" cssClass="input-xlarge focused"/>
									<form:errors path="wishList.notes" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>
						
						<s:bind path="newForm.budget">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="budget" cssClass="control-label">
									<s:message code="wishlist.budget.label" />:
								</form:label>
								<div class="controls">
									<div class="input-prepend">
	  									<span class="add-on">$</span>
	  									<form:input path="budget" cssClass="span6"/> 
									</div>
									<span class="help-block"><s:message code="wishlist.budget.help.label" /></span>
								</div>
							</div>
						</s:bind>

						<div class="control-group">
							<div class="controls">
								<c:forEach var="vis" items="${newForm.visibilities}"> 
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
							<s:message code="button.save.label" text="Save" />
						</form:button>
						<form:button class="btn" type="reset" name="_action_reset">
							<i class="icon-repeat icon-black"></i>
							<s:message code="button.reset.label" text="Reset" />
						</form:button>
					</div>
				</form:form>
			</div>
		</div>
	</body>
</html>
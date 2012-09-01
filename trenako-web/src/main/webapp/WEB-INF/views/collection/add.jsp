<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="collection.page.add.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="page-header">
				<h1><s:message code="collection.new.title.label" /></h1>
			</div>

			<div class="row-fluid">
				<div class="span3">
					<p>
						<s:url var="backUrl" value="/rollingstocks/{slug}">
							<s:param name="slug" value="${rs.slug}" />
						</s:url>
						<a href="${backUrl}" class="btn btn-info" style="width: 110px">
							<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
						</a>
					</p>
					<a href="#" class="thumbnail">
						<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
							<s:param name="slug" value="${rs.slug}" />
						</s:url>
						<img src="${imgUrl}" alt="Not found">
					</a>
					<dl>
						<dt><s:message code="rollingStock.brand.label"/>:</dt>
						<dd>${rs.brand.label}</dd>
						<dt><s:message code="rollingStock.itemNumber.label"/>:</dt>
						<dd>${rs.itemNumber}</dd>
						<dt><s:message code="rollingStock.description.label"/>:</dt>
						<dd><tk:eval expression="${rs.description}"/></dd>
					</dl>
				</div>

				<div class="span9">
					<s:url var="saveUrl" value="/collections"/>
					<form:form class="form-horizontal" modelAttribute="itemForm" method="POST" action="${saveUrl}">
						<fieldset>
							<c:if test="${not empty message}">
							<div class="alert alert-${message.type}">
								<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
							</div>
							</c:if>
							<c:if test="${itemForm.alreadyInCollection}">
							<div class="alert alert-warning">
								<s:message code="collection.already.add.message"/>
							</div>
							</c:if>

							<input type="hidden" id="rs.slug" value="${rs.slug}" />

							<s:bind path="itemForm.item.addedAt">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="item.addedAt" cssClass="control-label">
									<s:message code="collection.addedAt.label"/>:
								</form:label>
								<div class="controls">
									<form:input path="item.addedAt" type="date" cssClass="input-xlarge focused"/>
									<form:errors path="item.addedAt" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="itemForm.item.price">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="item.price" cssClass="control-label">
									<s:message code="collection.price.label"/>:
								</form:label>
								<div class="controls">
									<form:input path="item.price" type="number" min="0" step="0.25" cssClass="input-xlarge focused" />
									<form:errors path="item.price" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="itemForm.item.condition">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="item.condition" cssClass="control-label">
									<s:message code="collection.condition.label"/>:
								</form:label>
								<div class="controls">
									<form:select path="item.condition">
										<s:message code="collection.conditions.list.label" var="conditionsLabel"/>
										<form:option value="-1" label="${conditionsLabel}"/>
										<form:options items="${itemForm.conditionsList}" />
									</form:select>
									<form:errors path="item.condition" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

							<s:bind path="itemForm.item.notes">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="item.notes" cssClass="control-label">
									<s:message code="collection.notes.label"/>:
								</form:label>
								<div class="controls">
									<form:textarea path="item.notes" rows="5" cssClass="input-xlarge focused" />
									<form:errors path="item.notes" element="span" cssClass="help-inline"/>
								</div>
							</div>
							</s:bind>

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
		</div>
	</body>
</html>
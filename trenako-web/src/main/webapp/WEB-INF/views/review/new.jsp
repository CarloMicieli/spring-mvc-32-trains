<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="review.page.new.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="page-header">
				<h1><s:message code="review.new.title.label" /></h1>
				<small><s:message code="review.new.subtitle.label" /></small>
			</div>
		
			<div class="row-fluid">
				<div class="span3">
					<p>
					<s:url var="backUrl" value="/rollingstocks/{slug}">
						<s:param name="slug" value="${reviewForm.rs.slug}" />
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
					</p>
					<a href="#" class="thumbnail">
						<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
							<s:param name="slug" value="${reviewForm.rs.slug}" />
						</s:url>
						<img src="${imgUrl}" alt="Not found">
			    	</a>
			    	<dl>
			    		<dt><s:message code="rollingStock.brand.label"/>:</dt>
			    		<dd>${reviewForm.rs.brand.label}</dd>
			    		<dt><s:message code="rollingStock.itemNumber.label"/>:</dt>
			    		<dd>${reviewForm.rs.itemNumber}</dd>
			    		<dt><s:message code="rollingStock.description.label"/>:</dt>
			    		<dd><tk:eval expression="${reviewForm.rs.description}"/></dd>			    		
			    	</dl>
				</div>
				
				<div class="span9">
					<s:url var="createUrl" value="/rollingstocks/{slug}/reviews">
						<s:param name="slug" value="${reviewForm.rs.slug}" />
					</s:url>
					<form:form id="form" class="form-horizontal" method="POST" action="${createUrl}" modelAttribute="reviewForm">
						<fieldset>
							<c:if test="${not empty message}">
							<div class="alert alert-${message.type}">
								<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
							</div>
							</c:if>
						</fieldset>
						
						<input type="hidden" id="review.author" name="review.author" value="${reviewForm.author.slug}"/>
						
						 <s:bind path="reviewForm.review.title">
	    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="review.title" cssClass="control-label">
									<s:message code="review.title.label" />:
								</form:label>
								<div class="controls">
									<form:input path="review.title" cssClass="input-xlarge focused" required="required"/>
									<form:errors path="review.title" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>
						
						<s:bind path="reviewForm.review.rating">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="review.rating" cssClass="control-label">
									<s:message code="review.rating.label" />:
								</form:label>
								<div class="controls">
								<form:select path="review.rating" required="required">
									<s:message code="review.ratings.list.label" var="ratingsLabel"/>
									<form:option value="-1" label="${ratingsLabel}"/>
									<form:options items="${reviewForm.ratings}" />
								</form:select>
								<form:errors path="review.rating" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>					
						
						 <s:bind path="reviewForm.review.content">
	    					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<form:label path="review.content" cssClass="control-label">
									<s:message code="review.content.label" />:
								</form:label>
								<div class="controls">
									<form:textarea rows="10" path="review.content" cssClass="input-xlarge focused" required="required"/>
									<form:errors path="review.content" element="span" cssClass="help-inline"/>
								</div>
							</div>
						</s:bind>					
						
						<div class="form-actions">
							<form:button class="btn btn-primary" type="submit" name="_action_save">
								<i class="icon-check icon-white"></i>
								<s:message code="button.create.label" />
							</form:button>
						
							<form:button class="btn" type="reset" name="_action_reset">
								<i class="icon-repeat icon-black"></i>
								<s:message code="button.reset.label" />
							</form:button>
						</div>						
					</form:form>
				</div>
			</div>
		</div>	
	</body>
</html>
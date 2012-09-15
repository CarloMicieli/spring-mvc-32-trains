<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="collection.name.label" arguments="${owner.displayName}"/>
		</title>
		<meta name="you" content="active"/>
	</head>
	<body>
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="nav.home.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/you" var="youUrl"/>
		    	<a href="${youUrl}"><s:message code="nav.you.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active">
		  		<s:message code="collection.name.label" arguments="${owner.displayName}"/>
		  	</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>
					<s:message code="collection.name.label" arguments="${owner.displayName}"/> 
					<small>(<tk:evalValue type="Visibility" expression="${collection.visibility}"/>)</small>
				</h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span3" style="padding-right: 15px">
				<p>
					<s:url var="backUrl" value="/you">
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
				</p>
				
				<div style="margin-top: 20px"></div>
				<h4><s:message code="collection.content.label"/></h4>
				<ul class="unstyled">
					<li>
						<s:message code="you.count.steam.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.steamLocomotives}</span>
					</li>
					<li>
						<s:message code="you.count.diesel.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.dieselLocomotives}</span>
					</li> 
					<li>
						<s:message code="you.count.electric.locomotives.label"/>
						<span class="badge badge-info pull-right">${collection.categories.electricLocomotives}</span>
					</li>
					<li>
						<s:message code="you.count.railcars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.railcars}</span>
					</li>
					<li>
						<s:message code="you.count.electric.multiple.unit.label"/>
						<span class="badge badge-info pull-right">${collection.categories.electricMultipleUnit}</span>
					</li>
					<li>
						<s:message code="you.count.passenger.cars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.passengerCars}</span>
					</li>
					<li>
						<s:message code="you.count.freight.cars.label"/>
						<span class="badge badge-info pull-right">${collection.categories.freightCars}</span>
					</li>
					<li>
						<s:message code="you.count.train.sets.label"/>
						<span class="badge badge-info pull-right">${collection.categories.trainSets}</span>
					</li>
					<li>
						<s:message code="you.count.starter.sets.label"/>
						<span class="badge badge-info pull-right">${collection.categories.starterSets}</span>
					</li>					
				</ul>
			</div>
			<div class="span9">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				<div class="row-fluid">
					<div class="span7">
						<dl class="dl-horizontal">
							<dt><s:message code="collection.visibility.label"/>:</dt>
							<dd><tk:evalValue type="Visibility" expression="${collection.visibility}"/></dd>
							<dt><s:message code="collection.numberOfItems.label"/>:</dt>
							<dd>${collection.categories.totalCount}</dd>
							<dt><s:message code="collection.notes.label"/>:</dt>
							<dd>${collection.notes}</dd>
						</dl>
					</div>
					<div class="span5">
						<p>
							<s:message code="collection.lastModified.label"/> <strong><tk:period since="${collection.lastModified}"/></strong>
						</p>
						<s:url var="deleteUrl" value="/collections" />
						<form:form cssClass="form-inline" method="DELETE" action="${deleteUrl}">
							<input type="hidden" id="id" name="id" value="${collection.id}"/>
							<s:url var="editUrl" value="/collections/{slug}/edit">
								<s:param name="slug" value="${collection.slug}"/>
							</s:url>
							<a href="${editUrl}" class="btn btn-warning">Edit collection</a>
							<button type="submit" class="btn btn-danger">Delete collection</button>
						</form:form>		
					</div>
				</div>
				
				<!-- Edit modal -->
				<div id="editItemModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 id="myModalLabel">Edit item</h3>
					</div>
					<s:url var="itemUrl" value="/collections/items"/>
					<form:form class="form-horizontal" method="PUT" action="${itemUrl}" modelAttribute="editForm">
						<input type="hidden" id="slug" name="slug" value="${collection.slug}"/>
						<form:hidden path="rsSlug"/>
						<form:hidden path="item.itemId"/>

						<div class="modal-body">
  							<div class="control-group">
  								<form:label path="item.addedAt" class="control-label">
									<s:message code="collection.addedAt.label"/>:&nbsp;&nbsp;
								</form:label>
  								<form:input path="item.addedAt"/>
  							</div>
							<div class="control-group">
								<form:label path="price" class="control-label">
									<s:message code="collection.price.label"/>:&nbsp;&nbsp;
								</form:label>
								<div class="controls">
									<div class="input-prepend">
	  									<span class="add-on">$</span>
	  									<input id="price" name="price" class="span6" type="text"> 
									</div>
								</div>
							</div>	  	
  							<div class="control-group">
  								<form:label path="item.condition" class="control-label">
  									<s:message code="collection.condition.label"/>:&nbsp;&nbsp;
  								</form:label>
  								<form:select path="item.condition">
  									<s:message code="collection.conditions.list.label" var="conditionsLabel"/>
									<form:option value="" label="${conditionsLabel}"/>
  									<form:options items="${editForm.conditionsList}" itemLabel="label" itemValue="key"/>
  								</form:select>
  							</div>															
							<div class="control-group">
  								<form:label path="item.notes" class="control-label">
  									<s:message code="collection.notes.label"/>:&nbsp;&nbsp;
								</form:label>
  								<form:textarea path="item.notes" rows="3" maxlength="150"/>
  							</div>
						</div>
						<div class="modal-footer">
						    <button type="submit" class="btn btn-primary">
								<i class="icon-check icon-white"></i>
								<s:message code="button.save.label"/>
							</button>
						    <button class="btn" data-dismiss="modal">
								<i class="icon-remove-circle icon-black"></i>
								<s:message code="button.close.label"/>
							</button>
						</div>
					</form:form>
				</div>
				
				<!-- Remove item modal -->
				<s:url var="removeItemUrl" value="/collections/items"/>
				<form:form class="form-horizontal" method="DELETE" action="${removeItemUrl}" modelAttribute="editForm">
					<input type="hidden" id="slug" name="slug" value="${collection.slug}"/>
					<form:hidden path="rsSlug"/>
					<form:hidden path="item.itemId"/>
					<div id="removeItemModal" class="modal hide fade" tabindex="-1" role="dialog">
				  		<div class="modal-header">
				    		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				    		<h3><s:message code="collection.remove.item.label"/></h3>
				  		</div>
				  		<div class="modal-body">
				    		<p><s:message code="collection.remove.item.confirm.msg"/></p>
				  		</div>
				  		<div class="modal-footer">
				  			<button type="submit" class="btn btn-danger">
				  				<i class="icon-warning-sign icon-white"></i>
				  				<s:message code="button.remove.item.label"/></button>
				    		<a href="#" class="btn" data-dismiss="modal">
				    			<i class="icon-remove-circle icon-black"></i>
								<s:message code="button.close.label"/>
							</a>
				  		</div>
					</div>
				</form:form>
				
				<div class="row-fluid">
					<div class="span12">
						<hr>
						<c:forEach var="item" items="${collection.items}">
							<s:url var="rsUrl" value="/rollingstocks/{slug}">
								<s:param name="slug" value="${item.rollingStock.slug}" />
							</s:url>
							<div class="row-fluid">
								<div class="span3">
									<s:url var="imgUrl" value="/images/th_rollingstock_{slug}">
										<s:param name="slug" value="${item.rollingStock.slug}" />
									</s:url>
									<a href="${rsUrl}" class="thumbnail"><img src="${imgUrl}" alt="Not found"></a>
								</div>
								<div class="span9">
									<a href="${rsUrl}"><strong>${item.rollingStock.label}</strong></a>
									
									<dl>
										<dt><s:message code="collection.addedAt.label"/>:</dt>
										<dd>${item.addedDay}</dd>
										<dt><s:message code="collection.price.label"/>:</dt>
										<dd>${item.price}</dd>
										<dt><s:message code="collection.condition.label"/>:</dt>
										<dd><tk:evalValue type="Condition" expression="${item.condition}"/></dd>
										<dt><s:message code="collection.notes.label"/>:</dt>
										<dd>${item.notes}</dd>
										
									</dl>	
									<p style="margin-top: 20px">
										<a href="#editItemDialog" class="btn btn-warning open-editItemDialog" role="button"
											data-toggle="modal" 
											data-rs-slug="${item.rollingStock.slug}"
											data-rs-label="${item.rollingStock.label}"
											data-added-at="<s:eval expression="item.addedAt"/>"
											data-condition="${item.condition}"
											data-price="${item.price.decimalValue}"
											data-item-id="${item.itemId}"><s:message code="collection.edit.item.label"/></a>&nbsp;&nbsp;OR&nbsp;&nbsp;
										<a href="#removeItemDialog" class="open-removeItemDialog" role="button" 
											data-toggle="modal" 
											data-rs-slug="${item.rollingStock.slug}"
											data-rs-label="${item.rollingStock.label}"
											data-item-id="${item.itemId}"><s:message code="collection.remove.item.label"/></a>
									</p>
								</div>
							</div>
							<hr/>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function() {
				$("#item\\.addedAt").datepicker({ dateFormat: "yy-mm-dd" });
			});
		
			$(document).on("click", ".open-removeItemDialog", function() {
				var rsSlug = $(this).data('rs-slug');
				var rsLabel = $(this).data('rs-label');
				var itemId = $(this).data('item-id');
				
				$("#editForm #rsSlug").val(rsSlug);
				$("#editForm #rsLabel").val(rsLabel);
				$("#editForm #item\\.itemId").val(itemId);
				$("#editForm #item\\.itemId").val(itemId);
				
				$('#removeItemModal').modal("show");
			});
			
			$(document).on("click", ".open-editItemDialog", function() {
				var rsSlug = $(this).data('rs-slug');
				var rsLabel = $(this).data('rs-label');
				var itemId = $(this).data('item-id');
				var condition = $(this).data('condition');
				var addedAt = $(this).data('added-at');
				var price = $(this).data('price');
				
				$("#editForm #rsSlug").val(rsSlug);
				$("#editForm #rsLabel").val(rsLabel);
				$("#editForm #item\\.itemId").val(itemId);
				$("#editForm #item\\.condition").val(condition);
				$("#editForm #item\\.addedAt").val(addedAt);
				$("#editForm #price").val(price);
				
				$('#editItemModal').modal("show");
			});
		</script>
	</body>
</html>
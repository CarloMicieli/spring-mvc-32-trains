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
		<ul class="breadcrumb">
			<li>
				<s:url value="/home" var="homeUrl"/>
		    	<a href="${homeUrl}"><s:message code="nav.home.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/you" var="youUrl"/>
		    	<a href="${youUrl}"><s:message code="nav.you.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/wishlists/owner/{owner}" var="wishlistsUrl">
					<s:param name="owner" value="${wishList.owner}"></s:param>
				</s:url>
		    	<a href="${wishlistsUrl}"><s:message code="nav.wish.lists.label"/></a> <span class="divider">/</span>
			</li>
		  	<li class="active">${wishList.name}</li>
		</ul>
	
		<div class="row-fluid">
			<div class="page-header">
				<h1>${wishList.name} <small>(<tk:evalValue type="Visibility" expression="${wishList.visibility}"/>)</small></h1>
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
			</div>
			<div class="span10">
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				<div class="row-fluid">
					<div class="span8">
						<dl class="dl-horizontal">
							<dt><s:message code="wishlist.name.label"/>:</dt>
							<dd>${wishList.name}</dd>
							<dt><s:message code="wishlist.numberOfItems.label"/>:</dt>
							<dd>${wishList.numberOfItems}</dd>
							<dt><s:message code="wishlist.visibility.label"/>:</dt>
							<dd><tk:evalValue type="Visibility" expression="${wishList.visibility}"/></dd>
							<dt><s:message code="wishlist.notes.label"/>:</dt>
							<dd>${wishList.notes}</dd>
							<dt><s:message code="wishlist.budget.label"/>:</dt>
							<dd>${wishList.budget}</dd>
						</dl>
					</div>
					<div class="span4">
						<p>
							<s:message code="wishlist.lastModified.label"/> <strong><tk:period since="${wishList.lastModified}"/></strong>
						</p>
						<s:url var="deleteUrl" value="/wishlists" />
						<form:form cssClass="form-inline" method="DELETE" action="${deleteUrl}">
							<input type="hidden" id="id" name="id" value="${wishList.id}"/>
							<s:url var="editUrl" value="/wishlists/{slug}/edit">
								<s:param name="slug" value="${wishList.slug}"/>
							</s:url>
							<a href="${editUrl}" class="btn btn-warning">Edit list</a>
							<button type="submit" class="btn btn-danger">Delete list</button>
						</form:form>						
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<hr>
						
						<c:if test="${wishList.items.size() == 0}">
							<div class="well">
								<h4><s:message code="wishlist.empty.list.message"/></h4> 
								<s:url var="rollingStocksUrl" value="/rs"/>
								<a href="${rollingStocksUrl}" class="btn">Add rolling stocks</a>
							</div>
						</c:if>
						
						<!-- Remove item modal -->
						<s:url var="removeItemUrl" value="/wishlists/items"/>
						<form:form class="form-horizontal" method="DELETE" action="${removeItemUrl}" modelAttribute="editForm">
							<input type="hidden" id="slug" name="slug" value="${wishList.slug}"/>
							<form:hidden path="rsSlug"/>
							<form:hidden path="rsLabel"/>
							<form:hidden path="item.itemId"/>
							<div id="removeItemModal" class="modal hide fade" tabindex="-1" role="dialog">
						  		<div class="modal-header">
						    		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						    		<h3>Remove item</h3>
						  		</div>
						  		<div class="modal-body">
						    		<p>Are you sure to remove this item from your wish list?</p>
						  		</div>
						  		<div class="modal-footer">
						  			<button type="submit" class="btn btn-primary">Remove this item</button>
						    		<a href="#" class="btn" data-dismiss="modal">Close</a>
						  		</div>
							</div>
						</form:form>
						
						<!-- Edit modal -->
						<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h3 id="myModalLabel">Edit item</h3>
							</div>
							<s:url var="itemUrl" value="/wishlists/items"/>
							<form:form class="form-horizontal" method="PUT" action="${itemUrl}" modelAttribute="editForm">
								<input type="hidden" id="slug" name="slug" value="${wishList.slug}"/>
								<form:hidden path="rsSlug"/>
								<form:hidden path="rsLabel"/>
								<form:hidden path="previousPrice"/>
								<form:hidden path="item.itemId"/>
		
								<div class="modal-body">
									<div class="control-group">
		  								<form:label path="item.notes" class="control-label">Notes:&nbsp;&nbsp;</form:label>
		  								<form:textarea path="item.notes" rows="3" maxlength="150"/>
		  							</div>
		  							<div class="control-group">
		  								<form:label path="item.priority" class="control-label">Priority:&nbsp;&nbsp;</form:label>
		  								<form:select path="item.priority">
		  									<form:options items="${editForm.priorities}" itemLabel="label" itemValue="key"/>
		  								</form:select>  				
		  							</div>
									<div class="control-group">
										<form:label path="price" class="control-label">Price:&nbsp;&nbsp;</form:label>
										<div class="controls">
											<div class="input-prepend">
			  									<span class="add-on">$</span>
			  									<input id="price" name="price" class="span6" type="text"> 
											</div>
										</div>
									</div>				
								</div>
								<div class="modal-footer">
								    <button type="submit" class="btn btn-primary">Save changes</button>
								    <button class="btn" data-dismiss="modal">Close</button>
								</div>
							</form:form>
						</div>
						
						<c:forEach var="item" items="${wishList.items}">
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
									<p>
										<small><em>Added</em> <strong><tk:period since="${item.addedAt}"/></strong></small>
									</p>
									
									<dl class="dl-horizontal">
										<dt>Priority:</dt>
										<dd><tk:evalValue type="Priority" expression="${item.priority}"/></dd>
										<dt>Price [<a class="priceHelp" href="#" rel="tooltip" data-placement="right">?</a>]:</dt>
										<dd>${item.price}</dd>
										<dt>Notes:</dt>
										<dd>${item.notes}</dd>
									</dl>	
									
									<p style="margin-top: 20px">
										<a href="#" class="btn btn-warning">Move to collection</a> 
										<a href="#editItemDialog" class="open-editItemDialog" role="button" 
											data-toggle="modal" 
											data-rs-slug="${item.rollingStock.slug}"
											data-rs-label="${item.rollingStock.label}"
											data-item-id="${item.itemId}" 
											data-priority="${item.priority}" 
											data-price="${item.price.decimalValue}" 
											data-notes="${item.notes}">Edit this item</a> | 
										<a href="#">Move to another list</a> |
										<a href="#removeItemDialog" class="open-removeItemDialog" role="button" 
											data-toggle="modal" 
											data-rs-slug="${item.rollingStock.slug}"
											data-rs-label="${item.rollingStock.label}"
											data-item-id="${item.itemId}">Remove this item</a>
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
				$(".priceHelp").tooltip({title: 'This is the extimated price.'});
			});

			$(document).on("click", ".open-removeItemDialog", function() {
				var rsSlug = $(this).data('rs-slug');
				var rsLabel = $(this).data('rs-label');
				var itemId = $(this).data('item-id');
				
				$("#editForm #rsSlug").val(rsSlug);
				$("#editForm #rsLabel").val(rsLabel);
				$("#editForm #item\\.itemId").val(itemId);
				
				$('#removeItemModal').modal("show");
			});
			
			$(document).on("click", ".open-editItemDialog", function() {
				var rsSlug = $(this).data('rs-slug');
				var rsLabel = $(this).data('rs-label');
				var itemId = $(this).data('item-id');
				var notes = $(this).data('notes');
				var priority = $(this).data('priority');
				var price = $(this).data('price');
				
				$("#editForm #rsSlug").val(rsSlug);
				$("#editForm #rsLabel").val(rsLabel);
				$("#editForm #price").val(price);
				$("#editForm #previousPrice").val(price);
				$("#editForm #item\\.itemId").val(itemId);
				$("#editForm #item\\.notes").val(notes);
				$("#editForm #item\\.priority").val(priority);
				$('#myModal').modal("show");
			});
		</script>
	</body>
</html>
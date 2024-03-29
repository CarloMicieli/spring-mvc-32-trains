<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="rollingStock.page.show.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<ul class="breadcrumb">
			<li>
				<s:url value="/rs" var="rsUrl"/>
		    	<a href="${rsUrl}"><s:message code="browse.breadcrumb.rollingStocks.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/browse/brands/{slug}" var="brandsUrl">
					<s:param name="slug" value="${result.rs.brand.slug}"></s:param>
				</s:url>
		    	<a href="${brandsUrl}">${result.rs.brand.label}</a> <span class="divider">/</span>
			</li>		
		  	<li class="active">${result.rs.itemNumber}</li>
		</ul>
		<div class="row-fluid">
        	<div class="page-header">
				<h1>
					${result.rs.brand.label} ${result.rs.itemNumber}
					<small>(<tk:evalValue type="Category" expression="${result.rs.category}"/>)</small>
				</h1>
			</div>
		</div>
	    <div class="row-fluid">
	    	<div class="span2">
	    		<sec:authorize access="isAuthenticated()">
				<p>
	    			<s:url var="addCollUrl" value="/collections/add/{slug}">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>				
					<a class="btn btn-info" href="${addCollUrl}" style="width: 130px"><i class="icon-tags icon-white">
						</i> <s:message code="button.add.collection.label"/>
					</a>
				</p>
				
				<p>
				-- or --
				</p>
				
				<div class="btn-group">
					<a class="btn btn-info" href="#" style="width: 110px"><i class="icon-gift icon-white"></i> <s:message code="button.add.wish.list.label"/></a>
					<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
					<c:forEach var="wl" items="${result.wishLists}">
						<li><a class="addWishList" href="${wl.slug}"><i class="icon-list-alt"></i> ${wl.name}</a></li>
					</c:forEach>
					</ul>
					
					<s:url var="wishListsUrl" value="/wishlists/items"/>
					<form method="POST" id="wish-list-form" action="${wishListsUrl}">
						<input type="hidden" id="rsSlug" name="rsSlug" value="${result.rs.slug}"/>
						<input type="hidden" id="rsLabel" name="rsLabel" value="${result.rs.label}"/>
					</form>
					<script type="text/javascript">
						$(document).ready(function(){
							$('.addWishList').bind('click', function() {
								var name = $(this).attr("href");
								var input = $("<input>").attr("type", "hidden").attr("name", "slug").val(name);
								$('#wish-list-form').append($(input))
								$('#wish-list-form').submit();
								event.preventDefault();
							});
						});
					</script>
				</div>
				<hr>
	    		<p>
	    			<s:url var="postReviewUrl" value="/rollingstocks/{slug}/reviews/new">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
	    			<a class="btn btn-info" href="${postReviewUrl}" style="width: 130px">
						<i class="icon-comment icon-white"></i> <s:message code="button.write.review.label"/>
					</a>
				</p>
				</sec:authorize>
				<p>
					<s:message code="rollingStock.review.info.label" arguments="${result.reviews.numberOfReviews}, ${result.reviews.rating}"/>
					<s:url var="reviewsUrl" value="/rollingstocks/{slug}/reviews">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
					<br/>
	    			<a href="${reviewsUrl}">
						<i class="icon-comment icon-black"></i> <s:message code="button.all.reviews.label"/>
					</a>
				</p>
			</div>
	    	<div class="span6">
	    		<div class="row-fluid">
	    			<sec:authorize access="isAuthenticated()">
	    			<s:url var="deleteUrl" value="/rollingstocks"/>
	    			<form:form method="DELETE" action="${deleteUrl}">
	    				<input type="hidden" id="id" name="id" value="${result.rs.id}"/>
		    			<div class="btn-toolbar pull-right">
			    			<div class="btn-group">
								<s:url var="editUrl" value="/rollingstocks/{slug}/edit">
									<s:param name="slug" value="${result.rs.slug}"></s:param>
								</s:url>
							  	<a class="btn" href="#">
							  		<i class="icon-folder-open icon-black"></i> <s:message code="rollingStock.change.image.button.label"/>
							  	</a>
							  	<a class="btn" href="${editUrl}">
							  		<i class="icon-edit icon-black"></i> <s:message code="button.edit.label"/>
							  	</a>
							</div>
							<div class="btn-group">
							  	<button class="btn btn-danger">
							  		<i class="icon-remove-sign icon-white"></i> <s:message code="rollingStock.delete.button.label"/>
							  	</button>
							</div>
						</div>
					</form:form>
					</sec:authorize>
					<s:url value="/images/rollingstock_{slug}" var="imgUrl">
						<s:param name="slug" value="${result.rs.slug}" />
					</s:url>
					<img src="${imgUrl}" alt="Not found">
				</div>
				<div class="row-fluid">
					<c:forEach var="tag" items="${result.rs.tags}">
						<s:url var="tagUrl" value="/tag/{tagName}">
							<s:param name="tagName" value="${tag}"></s:param>
						</s:url>
						<a class="label label-info" href="#" title="Tag: ${tag}">${tag}</a>
					</c:forEach>
		   		</div>
		   		
		   		<hr/>
		   		
		   		<div class="row-fluid">
	        		<sec:authorize access="isAnonymous()">
	        			<div class="well">
	        				<p>
			           			<s:url value="/auth/login" var="loginUrl"/>
	    	       				<s:url value="/auth/signup" var="signupUrl"/>
		    		       		<a href="${loginUrl}"><s:message code="auth.signin.link.label"/></a> / 
		           				<a href="${signupUrl}"><s:message code="auth.signup.link.label"/></a> <s:message code="auth.not.authorize.comment.label"/>
	           				</p>
	           			</div>
	           		</sec:authorize>
	           	
	           		<sec:authorize access="isAuthenticated()">
	           		<s:url value="/rollingstocks/{slug}/comments" var="commentsUrl">
	           			<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
	           		<form:form modelAttribute="commentForm" method="POST" action="${commentsUrl}" class="well form-inline">
	           			<form:hidden path="rsSlug"/>
	           			<form:hidden path="rsLabel"/>
	           			<form:hidden path="comment.author"/>
	           			
	           			<div class="control-group">
	           			<s:message var="placeholder" code="comment.content.placeholder"/>
	           			<form:textarea path="comment.content" 
		           			class="span12" 
		           			rows="3" 
		           			placeholder="${placeholder}" required="required"/>
		           		</div>
		           		<div class="control-group">
	           				<form:button type="submit" class="btn btn-primary">
	           					<i class="icon-envelope icon-white"></i> <s:message code="button.send.label"/>
		           			</form:button>
	           			</div>
	           		</form:form>
	           		<hr/>
	           		</sec:authorize>
	        	</div>
		   		<div class="row-fluid">
	           		<c:forEach var="cmm" items="${result.comments}">
           			<div class="row-fluid">
           				<div class="span2 offset1">
           					<tk:avatar user="${cmm.author}" size="48" showName="true"/>
           				</div>
           				<div class="span9">
           					<p>
           						${cmm.content}
           						<br/>
           						<strong><tk:period since="${cmm.postedAt}"/></strong>
           					</p>
           				</div>
           			</div>
           			<hr>
    	       		</c:forEach>
        	  	</div>
            </div>
            <div class="span4">
            	<div class="tabbable">
                	<ul class="nav nav-tabs">
                    	<li class="active"><a href="#tab1" data-toggle="tab"><s:message code="rollingStock.overview.label" /></a></li>
                        <li><a href="#tab2" data-toggle="tab"><s:message code="rollingStock.details.label" /></a></li>
						<li><a href="#tab3" data-toggle="tab"><s:message code="rollingStock.options.label" /></a></li>
               		</ul>
                  	<div class="tab-content">
                    	<div class="tab-pane active" id="tab1">
							<dl class="dl-horizontal">
								<dt><s:message code="rollingStock.description.label"/>:</dt>
								<dd><tk:eval expression="${result.rs.description}"/></dd>
								<dt><s:message code="rollingStock.scale.label"/>:</dt>
								<dd>${result.rs.scale.label}</dd>
								<dt><s:message code="rollingStock.era.label"/>:</dt>
								<dd><tk:evalValue type="Era" expression="${result.rs.era}"/></dd>
								<dt><s:message code="rollingStock.railway.label"/>:</dt>
								<dd>${result.rs.railway.label}</dd>
								<dt><s:message code="rollingStock.powerMethod.label"/>:</dt>
								<dd><tk:evalValue type="PowerMethod" expression="${result.rs.powerMethod}"/></dd>
							</dl>
					    </div>
                  		<div class="tab-pane" id="tab2">
                        	<p><tk:eval expression="${result.rs.details}"/></p>
               			</div>
                        <div class="tab-pane" id="tab3">
                        	<dl class="dl-horizontal">
                        		<dt><s:message code="rollingStock.totalLength.label" />:</dt>
								<dd>${result.rs.totalLength} mm</dd>
								<dt><s:message code="rollingStock.deliveryDate.label" />:</dt>
								<dd>${result.rs.deliveryDate}</dd>
								<dt><s:message code="rollingStock.upcCode.label" />:</dt>
								<dd>${result.rs.upcCode}</dd>
							</dl>
						</div>								
					</div>
				</div>
           	</div>
		</div>
	</body>
</html>
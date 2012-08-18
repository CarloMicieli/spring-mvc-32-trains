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
				<h1>${result.rs.brand.label} ${result.rs.itemNumber}</h1>
				<small>${result.rs.category}</small>
			</div>
		</div>
	    <div class="row-fluid">
	    	<div class="span2">
	    		<sec:authorize access="isAuthenticated()">
	    		<p>
					<s:url var="editUrl" value="/rollingstocks/{slug}/edit">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
					<a class="btn btn-primary" style="width: 110px" href="${editUrl}">
						<i class="icon-edit icon-white"></i> <s:message code="button.edit.label"/>
					</a>
				</p>
				
	    		<p>
	    			<s:url var="postReviewUrl" value="/rollingstocks/{slug}/reviews/new">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
	    			<a class="btn btn-success" href="${postReviewUrl}" style="width: 110px">
						<i class="icon-comment icon-white"></i> <s:message code="button.write.review.label"/>
					</a>
				</p>
				<p>
	    			<s:url var="addCollUrl" value="/collections/add/{slug}">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>				
					<a class="btn btn-danger" href="${addCollUrl}" style="width: 110px"><i class="icon-tags icon-white">
						</i> <s:message code="button.add.collection.label"/>
					</a>
				</p>
				<div class="btn-group">
					<a class="btn btn-primary" href="#" style="width: 110px"><i class="icon-gift icon-white"></i> Add wish list</a>
					<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="icon-list-alt"></i> New stuff</a></li>
						<li><a href="#"><i class="icon-list-alt"></i> Old stuff</a></li>
						<li><a href="#"><i class="icon-list-alt"></i> New</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="i"></i> New wishlist</a></li>
					</ul>
				</div>
				<hr>
				</sec:authorize>
				<p>
					<s:message code="rollingStock.review.info.label" arguments="${result.reviews.numberOfReviews}, ${result.reviews.rating}"/>
					<s:url var="reviewsUrl" value="/rollingstocks/{slug}/reviews">
						<s:param name="slug" value="${result.rs.slug}"></s:param>
					</s:url>
	    			<a class="btn btn-success" href="${reviewsUrl}" style="width: 110px">
						<i class="icon-comment icon-white"></i> <s:message code="button.all.reviews.label"/>
					</a>
				</p>
			</div>
	    	<div class="span6">
				<s:url value="/images/rollingstock_{slug}" var="imgUrl">
					<s:param name="slug" value="${result.rs.slug}" />
				</s:url>
				<img src="${imgUrl}" alt="Not found">
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
								<dd><s:message code="era.${result.rs.era}.name" /></dd>
								<dt><s:message code="rollingStock.railway.label"/>:</dt>
								<dd>${result.rs.railway.label}</dd>
								<dt><s:message code="rollingStock.powerMethod.label"/>:</dt>
								<dd>${result.rs.powerMethod}</dd>
							</dl>
					    </div>
                  		<div class="tab-pane" id="tab2">
                        	<p><tk:eval expression="${result.rs.details}"/></p>
               			</div>
                        <div class="tab-pane" id="tab3">
                        	<dl class="dl-horizontal">
								<dt><s:message code="rollingStock.upcCode.label" />:</dt>
								<dd>${result.rs.upcCode}</dd>
								<dt><s:message code="rollingStock.deliveryDate.label" />:</dt>
								<dd>${result.rs.deliveryDate}</dd>
							</dl>
						</div>								
					</div>
				</div>
           	</div>
		</div>
		<div class="row-fluid">
        	<div class="span2">
        	</div>
           	<div class="span6">
				<p>
					<span class="label label-info">Tags</span>
					<c:forEach var="tag" items="${result.rs.tags}">
						<s:url var="tagUrl" value="/tag/{tagName}">
							<s:param name="tagName" value="${tag}"></s:param>
						</s:url>
						[<a href="${tagUrl}" title="Tag: ${tag}">${tag}</a>]
					</c:forEach>
				</p>
        	</div>
         	<div class="span4"></div>
   		</div>
   		<div class="row-fluid">
        	<div class="span2"></div>
           	<div class="span6">
           		<sec:authorize access="isAnonymous()">
           			<s:url value="/auth/login" var="loginUrl"/>
           			<s:url value="/auth/signup" var="signupUrl"/>
	           		<a href="${loginUrl}"><s:message code="auth.signin.link.label"/></a> / 
	           		<a href="${signupUrl}"><s:message code="auth.signup.link.label"/></a> <s:message code="auth.not.authorize.comment.label"/>
           		</sec:authorize>
           	
           		<sec:authorize access="isAuthenticated()">
           		<s:url value="/rollingstocks/{slug}/comments" var="commentsUrl">
           			<s:param name="slug" value="${result.rs.slug}"></s:param>
				</s:url>
           		<form:form modelAttribute="newComment" method="POST" action="${commentsUrl}" class="well form-inline">
           			<form:hidden path="rollingStock.slug"/>
           			<form:hidden path="rollingStock.label"/>
           			<form:hidden path="author.slug"/>
           			<form:hidden path="author.label"/>
           			
           			<form:textarea path="content" class="input-small" style="width:400px" rows="3" placeholder="New comment"/>
           			<form:button type="submit" class="btn"><i class="icon-envelope icon-black"></i> <s:message code="button.send.label"/></form:button>
           		</form:form>
           		<hr/>
           		</sec:authorize>
        	</div>
         	<div class="span4"></div>
   		</div>
   		<div class="row-fluid">
        	<div class="span2"></div>
           	<div class="span6">
           		<c:forEach var="cmm" items="${result.comments}">
           			<div class="row-fluid">
           				<div class="span2">
           					<img src="http://placehold.it/96x96">
           				</div>
           				<div class="span10">
           					<p>
           						${cmm.content}
           						<br>
           						<strong><a href="">${cmm.author.label}</a></strong> - ${cmm.postedAt}
           					</p>
           				</div>
           			</div>
           			<hr>
           		</c:forEach>
          	</div>
         	<div class="span4"></div>
   		</div>
	</body>
</html>
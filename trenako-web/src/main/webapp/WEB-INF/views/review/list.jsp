<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="review.page.list.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="page-header">
				<h1>
					<s:message code="review.list.title.label" arguments="${rollingStock.label}"/>
					<small><s:message code="review.list.subtitle.label" /></small>
				</h1>
			</div>
		
			<div class="row-fluid">
				<div class="span3">
					<p>
					<s:url var="backUrl" value="/rollingstocks/{slug}">
						<s:param name="slug" value="${rollingStock.slug}" />
					</s:url>
					<a href="${backUrl}" class="btn btn-info" style="width: 110px">
						<i class="icon-arrow-left icon-white"></i> <s:message code="button.go.back.label"/>
					</a>
					</p>
					<a href="#" class="thumbnail">
						<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
							<s:param name="slug" value="${rollingStock.slug}" />
						</s:url>
						<img src="${imgUrl}" alt="Not found">
			    	</a>
			    	<dl>
			    		<dt><s:message code="rollingStock.brand.label"/>:</dt>
			    		<dd>${rollingStock.brand.label}</dd>
			    		<dt><s:message code="rollingStock.itemNumber.label"/>:</dt>
			    		<dd>${rollingStock.itemNumber}</dd>
			    		<dt><s:message code="rollingStock.description.label"/>:</dt>
			    		<dd><tk:eval expression="${rollingStock.description}"/></dd>			    		
			    	</dl>
				</div>
				
				<div class="span9">
					<div class="well">
						<s:message code="review.info.label" arguments="${reviews.numberOfReviews}, ${reviews.rating}"/>
					</div>
				
					<c:forEach var="rev" items="${reviews.items}">
           			<div class="row-fluid">
           				<div class="span2">
           					<tk:avatar user="${rev.author}" size="64" showName="true"/>
           				</div>
           				<div class="span10">
           					<h4>
           						${rev.title}
           						<small>(<s:message code="review.rating.out.of.label" arguments="${rev.rating}"/>)</small>
           					</h4>
           					<p>
           						${rev.content}
           						<br>
           						<strong><a href="">${rev.author}</a></strong> - <tk:period since="${rev.postedAt}"/> 
           					</p>
           				</div>
           			</div>
           			<hr>						
					</c:forEach>
				</div>
			</div>
		</div>	
	</body>
</html>
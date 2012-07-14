<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
	<head>
		<title>Trenako.com</title>
		<meta name="browse" content="active"/>
	</head>
	
	<body>
		<div class="row">
        	<div class="span4">
        		<h1>Rolling Stocks</h1>
        	</div>
        	<div class="span8">
        		<p class="pull-right">
				    <a href="<s:url value="/rollingstocks" />" class="btn">
						<i class="icon-pencil"></i>
						<s:message code="button.edit.label" />
					</a>
        		</p>
        	</div>
        </div>
        
        <div class="row">
        	<c:forEach var="rs" items="${results.items}">
				<div class="row" style="border-bottom: 1pt solid silver;">
					<div class="span2">
						<strong>${rs?.brand?.name}</strong><br/>
						${rs?.itemNumber}
					</div>
					<div class="span3">
						<a href="#" class="thumbnail">
					      <img src="http://placehold.it/150x50" alt="">
					    </a>
					</div>
					<div class="span5">
						<h4>${rs?.description}</h4>
						<div class="row">
							<div class="span1">Era: <br/><strong>${rs?.era?.name}</strong></div>
							<div class="span1">Scale: <br /><strong>${rs?.scale?.name}</strong></div>
							<div class="span1">Railway: <br /><strong>${rs?.railway?.name}</strong></div>
							<div class="span2">Power Method: <br /><strong>${rs?.powerMethod}</strong></div>
						</div>
					</div>
					<div class="span2">
						<s:url value="/rollingstocks/{slug}" var="showUrl">
							<s:param name="slug" value="${rs.slug}" />
						</s:url>
						<a href="${showUrl}">
							<s:message code="button.details.label" text="Details" />
						</a>
					</div>
				</div>
        	</c:forEach>
        </div>
        
      	<div class="row">
      		<ul class="pager">
  				<li class="previous">
					<s:url value="/url/path" var="prevUrl">
						<s:param name="max" value="${result.max}" />
						<s:param name="sort" value="${result.sort}" />
						<s:param name="order" value="${result.order}" />
					</s:url>
					<a href="${prevUrl}" class="control-group${(not result.hasPreviousPage) ? ' hidden' : ''}">
						&larr; <s:message code="button.previous.label" text="Previous" />
					</a>
  				</li>
  				<li class="next">
					<s:url value="/url/path" var="nextUrl">
						<s:param name="since" value="${result.since}" />
						<s:param name="sort" value="${result.sort}" />
						<s:param name="order" value="${result.order}" />
					</s:url>
					<a href="${nextUrl}" class="control-group${(not result.hasNextPage) ? ' hidden' : ''}">
						<s:message code="button.next.label" text="Next" /> &rarr;
					</a>
  				</li>
			</ul>
        </div>
	</body>
</html>
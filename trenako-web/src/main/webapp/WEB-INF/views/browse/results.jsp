<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
	<title></title>
	<meta name="rs" content="active"/>
</head>

<body>
	<div class="row-fluid">
		<div class="span2">
			<tk:searchBar results="${results}">
				<tk:brands label="name" />
				<tk:railways label="name" />
				<tk:scales/>
				<tk:eras/>
				<tk:powerMethods/>
				<tk:categories/>
			</tk:searchBar>
		</div>
		<div class="span10">
			<tk:breadcrumb results="${results}"/>

			<div class="row-fluid">
				<tk:pagerOptions results="${results}"/>
			</div>

        	<c:forEach var="rs" items="${results.items}">
				<div class="row-fluid" style="border-bottom: 1pt solid silver;">
					<div class="span1">
						<strong>${rs.brand.name}</strong><br/>
						${rs.itemNumber}
					</div>
					<div class="span3">
						<a href="#" class="thumbnail">
					      <img src="http://placehold.it/150x75" alt="">
					    </a>
					</div>
					<div class="span6">
						<h3>${rs.description}</h3>
						<div class="row-fluid">
							<div class="span3">Era: <br/><strong>${rs.era}</strong></div>
							<div class="span3">Scale: <br /><strong>${rs.scale.name}</strong></div>
							<div class="span3">Railway: <br /><strong>${rs.railway.name}</strong></div>
							<div class="span3">Power Method: <br /><strong>${rs.powerMethod}</strong></div>
						</div>
					</div>
					<div class="span2">
						<s:url value="/rollingstocks/{slug}" var="showUrl">
							<s:param name="slug" value="${rs.slug}" />
						</s:url>
						<a href="${showUrl}">
							<s:message code="button.details.label" text="Show details" />
						</a>
					</div>
				</div>
        	</c:forEach>
        	
        	<div class="row-fluid">
        		<p><tk:pager results="${results}"/></p>
        	</div>
        	
		</div>
	</div>
</body>
</html>

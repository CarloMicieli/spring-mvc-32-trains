<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
	<title>
		Brands | ${brand.name}
	</title>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Brand</li>
						<li class="active">
							<a href="<spring:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								Brands List
							</a>
						</li>
						<li>
							<spring:url var="editUrl" value="/admin/brands/{id}/edit">
				            	<spring:param name="id" value="${brand.id}" />
							</spring:url>
							<a href="${editUrl}" class="create">
								<i class="icon-plus"></i>
								Edit this brand
							</a>
						</li>
						<li>
							<a href="<spring:url value="/admin/brands/new" />" class="create">
								<i class="icon-plus"></i>
								Create new brand
							</a>
						</li>						
					</ul>
				</div>
			</div>
    		<div class="span8">
    			<h3>${brand.name}</h3>
    			<dl class="dl-horizontal">
    				<dt><spring:message code="brand.description" text="Description" />:</dt>
    				<dd>${brand.description}</dd>
    				<dt><spring:message code="brand.emailAddress" text="Email Address" />:</dt>
    				<dd>${brand.emailAddress}</dd>
    				<dt><spring:message code="brand.website" text="Website" />:</dt>
    				<dd>${brand.website}</dd>    				
    			</dl>
    		</div>
    	</div>
	</body>
</html>
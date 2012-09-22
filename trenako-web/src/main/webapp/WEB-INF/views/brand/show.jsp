<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="brands.show.title.label" arguments="${brand.name}" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="brands.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/brands" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="brands.list.label" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/brands/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="brands.create.brand.label" />
							</a>
						</li>						
					</ul>
				</div>
			</div>
    		<div class="span9">
				<div class="page-header">
					<h1>
						${brand.name}
						<small>(${brand.companyName})</small>
					</h1>
					<s:message code="brand.lastModified.label" /> <strong><tk:period since="${brand.lastModified}"/></strong>
				</div>
				
				<html:alert msg="${message}"/>
								
				<div class="row-fluid">
					<div class="span12">
						<dl class="dl-horizontal">
		    				<dt><s:message code="brand.description.label" />:</dt>
		    				<dd><tk:eval expression="${brand.description}"/></dd>
		    				<dt><s:message code="brand.industrial.label" />:</dt>
		    				<dd>${brand.industrial}</dd>		
		    				<dt><s:message code="brand.emailAddress.label" />:</dt>
		    				<dd>${brand.emailAddress}</dd>
		    				<dt><s:message code="brand.website.label" />:</dt>
		    				<dd>${brand.website}</dd>
		    			</dl>
	    			</div>
				</div>
				
				<div class="row-fluid">
	    			<s:url value="/admin/brands/{id}" var="deleteUrl">
						<s:param name="id" value="${brand.id}" />
					</s:url>
					<form:form action="${deleteUrl}" method="delete" modelAttribute="brand" >
						<form:hidden path="id"/>
						<div class="form-actions">
							<s:url var="editUrl" value="/admin/brands/{slug}/edit">
					           	<s:param name="slug" value="${brand.slug}" />
							</s:url>
							<a href="${editUrl}" class="btn">
								<i class="icon-pencil"></i>
								<s:message code="button.edit.label" />
							</a>
	
							<button class="btn btn-danger" type="submit" name="_action_delete">
								<i class="icon-trash icon-white"></i>
								<s:message code="button.delete.label" />
							</button>
						</div>
					</form:form>
				</div>
				
				<hr/>
				
				<s:url var="uploadUrl" value="/admin/brands/upload"/>
				<s:url value="/images/brand_{slug}" var="imgUrl">
					<s:param name="slug" value="${brand.slug}" />
				</s:url>
				<s:url value="/images/th_brand_{slug}" var="thumbUrl">
					<s:param name="slug" value="${brand.slug}" />
				</s:url>
				<html:upload uploadUrl="${uploadUrl}" name="${brand.name}" thumbUrl="${thumbUrl}" imgUrl="${imgUrl}"/>
			</div>
    	</div>
    	<script type="text/javascript">
			$(document).ready(function() {
				$("img").error(function(){
					$(this).hide();
				});
				
				$(document).on("click", ".open-uploadModal", function() {
					$('#uploadModal').modal("show");
				});
				
				$("#deleteLink").bind('click', function() {
					$("#deleteForm").submit();
					$(this).preventDefault();
				});		
			});
		</script>
	</body>
</html>
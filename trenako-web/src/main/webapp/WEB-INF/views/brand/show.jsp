<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

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
				
				<c:if test="${not empty message}">
				<div class="row-fluid">
					<div class="span12">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
					</div>
				</div>
				</c:if>
				
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
				<!-- Modal -->
				<div id="uploadModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  				<s:url var="uploadUrl" value="/admin/brands/{slug}/upload">
						<s:param name="slug" value="${brand.slug}"></s:param>
					</s:url>
	  				<form:form action="${uploadUrl}" method="POST" enctype="multipart/form-data" modelAttribute="brand">
	  				<div class="modal-header">
	    				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	    				<h3 id="myModalLabel">Upload file</h3>
	  				</div>
	  				<div class="modal-body">
	    				<p>
							<input class="input-file" id="file" name="file" type="file">
							<form:errors element="span" cssClass="help-inline"/>
						</p>
						<p class="help-block">
							<s:message code="brand.logo.help.label"/>
						</p>
					</div>
	  				<div class="modal-footer">
	    				<button class="btn btn-primary"><s:message code="button.upload.label"/></button>
	    				<button class="btn" data-dismiss="modal" aria-hidden="true"><s:message code="button.close.label"/></button>
	  				</div>
	  				</form:form>
				</div>
				<div class="row-fluid">
					<div class="span10 offset1">
						<ul class="thumbnails">
            				<li class="span6">
            					<s:message code="brand.logo.title.label" arguments="${brand.name}" />
              					<a href="#" class="thumbnail">
                					<s:url value="/images/brand_{slug}" var="imgUrl">
										<s:param name="slug" value="${brand.slug}" />
									</s:url>
									
									<img src="${imgUrl}" alt="Not found"/>
              					</a>
            				</li>
               				<li class="span3">
              					<s:message code="brand.thumbnail.title.label" arguments="${brand.name}" />
              					<a href="#" class="thumbnail">
                					<s:url value="/images/th_brand_{slug}" var="imgUrl">
										<s:param name="slug" value="${brand.slug}" />
									</s:url>
									<img src="${imgUrl}" alt="Not found"/>
              					</a>
            				</li>
            			</ul>
					</div>
				</div>
				<div class="row-fluid" style="margin-bottom: 15px">
					<div class="span10 offset1">
						<s:url var="deleteImgUrl" value="/admin/brands/{slug}/upload">
							<s:param name="slug" value="${brand.slug}"></s:param>
						</s:url>
              			<form:form id="deleteForm" action="${deleteImgUrl}" method="DELETE">
							<a href="#uploadModal" class="open-uploadModal btn btn-warning" role="button" data-toggle="modal">
								<s:message code="button.upload.file.label"/>
							</a>&nbsp;&nbsp;or&nbsp;
							<a id="deleteLink" href="#"><s:message code="button.upload.delete.label"/></a>
						</form:form>
					</div>
				</div>
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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="uploadUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="imgUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="thumbUrl" required="true" rtexprvalue="true" %>

<!-- Modal -->
<div id="uploadModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<form:form action="${uploadUrl}" method="POST" enctype="multipart/form-data" modelAttribute="uploadForm">
		<form:hidden path="entity"/>
		<form:hidden path="slug"/>
	
		<div class="modal-header">
 			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
 			<h3 id="myModalLabel"><s:message code="upload.modal.box.title" text="Upload file"/></h3>
		</div>
		<div class="modal-body">
 			<p>
				<input class="input-file" id="file" name="file" type="file">
				<form:errors element="span" cssClass="help-inline"/>
			</p>
			<p class="help-block">
				<s:message code="upload.modal.help.label" text="Help text"/>
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
    			<s:message code="brand.logo.title.label" arguments="${name}" />
        		<a href="#" class="thumbnail">
					<img src="${imgUrl}" alt="Not found"/>
				</a>
        	</li>
        	<li class="span3">
        		<s:message code="brand.thumbnail.title.label" arguments="${name}" />
        		<a href="#" class="thumbnail">
        			<img src="${thumbUrl}" alt="Not found"/>
          		</a>
        	</li>
       	</ul>
	</div>
</div>
<div class="row-fluid" style="margin-bottom: 15px">
	<div class="span10 offset1">
		<form:form id="deleteForm" action="${uploadUrl}" method="DELETE" modelAttribute="uploadForm">
			<form:hidden path="entity"/>
			<form:hidden path="slug"/>
	
			<a href="#uploadModal" class="open-uploadModal btn btn-warning" role="button" data-toggle="modal">
				<s:message code="button.upload.file.label"/>
			</a>&nbsp;&nbsp;or&nbsp;
			<a id="deleteLink" href="#"><s:message code="button.upload.delete.label"/></a>
		</form:form>
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
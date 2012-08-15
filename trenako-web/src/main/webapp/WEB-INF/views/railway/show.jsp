<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="railways.show.title.label" arguments="${railway.name}" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">
							<s:message code="railways.header.title.label" />
						</li>
						<li class="active">
							<a href="<s:url value="/admin/railways" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="railways.list.label" />
							</a>
						</li>
						<li>
							<a href="<s:url value="/admin/railways/new" />" class="create">
								<i class="icon-plus"></i>
								<s:message code="railways.create.railway.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="railways.show.title.label" arguments="${railway.name}" /></h1>
					<small>
						<s:message code="railway.lastModified.label" /> ${railway.lastModified}
					</small>
				</div>
				
				<div class="row-fluid">
					<div class="span12">
						<c:if test="${not empty message}">
						<div class="alert alert-${message.type}">
							<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
						</div>
						</c:if>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span4">
						<div class="thumbnail">
							<s:url value="/images/railway_{slug}" var="imgUrl">
								<s:param name="slug" value="${railway.slug}" />
							</s:url>
							<img src="${imgUrl}" alt="Not found">
							<s:url value="/images/th_railway_{slug}" var="imgUrl">
								<s:param name="slug" value="${railway.slug}" />
							</s:url>
							<img src="${imgUrl}" alt="Not found">

							<div class="caption">
              					<h5></h5>
              					<p><s:message code="railway.logo.title.label" arguments="${railway.name}" /></p>
              					<p>
              						<s:url var="uploadUrl" value="/admin/railways/{slug}/upload">
										<s:param name="slug" value="${railway.slug}"></s:param>
									</s:url>
              						<form:form action="${uploadUrl}" method="POST" enctype="multipart/form-data" modelAttribute="brand">
              							<div class="control-group">
											<div class="controls">
												<input class="input-file" id="file" name="file" type="file">
												<form:errors element="span" cssClass="help-inline"/>
												<p class="help-block">
													<s:message code="railway.logo.help.label"/>
												</p>
											</div>
										</div>
										<p>
											<button class="btn btn-primary" type="submit" name="_action_upload">
												<s:message code="button.upload.file.label"/>
											</button>
										</p>
              						</form:form>
								</p>
            				</div>
            			</div>
            			<div class="thumbnail">
            				<div class="caption">
            					<p><s:message code="railway.logo.delete.help.label"/></p>
              					<p>
              						<s:url var="deleteImgUrl" value="/admin/railways/{slug}/upload">
										<s:param name="slug" value="${railway.slug}"></s:param>
									</s:url>
              						<form:form action="${uploadUrl}" method="DELETE">
  										<div></div>
  										<p>
											<button class="btn btn-danger" type="submit" name="_action_delete_img">
												<s:message code="button.upload.delete.label"/>
											</button>
										</p>
              						</form:form>
								</p>
            				</div>
						</div>
					</div>
					<div class="span8">
						<dl class="dl-horizontal">
							<dt><s:message code="railway.name.label" />:</dt>
							<dd>${railway.name}</dd>
							<dt><s:message code="railway.companyName.label" />:</dt>
							<dd>${railway.companyName}</dd>
							<dt><s:message code="railway.description.label" />:</dt>
							<dd><tk:eval expression="${railway.description}" /></dd>
							<dt><s:message code="railway.country.label"/>:</dt>
							<dd>${railway.country}</dd>
							<dt><s:message code="railway.operatingSince.label" />:</dt>
							<dd>${railway.operatingSince}</dd>
							<dt><s:message code="railway.operatingUntil.label" />:</dt>
							<dd>${railway.operatingUntil}</dd>
		    			</dl>
				
						<s:url value="/admin/railways/{id}" var="deleteUrl">
							<s:param name="id" value="${railway.id}" />
						</s:url>
						<form:form action="${deleteUrl}" method="delete" modelAttribute="railway" >
							<form:hidden path="id"/>
							<div class="form-actions">
								<s:url var="editUrl" value="/admin/railways/{slug}/edit">
						           	<s:param name="slug" value="${railway.slug}" />
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
				</div>
    		</div>
    	</div>
	</body>
</html>
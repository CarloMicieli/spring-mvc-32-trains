<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

<html>
	<head>
		<title>
			<s:message code="users.edit.title.label" arguments="${account.displayName}" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header"><s:message code="users.header.title.label" /></li>
						<li class="active">
							<a href="<s:url value="/admin/users" />" class="list">
								<i class="icon-list icon-white"></i>
								<s:message code="users.list.label" />
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1><s:message code="users.edit.title.label" arguments="${account.displayName}" /></h1>
				</div>
				<s:url var="editUrl" value="/admin/users"/>
				
				<html:form actionUrl="${editUrl}" model="account" method="POST">
					<form:hidden path="id"/>
					<form:hidden path="slug"/>
					<form:hidden path="displayName"/>
					
					<input type="hidden" id="emailAddress" name="emailAddress" value="mail@mail.com"/>
					<input type="hidden" id="password" name="password" value="{secret}"/>
										
					<html:checkBox bindContext="account" name="enabled" 
						label="user.enabled.label"
						helpLabel="user.enabled.help.label" />
					
					<html:checkBox bindContext="account" name="locked" 
						label="user.locked.label"
						helpLabel="user.locked.help.label" />					
					
					<html:multiCheckBox items="${rolesList}" 
						label="user.roles.label" 
						name="roles" 
						title="user.roles.title.label"/>
					
					
				</html:form>
			</div>
		</div>	
	</body>
</html>
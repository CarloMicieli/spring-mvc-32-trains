<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
	<head>
		<title>Not found</title>
	</head>
	<body>
		<div class="page-header">
			<h1>
				Not found
				<small>Sorry we can't find the requested page.</small>
			</h1>
		</div>
		
		<div class="row-fluid" style="padding: 25px">
			<div class="span5 offset1">
				<s:url var="imgUrl" value="/resources/img/static/error.png"/>
				<img src="${imgUrl}"/>
			</div>
			<div class="span6">
				The requested page is not here...
				<br/>
				<s:url var="homeUrl" value="/"/>
				<a href="${homeUrl}">Back to the home page</a>
			</div>
		</div>
	</body>
</html>
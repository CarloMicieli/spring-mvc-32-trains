<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>Brands</title>
	</head>
	<body>
		<ul>
		<c:forEach var="brand" items="${brands}">
			<spring:url var="showUrl" value="/brands/{slug}">
            	<spring:param name="slug" value="${brand.slug}" />
			</spring:url>
            <a href="${showUrl}">view</a>
			<li>${brand.name}</li>
		</c:forEach>
		</ul>
	</body>
</html>
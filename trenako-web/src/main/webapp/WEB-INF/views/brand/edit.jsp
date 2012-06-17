<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<title>
		Brands | new
	</title>
	<body>
		<c:url value="/brands" var="brandsUrl" />
		<form:form id="form" method="post" action="${brandsUrl}" modelAttribute="brand" >
		
			<form:label path="name">
		  		Name <form:errors path="name" cssClass="error" />
		 	</form:label>
		  	<form:input path="name" />
		  	
		  	<p><button type="submit">Submit</button></p>
		</form:form>
	</body>
</html>
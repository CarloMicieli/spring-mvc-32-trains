<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
<head>
	<title><s:message code="rolling.stock.title.label"/></title>
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
				<form class="well form-inline">
					<div class="row-fluid">
						<div class="span2">
							<sec:authorize url="/rollingstocks/new">
								<s:url var="createUrl" value="/rollingstocks/new" />
								<a href="${createUrl}" class="btn btn-info"><s:message code="button.create.new.label"/></a>
							</sec:authorize>
						</div>
						<div class="span10">
							<div class="pull-right">
								<form:form method="get" modelAttribute="options" class="form-inline">
									<s:message code="rolling.stock.items.label"/>:&nbsp;
									
									<form:select path="size" class="input-mini">
										<form:options items="${options.resultSizes}"/>
									</form:select>
									
									<button type="submit" class="btn">
										<s:message code="rolling.stock.change.button"/>
									</button>
								</form:form>
							</div>
						</div>
					</div>
				</form>
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

        	<c:forEach var="rs" items="${results.items}">
        		<s:url value="/rollingstocks/{slug}" var="showUrl">
					<s:param name="slug" value="${rs.slug}" />
				</s:url>
        	
				<div class="row-fluid">
					<div class="span1">
						<strong>${rs.brand.label}</strong><br/>
						${rs.itemNumber}
					</div>
					<div class="span3">
						<a href="${showUrl}" class="thumbnail">
					      	<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
								<s:param name="slug" value="${rs.slug}" />
							</s:url>
							<img src="${imgUrl}" alt="Not found">
					    </a>
					</div>
					<div class="span8">
						<h4><tk:eval expression="${rs.description}" maxLength="50" /></h4>
						<div class="row-fluid">
							<div class="span2">
								<s:message code="rollingStock.era.label" />: 
								<br/><strong><tk:evalValue type="Era" expression="${rs.era}"/></strong>
							</div>
							<div class="span3">
								<s:message code="rollingStock.powerMethod.label" />: 
								<br/><strong><tk:evalValue type="PowerMethod" expression="${rs.powerMethod}"/></strong>
							</div>
							<div class="span2">
								<s:message code="rollingStock.scale.label" />: 
								<br /><strong>${rs.scale.label}</strong>
							</div>
							<div class="span5">
								<s:message code="rollingStock.railway.label" />: 
								<br /><strong>${rs.railway.label}</strong>
							</div>
						</div>
						<div class="row-fluid">
							<a href="${showUrl}">
								<s:message code="button.details.label"/>
							</a> - 
							<em><tk:period since="${rs.lastModified}"/></em> 
						</div>
					</div>
				</div>
				<hr/>
        	</c:forEach>
        	
        	<div class="row-fluid">
        		<p><tk:pager results="${results}"/></p>
        	</div>
        	
		</div>
	</div>
</body>
</html>

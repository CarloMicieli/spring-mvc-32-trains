<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="options.list.title.label" />
		</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					
					<a class="btn btn-info" href="<s:url value="/admin/options/new" />">
						<s:message code="options.create.option.label" /></a>
				</div>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><s:message code="options.list.label" /></h1>
				</div>
			
				<c:if test="${not empty message}">
				<div class="alert alert-${message.type}">
					<s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
				</div>
				</c:if>
				
				<div class="tabbable tabs-left">
              		<ul class="nav nav-tabs">
                		<li class="active">
                			<a href="#lA" data-toggle="tab">
	                			<s:message code="optionfamily.dcc.interface.label"/>
							</a>
						</li>
                		<li class=""><a href="#lB" data-toggle="tab"><s:message code="optionfamily.headlights.label"/></a></li>
                		<li class=""><a href="#lC" data-toggle="tab"><s:message code="optionfamily.transmission.label"/></a></li>
                		<li class=""><a href="#lD" data-toggle="tab"><s:message code="optionfamily.coupler.label"/></a></li>
              		</ul>
              		
              		<div class="tab-content">
                		<div class="tab-pane active" id="lA">
                			<ul class="unstyled">
							<c:forEach var="opt1" items="${dccOptions}">
								<li>${opt1.name}</li>								
							</c:forEach>
							</ul>			
                		</div>
                		<div class="tab-pane" id="lB">
                  			<ul class="unstyled">
							<c:forEach var="opt2" items="${headlightsOptions}">
								<li>${opt2.name}</li>								
							</c:forEach>
							</ul>	
                		</div>
                		<div class="tab-pane" id="lC">
                   			<ul class="unstyled">
							<c:forEach var="opt3" items="${transmissionOptions}">
								<li>${opt3.name}</li>								
							</c:forEach>
							</ul>	
                		</div>
                		<div class="tab-pane" id="lD">
                   			<ul class="unstyled">
							<c:forEach var="opt4" items="${couplerOptions}">
								<li>${opt4.name}</li>								
							</c:forEach>
							</ul>	
                		</div>                		
              		</div>
            	</div>
			</div>
		</div>
	</body>
</html>
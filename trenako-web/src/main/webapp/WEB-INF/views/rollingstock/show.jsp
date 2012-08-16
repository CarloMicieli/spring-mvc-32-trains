<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>
			<s:message code="rollingStock.page.new.title" />
		</title>
		<meta name="rs" content="active"/>
	</head>
	<body>
		<ul class="breadcrumb">
			<li>
				<s:url value="/rs" var="rsUrl"/>
		    	<a href="${rsUrl}"><s:message code="browse.breadcrumb.rollingStocks.label"/></a> <span class="divider">/</span>
			</li>
			<li>
				<s:url value="/browse/brands/{slug}" var="brandsUrl">
					<s:param name="slug" value="${rollingStock.brand.slug}"></s:param>
				</s:url>
		    	<a href="${brandsUrl}">${rollingStock.brand.label}</a> <span class="divider">/</span>
			</li>		
		  	<li class="active">${rollingStock.itemNumber}</li>
		</ul>
		<div class="row-fluid">
        	<div class="page-header">
				<h1>${rollingStock.brand.label} ${rollingStock.itemNumber}</h1>
				<small>${rollingStock.category}</small>
			</div>
		</div>
	    <div class="row-fluid">
	    	<div class="span2">
	    		<p>
					<a class="btn btn-success" href="#"><i class="icon-comment icon-white"></i> Write review</a>
				</p>
				<p>
					<a class="btn btn-danger" href="#"><i class="icon-tags icon-white"></i> Add collection</a>
				</p>
				<div class="btn-group">
					<a class="btn btn-primary" href="#"><i class="icon-gift icon-white"></i> Add wish list</a>
					<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="icon-list-alt"></i> New stuff</a></li>
						<li><a href="#"><i class="icon-list-alt"></i> Old stuff</a></li>
						<li><a href="#"><i class="icon-list-alt"></i> New</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="i"></i> New wishlist</a></li>
					</ul>
				</div>
			</div>
	    	<div class="span6">
				<s:url value="/images/rollingstock_{slug}" var="imgUrl">
					<s:param name="slug" value="${rollingStock.slug}" />
				</s:url>
				<img src="${imgUrl}" alt="Not found">
            </div>
            <div class="span4">
            	<div class="tabbable">
                	<ul class="nav nav-tabs">
                    	<li class="active"><a href="#tab1" data-toggle="tab"><s:message code="rollingStock.overview.label" /></a></li>
                        <li><a href="#tab2" data-toggle="tab"><s:message code="rollingStock.details.label" /></a></li>
						<li><a href="#tab3" data-toggle="tab"><s:message code="rollingStock.options.label" /></a></li>
               		</ul>
                  	<div class="tab-content">
                    	<div class="tab-pane active" id="tab1">
							<dl class="dl-horizontal">
								<dt><s:message code="rollingStock.description.label" /></dt>
								<dd><tk:eval expression="${rollingStock.description}"/></dd>
								<dt><s:message code="rollingStock.scale.label" /></dt>
								<dd>${rollingStock.scale.label}</dd>
								<dt><s:message code="rollingStock.era.label" /></dt>
								<dd><s:message code="era.${rollingStock.era}.name" /></dd>
								<dt><s:message code="rollingStock.railway.label" /></dt>
								<dd>${rollingStock.railway.label}</dd>
								<dt><s:message code="rollingStock.powerMethod.label" /></dt>
								<dd>${rollingStock.powerMethod}</dd>
							</dl>
					    </div>
                  		<div class="tab-pane" id="tab2">
                        	<p><tk:eval expression="${rollingStock.details}"/></p>
               			</div>
                        <div class="tab-pane" id="tab3">
                        	<p>Howdy, I'm in Section 3.</p>
						</div>								
					</div>
				</div>
           	</div>
		</div>
		<div class="row-fluid">
        	<div class="span2"></div>
           	<div class="span6">
				<p>
					<span class="label label-info">Tags</span>
					<a href="#">tag1</a> <a href="#">tag1</a> <a href="#">tag1</a>
				</p>
        	</div>
         	<div class="span4"></div>
   		</div>
   		<div class="row-fluid">
        	<div class="span2"></div>
           	<div class="span6">
           		<sec:authorize access="isAuthenticated()">
           		<form:form modelAttribute="newComment" method="POST" action="#" class="well form-inline">
           			<form:hidden path="rollingStock"/>
           			<form:hidden path="author"/>
           			
           			<form:textarea path="content" class="input-small" style="width:400px" rows="3" placeholder="New comment"/>
           			<form:button type="submit" class="btn">Send</form:button>
           		</form:form>
           		<hr/>
           		</sec:authorize>
        	</div>
         	<div class="span4"></div>
   		</div>
	</body>
</html>
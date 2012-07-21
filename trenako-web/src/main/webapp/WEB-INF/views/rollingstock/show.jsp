<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>
			<s:message code="rollingStock.page.new.title" />
		</title>
		<meta name="browse" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
       		<ul class="breadcrumb">
            	<li>
                	<a href="#">Home</a> <span class="divider">/</span>
                </li>
                <li>
                	<a href="#">Library</a> <span class="divider">/</span>
				</li>
                <li class="active">Data</li>
			</ul>
		</div>
		<div class="row-fluid">
        	<div class="page-header">
				<h1>${rollingStock.brand.name} ${rollingStock.itemNumber}</h1>
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
				<img src="http://placehold.it/550x350" alt="">
            </div>
            <div class="span4">
            	<div class="tabbable">
                	<ul class="nav nav-tabs">
                    	<li class="active"><a href="#tab1" data-toggle="tab">Overview</a></li>
                        <li><a href="#tab2" data-toggle="tab">Details</a></li>
						<li><a href="#tab3" data-toggle="tab">Options</a></li>
               		</ul>
                  	<div class="tab-content">
                    	<div class="tab-pane active" id="tab1">
							<dl class="dl-horizontal">
								<dt><s:message code="rollingStock.description.label" /></dt>
								<dd>${rollingStock.description}</dd>
								<dt><s:message code="rollingStock.scale.label" /></dt>
								<dd>${rollingStock.scaleName} (1:87)</dd>
								<dt><s:message code="rollingStock.era.label" /></dt>
								<dd>${rollingStock.era}</dd>										
								<dt><s:message code="rollingStock.railway.label" /></dt>
								<dd>${rollingStock.railway.name}</dd>
								<dd>${rollingStock.railway.companyName} (${rollingStock.country})</dd>
								<dt><s:message code="rollingStock.powerMethod.label" /></dt>
								<dd>${rollingStock.powerMethod}</dd>
							</dl>
					    </div>
                  		<div class="tab-pane" id="tab2">
                        	<p>${rollingStock.details}</p>
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
	</body>
</html>
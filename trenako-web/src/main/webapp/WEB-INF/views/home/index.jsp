<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
	<head>
		<title>Trenako.com</title>
		<meta name="home" content="active"/>
	</head>
	
	<body>
		<div class="row">
			<div class="span9">
				<div class="hero-unit">
        			<h1>Welcome aboard!</h1>
        			<p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
        			<p><a class="btn btn-primary btn-large" href="<s:url value="/home/explore"/>">Learn more &raquo;</a></p>
      			</div>
      		</div>
      		<div class="span3">
				<form id="form" class="form-vertical" action="<s:url value="/auth/signup"/>" method="POST">
				<fieldset>
      				<legend>Create new account</legend>
      			</fieldset>
      			    
   					
   					<div class="control-group">
						<label for="emailAddress" class="control-label">
							Email address:
						</label>
						<div class="controls">
							<input id="emailAddress" name="emailAddress" class="input-xlarge focused" required="required" type="text" value=""/>
							
						</div>
					</div>

				    <div class="control-group">
						<label for="displayName" class="control-label">
							Display name:
						</label>
						<div class="controls">
							<input id="displayName" name="displayName" class="input-xlarge focused" required="required" type="text" value=""/>
							
						</div>
					</div>

				    <div class="control-group">
						<label for="password" class="control-label">
							Password:
						</label>
						<div class="controls">
							<input id="password" name="password" class="input-xlarge focused" required="required" type="password" value=""/>
						</div>
					</div>

					<div class="form-actions">
						<button id="_action_save" name="_action_save" class="btn btn-primary" type="submit" type="submit" value="Submit">
							<i class="icon-user icon-white"></i>
							Sign up
						</button>
					
						<button id="_action_reset" name="_action_reset" class="btn" type="reset" type="submit" value="Submit">
							<i class="icon-repeat icon-black"></i>
							Reset
						</button>
					</div>					
				</form>
      		</div>
		</div>

      	<div class="row">
        	<div class="span4">
          		<h2>The passion of trains</h2>
           		<p>
           		Donec id elit non mi porta gravida at eget metus. Fusce dapibus, 
           		tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum
           		massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. 
           		Donec sed odio dui.
           		</p>
          		<p><a class="btn" href="#">View details &raquo;</a></p>
        	</div>
        	<div class="span4">
          		<h2>New rolling stocks</h2>
           		<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          		<p><a class="btn" href="#">View details &raquo;</a></p>
       		</div>
        	<div class="span4">
          		<h2>Last activity</h2>
          		<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          		<p><a class="btn" href="#">View details &raquo;</a></p>
        	</div>
      	</div>
	</body>
</html>
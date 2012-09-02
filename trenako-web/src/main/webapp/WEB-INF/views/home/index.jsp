<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>

<html>
	<head>
		<title>Trenako.com</title>
		<meta name="home" content="active"/>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span9">
				<div class="row-fluid">
					<div class="hero-unit">
						<h1><s:message code="home.hero.title.label"/></h1>
						<p><s:message code="home.hero.par1.text"/></p>
						<p><s:message code="home.hero.par2.text"/></p>
						<p><a class="btn btn-primary btn-large" href="<s:url value="/home/explore"/>"><s:message code="button.learn.more.label"/></a></p>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span5">
						<h2><s:message code="home.recent.activity.title"/></h2>
						<p>
							<c:forEach var="act" items="${content.activityStream}">
								<div class="row-fluid" style="border-left: thick solid orange;">
									<div class="span3 offset1">
										<tk:avatar user="${act.actor}" size="48" showName="true"/>
									</div>
									<div class="span8">
										<a href="#">${act.actor}</a> ${act.verb} <a href="#">${act.object.displayName}</a>
										<br/>
										<strong><tk:period since="${act.recorded}"/></strong> 
									</div>
								</div>
								<hr/>
							</c:forEach>
						</p>
					</div>
					
					<div class="span7">
						<h2><s:message code="home.rolling.stocks.title"/></h2>
						<sec:authorize url="/rollingstocks/new">
						<p>
							<s:message code="home.rolling.stocks.text"/>
						</p>
						<p>
							<a class="btn btn-info" href="<s:url value="/rollingstocks/new" />"><s:message code="button.new.rolling.stock.label"/></a>
						</p>
						<hr/>
						</sec:authorize>
						<p>
							<c:forEach var="rs" items="${content.rollingStocks}">
								<div class="row-fluid">
									<div class="span3 offset1">
							       		<s:url value="/rollingstocks/{slug}" var="showUrl">
											<s:param name="slug" value="${rs.slug}" />
										</s:url>
										<a href="${showUrl}" class="thumbnail">
									      	<s:url value="/images/th_rollingstock_{slug}" var="imgUrl">
												<s:param name="slug" value="${rs.slug}" />
											</s:url>
											<img src="${imgUrl}" alt="Not found">
									    </a>
									    <br/>
										<tk:period since="${rs.lastModified}"/>
									</div>
									<div class="span8">
										<h5><tk:eval expression="${rs.description}" maxLength="50" /></h5>
										<dl class="dl-horizontal">
											<dt><s:message code="rollingStock.itemNumber.label" /></dt> 
											<dd>${rs.brand.label} - ${rs.itemNumber}</dd>
											<dt><s:message code="rollingStock.scale.label" />:</dt> 
											<dd>${rs.scale.label}</dd>
											<dt><s:message code="rollingStock.railway.label" />:</dt> 
											<dd>${rs.railway.label}</dd>
										</dl>
									</div>
								</div>
								<hr/>
							</c:forEach>
						</p>
						<div class="pull-right">
							<s:url var="rsUrl" value="/rs"/>
							<a class="btn" href="${rsUrl}">See more</a>
						</div>
					</div>
				</div>
			</div>
			<div class="span3">
				<form id="form" class="form-vertical" action="<s:url value="/auth/signup"/>" method="POST">
					<fieldset>
						<legend><s:message code="signup.title.label" /></legend>
					</fieldset>

					<div class="control-group">
						<label for="emailAddress" class="control-label">
							<s:message code="account.emailAddress.label" />:
						</label>
						<div class="controls">
							<s:message var="email" code="account.emailAddress.help.label" />
							<input id="emailAddress" name="emailAddress" class="input-xlarge focused" required="required" type="text" value="" placeholder="${email}"/>
						</div>
					</div>

					<div class="control-group">
						<label for="displayName" class="control-label">
							<s:message code="account.displayName.label" />:
						</label>
						<div class="controls">
							<s:message var="name" code="account.displayName.help.label" />
							<input id="displayName" name="displayName" class="input-xlarge focused" required="required" type="text" value="" placeholder="${name}"/>
						</div>
					</div>

					<div class="control-group">
						<label for="password" class="control-label">
							<s:message code="account.password.label" />:
						</label>
						<div class="controls">
							<s:message var="pwd" code="account.password.help.label" text="At least 6 characters"/>
							<input id="password" name="password" data-typetoggle="#show-password" class="input-xlarge focused" required="required" type="password" value="" placeholder="${pwd}"/>
							<label class="checkbox">
								<input id="show-password" type="checkbox"> <s:message code="account.showPassword.label" />
							</label>
						</div>
					</div>

					<div class="form-actions">
						<button id="_action_save" name="_action_save" class="btn btn-primary" type="submit" type="submit" value="Submit">
							<i class="icon-user icon-white"></i>
							<s:message code="button.signup.label" />
						</button>

						<button id="_action_reset" name="_action_reset" class="btn" type="reset" type="submit" value="Submit">
							<i class="icon-repeat icon-black"></i>
							<s:message code="button.reset.label" />
						</button>
					</div>
				</form>
			</div>
		</div>

		

		<script src="<c:url value="/resources/js/jquery.showpassword.js" />" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#password').showPassword();
			});
		</script>

	</body>
</html>
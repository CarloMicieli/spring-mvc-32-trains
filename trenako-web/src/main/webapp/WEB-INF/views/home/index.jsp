<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="/WEB-INF/tlds/TrenakoTagLib.tld" prefix="tk" %>
<%@ taglib tagdir="/WEB-INF/tags/html" prefix="html" %>

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
						<p>
							<a class="btn btn-primary btn-large" href="<s:url value="/home/explore"/>">
								<i class="icon-info-sign icon-white"></i> <s:message code="button.learn.more.label"/>
							</a>
						</p>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span4">
						<h2><s:message code="home.recent.activity.title"/></h2>
						<p>
							<c:forEach var="act" items="${content.activityStream}">
								<div class="row-fluid" style="border-left: thick solid ${act.color};">
									<div class="span4">
										<html:thumb slug="${act.object.slug}"/>
									</div>
									<div class="span8">
										<tk:activity activity="${act}"/> 
									</div>
								</div>
								<hr/>
							</c:forEach>
						</p>
					</div>
					
					<div class="span8" style="margin-bottom: 15px">
						<h2><s:message code="home.rolling.stocks.title"/></h2>
						<p>
							<c:forEach var="rs" items="${content.rollingStocks}">
								<div class="row-fluid">
									<div class="span11 offset1">
										<h4><tk:eval expression="${rs.description}" maxLength="50" /></h4>
									</div>
								</div>
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
									</div>
									<div class="span8">
										<div class="row-fluid">
											<div class="span4">
												<strong><s:message code="rollingStock.itemNumber.label" /></strong>
												<br/>
												${rs.brand.label} - ${rs.itemNumber}
											</div>
											<div class="span3">
												<strong><s:message code="rollingStock.scale.label" /></strong>
												<br/>
												${rs.scale.label}
											</div>
											<div class="span5">
												<strong><s:message code="rollingStock.railway.label" /></strong>
												<br/>
												${rs.railway.label}
											</div>
										</div>
										<strong><tk:period since="${rs.lastModified}"/></strong>
									</div>
								</div>
								<hr/>
							</c:forEach>
						</p>
						<div class="pull-right">
							<s:url var="rsUrl" value="/rs"/>
							<a class="btn" href="${rsUrl}"><s:message code="home.see.more.label"/></a>
						</div>
					</div>
				</div>
			</div>
			<div class="span3">
			<sec:authorize var="loggedIn" access="isAuthenticated()" />
			<c:choose>
			    <c:when test="${loggedIn}">
			    	<h4>
						<s:message code="home.welcome.message.label" arguments="${account.displayName}"/>
						<br/>
						<small>(<s:message code="home.welcome.member.since.label"/> <tk:period since="${account.memberSince}"/>)</small> 
					</h4>

					<hr/>
					
					<p>
						<s:message code="home.welcome.help.label"/>
					</p>
					<ul class="nav nav-list" style="padding: 0 15px 0 15px">
						<s:url var="youUrl" value="/you"/>
						<li class=""><a href="${youUrl}">
							<i class="icon-chevron-right"></i> 
							<s:message code="home.welcome.your.page.label"/>
						</a></li>
						
						<s:url var="collectionsUrl" value="/collections/{slug}">
							<s:param name="slug" value="${account.slug}"></s:param>
						</s:url>
						<li class=""><a href="${collectionsUrl}">
							<i class="icon-chevron-right"></i> 
							<s:message code="home.welcome.your.collection.label"/>
						</a></li>
						
						<s:url var="wishlistsUrl" value="/wishlists/owner/{slug}">
							<s:param name="slug" value="${account.slug}"></s:param>
						</s:url>
						<li class=""><a href="${wishlistsUrl}">
							<i class="icon-chevron-right"></i> 
							<s:message code="home.welcome.your.wishlists.label"/>
						</a></li>
						
						<s:url var="rollingStocksUrl" value="/rs"/>
						<li class=""><a href="${rollingStocksUrl}">
							<i class="icon-chevron-right"></i> 
							<s:message code="home.welcome.rolling.stocks.label"/>
						</a></li>
						
						<s:url var="browseUrl" value="/browse"/>
						<li class=""><a href="${browseUrl}">
							<i class="icon-chevron-right"></i> 
							<s:message code="home.welcome.browse.label"/>
						</a></li>						
			        </ul>
			        <p>
						<s:message code="home.welcome.undecided.label"/>
					</p>
					<p>
						<a class="btn btn-info" href="<s:url value="/rollingstocks/new" />">
							<i class="icon-file icon-white"></i> <s:message code="button.new.rolling.stock.label"/>
						</a>
					</p>
			    </c:when>
			    <c:otherwise>
			        <html:signUpForm/>
			    </c:otherwise>
			</c:choose>
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
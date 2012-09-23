<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
						
<%@ attribute name="slug" required="true" rtexprvalue="true" %>

<s:url var="showUrl" value="/rollingstocks/{slug}">
	<s:param name="slug" value="${slug}" />
</s:url>
<a href="${showUrl}" class="thumbnail">
	<s:url var="imgUrl" value="/images/th_rollingstock_{slug}">
		<s:param name="slug" value="${slug}" />
	</s:url>
	<img src="${imgUrl}" alt="Not found">
</a>
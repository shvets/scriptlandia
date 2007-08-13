<%@ include file="/templates/taglibs-inc.jsp" %>

Testing tiles...
<br><br>

<p>JSTL -> <fmt:message key="greeting"/>

<p>Spring -> <spring:message code="greeting"/>

<br>
<a href="<c:url value='index.jsp'/>">Back</a>

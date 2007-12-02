<%@ include file="/templates/taglibs-inc.jsp" %>

Testing Struts...
<br>


<h1>Struts action</h1>

<c:forEach items="${items}" var="item">
  <c:out value="${item.name}"/><br>
</c:forEach>

<br>

From action: <c:out value="${fromAction}"/>

<br>
<a href="<c:url value='index.jsp'/>">Back</a>

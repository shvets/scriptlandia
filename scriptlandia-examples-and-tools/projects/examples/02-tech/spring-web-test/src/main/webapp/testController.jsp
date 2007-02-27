<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head><title><fmt:message key="title"/></title></head>

<body>

  <h1><fmt:message key="heading"/></h1>

  <p>JSTL -> <fmt:message key="greeting"/> : <c:out value="${model.date}"/></p>

  <p>Spring -> <spring:message code="greeting"/> : <c:out value="${model.date}"/></p>

  <h3>Items</h3>
  
  <c:forEach items="${model.items}" var="item">
    <c:out value="${item.name}"/> <i>$<c:out value="${item.price}"/></i><br><br>
  </c:forEach>

  <br>
  <a href="<c:url value='index.jsp'/>">Back</a>

</body>

</html>

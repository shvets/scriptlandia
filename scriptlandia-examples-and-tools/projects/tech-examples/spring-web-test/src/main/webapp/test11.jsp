<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head><title></title></head>

<body>

  <h3>Items</h3>

  <c:forEach items="${model.items}" var="item">
    <c:out value="${item.name}"/> <i>$<c:out value="${item.price}"/></i><br><br>
  </c:forEach>

  <br>
  <a href="<c:url value='index.jsp'/>">Back</a>

</body>

</html>
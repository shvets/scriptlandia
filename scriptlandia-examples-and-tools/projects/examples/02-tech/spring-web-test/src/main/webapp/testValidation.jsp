<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <title><fmt:message key="title"/></title></head>

  <c:set var="css"><spring:theme code="css"/></c:set>
  <c:if test="${not empty css}"><link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css"/></c:if>
<body>

  <h1><fmt:message key="heading"/></h1>

  <p>JSTL -> <fmt:message key="greeting"/> : <c:out value="${model.date}"/></p>
  <p>Spring -> <spring:message code="greeting"/> : <c:out value="${model.date}"/></p>

  <h3>Items</h3>
  <c:forEach items="${model.items}" var="item">
    <c:out value="${item.name}"/> <i>$<c:out value="${item.price}"/></i>
    <a href="<c:url value='edit.spring?itemId=${item.id}&mode=edit'/>">Edit</a>
    <!--a href="<c:url value='edit2.spring?itemId=${item.id}&mode=edit'/>">Edit (validator)</a-->
    <br><br>
  </c:forEach>

  <br>
  <a href="<c:url value='edit.spring'/>">Add new Item</a>
  <!--a href="<c:url value='edit2.spring'/>">Add new Item (validator)</a==>

  <br>
  <a href="<c:url value='index.jsp'/>">Back</a>

</body>

</html>

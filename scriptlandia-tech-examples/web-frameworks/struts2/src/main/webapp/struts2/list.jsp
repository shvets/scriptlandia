<%@ include file="/taglibs1-inc.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>List Users</title></head>

<body>

  Users:
  <br/>

  <c:forEach var="user" items="${requestScope.list}">
    <a href="view.action?id=<c:out value="${user.id}"/> ">
      (<c:out value="${user.id}"/>)
      <c:out value="${user.title}"/>:
      <c:out value="${user.firstName}"/>
      <c:out value="${user.lastName}"/>
      <c:out value="${user.admin}"/>
    </a>
    (<a href="delete.action?id=<c:out value="${user.id}"/> ">delete</a>)
    <br/>
  </c:forEach>

  <a href="create.action">Add a new entry</a>

</body>

</html>

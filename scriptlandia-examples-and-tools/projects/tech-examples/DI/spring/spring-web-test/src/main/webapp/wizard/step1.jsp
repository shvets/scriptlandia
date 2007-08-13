<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <c:set var="css"><spring:theme code="css"/></c:set>
  <c:if test="${not empty css}"><link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css"/></c:if>
</head>

<body>
  STEP 1:
  <br>

  <form action="test7.spring" method="post">
    <input type="hidden" name="_page" value="0">
    <table>
      <tr>
        <td>Name:</td>
        <td><spring:bind path="command.name">
          <input name="name" value="<c:out value="${status.value}"/>">
          <span class="error"><c:out value="${status.errorMessage}"/></span>
        </spring:bind>
        </td>
      </tr>
      <tr>
        <td><input type="submit" class="button" name="_target1" value="Next &raquo;"/></td>
        <td><input type="submit" class="button" name="_cancel" value="Cancel"/></td>
      </tr>
    </table>

    <spring:hasBindErrors name="command">
        <b>Please fix all errors!</b>
      </spring:hasBindErrors>

   <table>
      <spring:bind path="command.*">
        <c:forEach items="${status.errorMessages}" var="error">
        <tr>
          <td><c:out value="${error}"/></td>
        </tr>
        </c:forEach>
      </spring:bind>
    </table>
  </form>
</body>

</html>


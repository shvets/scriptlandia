<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <c:set var="css"><spring:theme code="css"/></c:set>
  <c:if test="${not empty css}"><link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css"/></c:if>
</head>

<body>
  STEP 2:
  <br>
  <form action="test7.spring" method="post">
    <input type="hidden" name="_page" value="1">
    <table>
      <tr>
        <td>Expiration Date:</td>
        <td><spring:bind path="command.expirationDate">
          <input name="expirationDate" value="<c:out value="${status.value}"/>">
          <span class="error"><c:out value="${status.errorMessage}"/></span>
        </spring:bind>
        </td>
      </tr>
      <tr>
        <tr>
          <td><input type="submit" class="button" name="_target0" value="&laquo; Prev"/></td>
          <td><input type="submit" class="button" name="_target2" value="Next &raquo;"/></td>
          <td><input type="submit" class="button" name="_cancel" value="Cancel"/></td>
        </tr>
      </tr>
    </table>
  </form>
</body>

</html>
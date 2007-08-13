<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <c:set var="css"><spring:theme code="css"/></c:set>
  <c:if test="${not empty css}">
    <link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css" />
  </c:if>

</head>

<body>

  <form method="post">
    <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td align="right" width="20%">Name:</td>

        <spring:bind path="newItem.name">
          <td width="20%">
            <input type="text" name="name" value='<c:out value="${status.value}"/>'>
          </td>
          <td width="60%">
            <font color="red"><c:out value="${status.errorMessage}"/></font>
          </td>
        </spring:bind>
      </tr>

      <tr>
        <td align="right" width="20%">Price:</td>

        <spring:bind path="newItem.price">
          <td width="20%">
            <input type="text" name="price" value='<c:out value="${status.value}"/>'>
          </td>
          <td width="60%">
            <font color="red"><c:out value="${status.errorMessage}"/></font>
          </td>
        </spring:bind>
      </tr>

      <tr>
        <td align="right" width="20%">Expiration Date:</td>

        <spring:bind path="newItem.expirationDate">
          <td width="20%">
            <spring:transform value="${newItem.expirationDate}" var="formattedDate"/>
            <%--input type="text" name="expirationDate" value='<c:out value="${status.value}"/>'--%>
            <input type="text" name="expirationDate" value='<c:out value="${formattedDate}"/>'>
          </td>
          <td width="60%">
            <font color="red"><c:out value="${status.errorMessage}"/></font>
          </td>
        </spring:bind>

      </tr>

    </table>

    <br>

    <spring:hasBindErrors name="newItem">
      <b>Please fix all errors!</b>
    </spring:hasBindErrors>

    <br><br>

    <!-- if you need to display all errors (both global and all field errors,
         use wildcard (*) in place of the property name -->
    <%--table>
      <spring:bind path="newItem.*">
        <c:forEach items="${status.errorMessages}" var="error">
        <tr>
          <td><c:out value="${error}"/></td>
        </tr>
        </c:forEach>
      </spring:bind>
    </table>

    <br><br--%>

    <input name="add" type="submit" value="Add/Edit Item">

  </form>

</body>

</html>

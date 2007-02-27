<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <c:set var="css"><spring:theme code="css"/></c:set>

  <c:if test="${not empty css}">
    <link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css" />
  </c:if>
</head>

<body>
  <table>
    <tr>
      <td>
        <tiles:insert attribute="header"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insert attribute="content"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insert attribute="footer"/>
      </td>
    </tr>
  </table>
</body>

</html>
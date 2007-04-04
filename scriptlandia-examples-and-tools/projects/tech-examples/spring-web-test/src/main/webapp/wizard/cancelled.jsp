<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <c:set var="css"><spring:theme code="css"/></c:set>
  <c:if test="${not empty css}"><link rel="stylesheet" href="<c:url value="${css}"/>" type="text/css" /></c:if>
</head>

<body>
  Maybe next time...
</body>

</html>
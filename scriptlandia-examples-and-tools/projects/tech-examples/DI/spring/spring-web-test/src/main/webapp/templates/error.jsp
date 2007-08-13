<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head><title>Error Page</title></head>

<body>

  <h1>Error Page</h1>

  <p>
    Error: <c:out value="${exception.message}"/>
  </p>

</body>

</html>
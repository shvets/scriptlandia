<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head></head>

<body>
  Testing multi action...
  <br>
  Mode:  <c:out value="${model.mode}"/>

  <br>
  <a href="<c:url value='test6.spring?method=view'/>">View</a>

  <br>
  <a href="<c:url value='test6.spring?method=edit'/>">Edit</a>

  <br>
  <a href="<c:url value='index.jsp'/>">Back</a>
  
</body>

</html>

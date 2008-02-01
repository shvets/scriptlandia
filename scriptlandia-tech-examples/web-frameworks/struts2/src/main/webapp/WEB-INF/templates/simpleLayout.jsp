<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/taglibs2-inc.jsp" %>

<%
  // Set to expire far in the past.
  response.setHeader("Expires", "Sat, 6 May 1095 12:00:00 GMT");
  // Set standard HTTP/1.1 no-cache headers.
  response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
  // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
  response.addHeader("Cache-Control", "post-check=0, pre-check=0");
  // Set standard HTTP/1.0 no-cache header.
  response.setHeader("Pragma", "no-cache");
%>

<html:html>

<head>

  <title>
    <tiles:getAsString name="title"/>
  </title>

</head>

<body>

  <tiles:insertAttribute name="header"/>

  <tiles:insertAttribute name="content"/>

  <tiles:insertAttribute name="footer"/>

</body>

</html:html>

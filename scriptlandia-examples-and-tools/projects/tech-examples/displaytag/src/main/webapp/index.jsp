<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<%-- Author: Alexander Shvets --%>
<%-- email: shvets_alexander@yahoo.com --%>

<%@ include file="/templates/taglibs.inc.jsp" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="displaytagtest.User"%>

<%
  response.setHeader("Expires", "Sat, 6 May 1095 12:00:00 GMT");
  response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
  response.addHeader("Cache-Control", "post-check=0, pre-check=0");
  response.setHeader("Pragma", "no-cache");
%>

<%
  List users = new ArrayList();

  users.add(new User("01", "fn01", "ln01", "em01"));
  users.add(new User("02", "fn02", "ln02", "em02"));
  users.add(new User("03", "fn03", "ln03", "em03"));
  users.add(new User("04", "fn04", "ln04", "em04"));
  users.add(new User("05", "fn05", "ln05", "em05"));
  users.add(new User("06", "fn06", "ln06", "em06"));
  users.add(new User("07", "fn07", "ln07", "em07"));
  users.add(new User("08", "fn08", "ln08", "em08"));
  users.add(new User("09", "fn09", "ln09", "em09"));
  users.add(new User("10", "fn10", "ln10", "em10"));
  users.add(new User("11", "fn11", "ln11", "em11"));
  users.add(new User("12", "fn12", "ln12", "em12"));

  request.setAttribute("users", users);

// sortable="false" sort="page"
%>

<head>
	<title>The &lt;display:*&gt; tag library</title>
	<meta http-equiv="Expires" content="-1" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen, print" />
</head>

<body>

  <div id="body">
    <display:table name="users" id="user" export="true" sort="list" pagesize="4">
      <display:column property="userId" title="User Id" sortable="true"/>
      <display:column property="firstName" title="First Name" sortable="true"/>
      <display:column property="lastName" title="Last Name" sortable="true"/>
      <display:column property="email" title="email" sortable="true"/>

      <display:caption>This is the table caption</display:caption>

      <display:footer>
        <tr align="center">
          <td colspan="4">footer</td>
        <tr>
      </display:footer>
    </display:table>
  </div>

</body>

</html>

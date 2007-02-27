<%@ include file="/taglibs1-inc.jsp" %>

<%@ page import="dao.ComboboxCollectionManager" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Create/Update User Entry</title></head>

<body>

<%
  application.setAttribute("comboboxCollectionManager", new ComboboxCollectionManager());
%>

  <html:form action="/struts1/user" method="post">
    <html:hidden property="id" />

    Title: <html:text property="title" />
    <br/>

    First Name: <html:text property="firstName"/>
    <br/>

    Last Name: <html:text property="lastName"/>
    <br/>

    Admin?:
    <html:select property="admin">
      <html:optionsCollection name="comboboxCollectionManager" property="booleanList"/>
    </html:select>
    <br/>

    <html:submit value="save" property="command"/>
  </html:form>
</body>

</html>
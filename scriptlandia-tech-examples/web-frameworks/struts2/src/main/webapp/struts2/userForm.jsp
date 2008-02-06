<%@ include file="/taglibs2-inc.jsp" %>

<%@ page import="dao.ComboboxCollectionManager" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Create/Update User Entry</title></head>

<body>

<%
  application.setAttribute("comboboxCollectionManager", new ComboboxCollectionManager());
%>

  <s:form name="user" action="save" method="post">
    <s:hidden name="id" />

    <s:textfield name="title" label="Title:"/>
    <br/>

    <s:textfield name="firstName" label="First Name:"/>
    <br/>

    <s:textfield name="lastName" label="Last Name: "/>
    <br/>

<%--
    <s:select name="admin" list="booleanList" label="Admin?:" >
    </s:select>
    
    <html:select property="admin">
      <html:optionsCollection name="comboboxCollectionManager" property="booleanList"/>
    </html:select>
--%>    
    <br/>

    <s:submit value="save"/>
  </s:form>
</body>

</html>

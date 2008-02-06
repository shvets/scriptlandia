<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<s:form action="login">
  <s:textfield label="User Name" name="username"/>
  <s:password label="Password" name="password" />
  <s:submit/>
</s:form>
</body>
</html>


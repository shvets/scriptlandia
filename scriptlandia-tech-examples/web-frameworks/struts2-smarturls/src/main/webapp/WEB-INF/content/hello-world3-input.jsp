<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
 <body>
  <p>
   What would you like to say to the world?
  </p>
  <s:form action="hello-world3">
    <s:textfield label="Greeting" name="greeting" />
    <s:submit />
  </s:form>
 </body>
</html>

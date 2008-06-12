<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
  <title>Examples</title>
</head>

<body>

  <table>
    <tr>
      <td>
        <a href="<c:url value='test1.spring'/>">Using Controller Interface <!--and ControllerClassNameHandlerMapping--></a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test2.spring'/>">Using AbstractController</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test3.spring'/>">Using ParametrizableViewController</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='testTiles.spring'/>">Using Tiles</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test4.spring'/>">Using Download</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test5.spring'/>">Using Simple Form Controller/Validator/Custom Editor</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test6.spring'/>">Using MultiActionController</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test7.spring'/>">Using WizardFormController</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test8.spring'/>">Using Velocity and XmlViewResolver</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test9.spring'/>">Using Struts</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test11.spring'/>">Using JDBC</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test12.spring'/>">Generating error in the controller...</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='testStatic.html'/>">Testing Static Page</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test13.spring'/>">Using FreeMarker and BeanNameUrlHandlerMapping;BeanNameViewResolver</a>
      </td>
    </tr>

    <tr>
      <td>
        <a href="<c:url value='test-groovy.spring'/>">Test Groovy Controller</a>
      </td>
    </tr>

  </table>

</body>
</html>
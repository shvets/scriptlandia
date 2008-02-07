<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head>
</head>

<body>
  <table>
    <tr>
      <td>
        <tiles:insertAttribute name="header"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insertAttribute name="content"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insertAttribute name="footer"/>
      </td>
    </tr>
  </table>
</body>

</html>
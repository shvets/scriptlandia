<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head></head>

<body>
  <table>
    <tr>
      <td>
        <tiles:insert attribute="header"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insert attribute="content"/>
      </td>
    </tr>

    <tr>
      <td>
        <tiles:insert attribute="footer"/>
      </td>
    </tr>
  </table>
</body>

</html>
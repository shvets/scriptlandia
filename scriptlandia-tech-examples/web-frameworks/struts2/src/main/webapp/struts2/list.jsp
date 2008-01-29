<%@ include file="/taglibs2-inc.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>List Users</title>
<script language="javascript" src="../js/dojo.js"></script>
<script language="javascript">
   dojo.require("dojo.io.*");
   dojo.require("dojo.event.*");
   dojo.require("dojo.html.*");

  function trMouseOver(bookId) {
   getUserInfo(bookId);
  }

  function trMouseOut(evt) {
   var userDiv = document.getElementById("userInfo");
   userDiv.style.display = "none";
  }

  function getUserInfo(userId) {
   var params = new Array();
   params['id'] = userId;
   var bindArgs = {
    url: "dojoAction.action",
    error: function(type, data, evt){alert("error");},
    mimetype: "text/json",
    content: params
   };
   var req = dojo.io.bind(bindArgs);
   dojo.event.connect(req, "load", this, "populateDiv");
  }

  function populateDiv(type, data, evt) {
   var userDiv = document.getElementById("userInfo");
   if (!data) {
    userDiv.style.display = "none";
   } else {
    userDiv.innerHTML = data.role + ": " + data.admin;
    userDiv.style.display = "";
   }
  }
  </script>

</head>

<body>

  Users:
  <br/>

  <table>
    <c:forEach var="user" items="${requestScope.list}">
      <tr onmouseover='trMouseOver(<c:out value="${user.id}"/>)' onmouseout='trMouseOut(<c:out value="${user.id}"/>)'>
        <td>
        <a href="view.action?id=<c:out value="${user.id}"/> ">
          (<c:out value="${user.id}"/>)
          <c:out value="${user.title}"/>:
          <c:out value="${user.firstName}"/>
          <c:out value="${user.lastName}"/>
          <c:out value="${user.admin}"/>
        </a>
          </td>

        <td>
        (<a href="delete.action?id=<c:out value="${user.id}"/> ">delete</a>)
        </td>
      </tr>
    </c:forEach>
 </table>

  <br>
  
  <div id="userInfo" style="display:none;"></div>

  <a href="create.action">Add a new entry</a>

</body>

</html>

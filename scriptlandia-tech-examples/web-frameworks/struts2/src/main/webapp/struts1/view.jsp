<%@ include file="/taglibs1-inc.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>View User Entry</title></head>

<body>

    Id: <c:out value="${requestScope.userForm.id}"/>
    <br/>

    Title: <c:out value="${requestScope.userForm.title}"/>
    <br/>

    First Name: <c:out value="${requestScope.userForm.firstName}"/>
    <br/>

    Last Name: <c:out value="${requestScope.userForm.lastName}"/>
    <br/>

    Admin?: <c:out value="${requestScope.userForm.admin}"/>
    <br/>

    <a href="user.do?command=list">Back to List</a> |
    <a href="user.do?command=update&id=<c:out value="${requestScope.userForm.id}"/>">Update</a>
</body>

</html>

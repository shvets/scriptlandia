<%@ include file="/taglibs1-inc.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>View User Entry</title></head>

<body>

    Id: <c:out value="${requestScope.user.id}"/>
    <br/>

    Title: <c:out value="${requestScope.user.title}"/>
    <br/>

    First Name: <c:out value="${requestScope.user.firstName}"/>
    <br/>

    Last Name: <c:out value="${requestScope.user.lastName}"/>
    <br/>

    Admin?: <c:out value="${requestScope.user.admin}"/>
    <br/>

    <a href="list.action">Back to List</a> |
    <a href="update.action?id=<c:out value="${requestScope.user.id}"/>">Update</a>
</body>

</html>

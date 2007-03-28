<%@ include file="/templates/taglibs-inc.jsp" %>

<html>

<head><title><fmt:message key="title"/></title></head>

<body>

<%
    java.util.Map model = request.getAttribute("model");

    String msg = (String)model.get("message") + " " + (String)model.get("firstName") + " " + 
                 (String)model.get("lastName");
%>

<%=msg%>
</body>

</html>

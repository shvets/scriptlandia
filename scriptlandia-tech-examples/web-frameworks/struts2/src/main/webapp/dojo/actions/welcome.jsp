<%
    String name = request.getParameter("name");
    if (name != null && !name.trim().equals("")) {
        out.print("Hello " + name + "!");
    } else {
        out.print("You didn't enter a name!");
    }
%>
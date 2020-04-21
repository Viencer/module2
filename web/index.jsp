<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="logic.Action" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<% final String name = request.getParameter("name");
    String answer = request.getParameter("answer");
    String cou = request.getParameter("cou");
    try {
        String user = "";
        if (name != null) {
            user = name;
        }
        Action action = new Action();
        char ch = cou.charAt(0);
        int count = Integer.parseInt("" + ch);
        if (answer != null) {
            action.answers(answer, user, count);
        }

        if (name != null) {
            action.getUser(name);
        }%>
<div>
    <%=Action.getQest(count) %>
    <br>
    <form action="/nn_war_exploded/index2.jsp">
        Answer: <input name="answer" type="text"/>
        <input name="cou" type="submit" value=<%= count + 1 + "ENTER"%>/>
        <input name="name" type="hidden" value=<%=name%>/>
    </form>
    <br>
    <form action="/nn_war_exploded/welcome.jsp">
        END: <input type="submit" value="END SESSION"/>
    </form>
</div>
<%
    } catch (NullPointerException e) {
        response.sendRedirect("/nn_war_exploded/index.jsp?cou=0&name=" + name);
    }%>
</body>
</html>


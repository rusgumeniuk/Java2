<%@ page import="simpleUI.Student" %>
<%@ page import="simpleUI.dao.StudentJdbcDao" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Omman
  Date: 30.10.2018
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Student JSP</title>
  </head>
  <body>
    <h1>Student list:</h1>
    <%! StudentJdbcDao dao = new StudentJdbcDao(); %>
    <% List<Student> students = request.getAttribute("students") != null ? (List<Student>) request.getAttribute("students") : dao.getAll(); %>
    <table>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>birth date</td>
        </tr>
        <% for (Student student : students) {%>
        <tr>
            <td><%=student.getId()%></td>
            <td><%=student.getName()%></td>
            <td><%=student.getBirthDay()%></td>
        </tr>
        <% }%>
    </table>
    <div></div>
  <form action="ss" method="post">
      <div>Write student name</div>
      <input type = "text" name ="name" size ="20"/>
      <div>Write birth date (like Day/Month/Year)</div>
      <input type="date" name="birthDay"/>
      <div/>
      <input type="submit" value="Add"/>
  </form>
  </body>
</html>

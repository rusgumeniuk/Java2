package simpleUI;

import simpleUI.dao.StudentJdbcDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class studentServlet extends HttpServlet {

    StudentJdbcDao dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectToIndex(request, response);
        dao.closeConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String name = request.getParameter("name");
            Date birthDay = Date.valueOf(request.getParameter("birthDay"));

            checkAttributes(name, birthDay);

            dao = new StudentJdbcDao();
            dao.insert(new Student(UUID.randomUUID(), name, birthDay));
            redirectToIndex(request, response);
        }
        catch (IllegalArgumentException ex){
            request.setAttribute("message", ex.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
        finally {
            dao.closeConnection();
        }
    }

    private void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new StudentJdbcDao();
        List<Student> studentList = dao.getAll();
        request.setAttribute("students", studentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    private void checkAttributes(String name, Date birthDay){
        if(name.isEmpty())
            throw new IllegalArgumentException("Name can not be empty");
        if(birthDay.after(new Date(System.currentTimeMillis())))
            throw new IllegalArgumentException("Date can not be after now");
    }
}

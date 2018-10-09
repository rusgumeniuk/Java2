package simpleData.dao;

import simpleData.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class StudentJdbcDao implements StudentDao {

    protected PreparedStatement preparedStatement;
    protected Connection connection;

    public static final String JDBC_Url = "";
    public static final String JDBC_Login = "";
    public static final String JDBC_Password = "";

    private final String INSERT_NEW_STUDENT = "INSERT INTO students(id, name, birthdayDate, groupId) VALUES (?,?,?,?)";
    private final String Get_By_Id = "SELECT * FROM students WHERE id = ?";
    private final String UPDATE_By_Id = "Update students SET name = ?, birthdayDate = ?, groupId = ? WHERE id = ?";
    private final String UPDATE_By_Name = "Update students SET name = ?, birthdayDate = ?, groupId = ? WHERE name = ?";
    private final String DELETE_By_Id = "DELETE FROM students WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM students";

    public StudentJdbcDao()throws SQLException{
        try{
            connection = DriverManager.getConnection(JDBC_Url, JDBC_Login, JDBC_Password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }

    public void closePreparedStatement(){
        try{
            if(preparedStatement != null && !preparedStatement.isClosed()){
                preparedStatement.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Student student) throws SQLException{
        if(connection != null && !connection.isClosed()){
            preparedStatement = connection.prepareStatement(INSERT_NEW_STUDENT);
            preparedStatement.setString(1, student.getId().toString());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setDate(3, (Date) student.getBirthdayDate());
            preparedStatement.setString(4, student.getGroupId());
            preparedStatement.execute();
        }
    }

    @Override
    public Student getById(UUID id)throws SQLException{
        if(connection != null && !connection.isClosed()) {
            preparedStatement = connection.prepareStatement(Get_By_Id);
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString(2);
                java.util.Date birthdayDay = resultSet.getDate(3);
                String groupId = resultSet.getString(4);
                return new Student(id, name, birthdayDay, groupId);
            }
        }
        return null;
    }

    @Override
    public void update(Student student)throws SQLException{
        if(connection != null && !connection.isClosed()){
            preparedStatement = connection.prepareStatement(UPDATE_By_Name);
            preparedStatement.setString(1, student.getId().toString());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setDate(3, (Date) student.getBirthdayDate());
            preparedStatement.setString(4, student.getName());
        }
    }

    @Override
    public void delete(Student student)throws SQLException{
        if(connection != null && !connection.isClosed()){
            preparedStatement = connection.prepareStatement(DELETE_By_Id);
            preparedStatement.setString(1, student.getId().toString());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public ArrayList<Student> getAll()throws SQLException{
        if(connection != null && !connection.isClosed()){
            ArrayList<Student> students = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()){
                students.add(new Student(
                        UUID.fromString(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4)));
            }
            return students;
        }
        return null;
    }
}

package simpleData.dao;

import simpleData.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class StudentJdbcDao implements StudentDao{

    private final String INSERT = "insert into students(id, name, birthDay) values(?,?,?)";
    private final String GET_BY_ID = "select * from students where id = ?";
    private final String UPDATE = "update students set name = ?, birthDay = ? where id = ?";
    private final String DELETE = "delete from students where id = ?";
    private final String SELECT_ALL = "select * from students";
    private final String TRUNCATE = "truncate table students";

    private Connection connection;
    private PreparedStatement preparedStatement;

    public StudentJdbcDao() {
        try{
            connection = getConnection();
        }
        catch (SQLException | IOException exception){
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in =
                     new FileInputStream(
                             new File("resources/database.properties")
                     )
        )
        {
            props.load(in);
        }
        catch(NoSuchFileException ex){
            ex.printStackTrace();
        }
        return DriverManager.getConnection(
                props.getProperty("url"),
                props.getProperty("username"),
                props.getProperty("password")
        );
    }

    @Override
    public void closeConnection() {
        if(isConnected()){
            try{
                connection.close();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void insert(Student model) {
        if(isConnected()){
            try{
                preparedStatement = connection.prepareStatement(INSERT);
                preparedStatement.setString(1,model.getId().toString());
                preparedStatement.setString(2,model.getName());
                preparedStatement.setDate(3, model.getBirthDay());
                preparedStatement.execute();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Student model) {
        if(isConnected()){
            try{
                preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setString(1,model.getName());
                preparedStatement.setDate(2, model.getBirthDay());
                preparedStatement.setString(3, model.getId().toString());
                preparedStatement.executeUpdate();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Student getById(UUID id) {
        if(isConnected()){
            try{
                preparedStatement = connection.prepareStatement(GET_BY_ID);
                preparedStatement.setString(1, id.toString());
                ResultSet result = preparedStatement.executeQuery();
                if(result.next()){
                    String name = result.getString(2);
                    Date birthDay = result.getDate(3);
                    return new Student(id, name, birthDay);
                }
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        if(isConnected()){
            ArrayList<Student> students = new ArrayList<>();
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_ALL);
                while (resultSet.next()){
                    students.add(
                            new Student(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getDate(3)
                            )
                    );
                }
                return students;
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void delete(Student model) {
        if(isConnected()){
            try{
                preparedStatement = connection.prepareStatement(DELETE);
                preparedStatement.setString(1, model.getId().toString());
                preparedStatement.executeUpdate();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void truncate(){
        if(isConnected()) {
            try{
                preparedStatement = connection.prepareStatement(TRUNCATE);
                preparedStatement.executeUpdate();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }

        }
    }

    public boolean isConnected(){
        try{
            return connection != null && !connection.isClosed();
        }
        catch (SQLException ex){
            return false;
        }
    }
}

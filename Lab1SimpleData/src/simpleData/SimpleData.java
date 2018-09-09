package simpleData;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class SimpleData {
    public static void main(String[] args) {
        try (Connection connection = getConnection())
        {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection to Store DB successful!");
            Statement statement = connection.createStatement();

            //String creaTableCommand = "CREATE TABLE products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT)";
            //statement.executeUpdate(creaTableCommand);
            //System.out.println("DB has been updated");

            //int rows = statement.executeUpdate("INSERT Products(ProductName, Price) VALUES ('iPhone X', 76000)," +"('Galaxy S9', 45000), ('Nokia 9', 36000)");
            //System.out.printf("Added %d rows", rows);

            //int rows = statement.executeUpdate("UPDATE Products SET Price = Price - 5000");
            //System.out.printf("Updated %d rows", rows);

            // int rows = statement.executeUpdate("DELETE FROM Products WHERE Id = 3");
            // System.out.printf("%d row(s) deleted", rows);


        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException, IOException{
        Properties props = new Properties();
        try (InputStream in =
                     new FileInputStream(
                             new File("C://Users//Omman//Desktop//Development//Java2//Lab1SimpleData//src//SimpleData//database.properties")
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
}

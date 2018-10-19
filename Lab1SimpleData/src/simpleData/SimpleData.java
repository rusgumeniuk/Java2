package simpleData;
import simpleData.dao.StudentJdbcDao;
import simpleData.dao.StudentNoSqlDao;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class SimpleData {
    public static void main(String[] args) throws IOException, SQLException {
        StudentNoSqlDao dao = new StudentNoSqlDao();
        List<Student> result =  dao.getAll();
        System.out.println(result);
    }
}

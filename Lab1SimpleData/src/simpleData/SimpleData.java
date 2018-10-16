package simpleData;
import simpleData.dao.StudentJdbcDao;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class SimpleData {
    public static void main(String[] args) throws IOException, SQLException {
        StudentJdbcDao dao = new StudentJdbcDao();
        Connection obj = StudentJdbcDao.getConnection();
        System.out.println(obj.isClosed());
    }
}

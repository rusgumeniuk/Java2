package simpleData.dao;

import simpleData.models.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

interface Dao <T extends Student> {
    void insert(T entity) throws SQLException, IOException;
    T getById(UUID id) throws SQLException;
    void update(T entity)  throws SQLException;
    void delete (T entity)  throws SQLException;
    ArrayList<T> getAll() throws SQLException;
}

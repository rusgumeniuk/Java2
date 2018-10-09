package simpleData.dao;

import simpleData.models.Student;
import java.sql.SQLException;

public abstract class DaoFactory<T extends Student> {
    public abstract Dao<T> getDao(DB_Type type) throws SQLException;
}

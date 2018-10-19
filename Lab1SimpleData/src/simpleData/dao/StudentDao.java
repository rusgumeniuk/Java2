package simpleData.dao;

import simpleData.Student;

interface StudentDao extends Dao<Student> {
    void closeConnection();
}

package simpleData.dao;

import simpleData.models.Student;

interface StudentDao extends Dao<Student> {
    void closeConnection();
}

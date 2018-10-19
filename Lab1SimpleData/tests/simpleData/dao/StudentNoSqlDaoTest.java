package simpleData.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simpleData.Student;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentNoSqlDaoTest {

    private StudentNoSqlDao dao;
    private Student firstStudent;
    private Student secondStudent;

    @Before
    public void setUp() {
        Logger.getLogger( "org.mongodb.driver" ).setLevel(Level.SEVERE);
        dao = new StudentNoSqlDao();
        firstStudent = new Student("FIRST");
        secondStudent = new Student("Second");
    }

    @After
    public void tearDown() {
        dao.drop();
        dao.closeConnection();
        firstStudent = null;
        secondStudent = null;
    }

    @Test
    public void drop(){
        dao.insert(firstStudent);
        dao.insert(secondStudent);
        dao.drop();
        Assert.assertEquals(0, dao.getAll().size());
    }

    @Test
    public void insert() {
        dao.insert(firstStudent);
        Assert.assertEquals(firstStudent, dao.getById(firstStudent.getId()));
    }

    @Test
    public void getById() {
        Student testStudent = new Student("FIND ME");
        dao.insert(testStudent);

        Assert.assertEquals(testStudent, dao.getById(testStudent.getId()));
        dao.delete(testStudent);
    }

    @Test
    public void update() {
        dao.insert(firstStudent);
        String newName = "UPDATED NAME";
        firstStudent.setName(newName);

        dao.update(firstStudent);

        Assert.assertEquals(newName, dao.getById(firstStudent.getId()).getName());
    }

    @Test
    public void delete() {
        dao.insert(firstStudent);
        Assert.assertNotNull(dao.getById(firstStudent.getId()));

        dao.delete(firstStudent);
        Assert.assertNull(dao.getById(firstStudent.getId()));
    }

    @Test
    public void getAll() {
        dao.insert(firstStudent);
        dao.insert(secondStudent);
        Assert.assertEquals(2, dao.getAll().size());
    }
}
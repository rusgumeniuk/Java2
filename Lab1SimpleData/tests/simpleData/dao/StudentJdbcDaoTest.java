package simpleData.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simpleData.Student;

public class StudentJdbcDaoTest {

    StudentJdbcDao studentJdbcDao;
    Student firstStudent;
    Student secondStudent;

    @Before
    public void setUp() {
        studentJdbcDao = new StudentJdbcDao();
        firstStudent = new Student("First");
        secondStudent = new Student("Second");
        studentJdbcDao.insert(firstStudent);
        studentJdbcDao.insert(secondStudent);
    }

    @After
    public void tearDown() {
        studentJdbcDao.truncate();
        studentJdbcDao.closeConnection();
        studentJdbcDao = null;
        firstStudent = null;
        secondStudent = null;
    }

    @Test
    public void insert() {
        Student testStudent = new Student("Test Student");
        studentJdbcDao.insert(testStudent);
        Assert.assertEquals(testStudent, studentJdbcDao.getById(testStudent.getId()));
    }

    @Test
    public void update() {
        String newName = "NEW SECOND NAME";
        secondStudent.setName(newName);
        studentJdbcDao.update(secondStudent);

        Assert.assertEquals(newName, studentJdbcDao.getById(secondStudent.getId()).getName());
    }

    @Test
    public void getById() {
        Student expectedStudent = studentJdbcDao.getById(firstStudent.getId());
        Assert.assertEquals(firstStudent, expectedStudent);
    }

    @Test
    public void getAll() {
        Assert.assertTrue( studentJdbcDao.getAll().size() > 1);
    }

    @Test
    public void delete() {
        studentJdbcDao.delete(firstStudent);
        Assert.assertNull(studentJdbcDao.getById(firstStudent.getId()));
        Assert.assertTrue(studentJdbcDao.getAll().size() == 1);
    }

    @Test
    public void truncate(){
        Assert.assertTrue(studentJdbcDao.getAll().size() > 0);

        studentJdbcDao.truncate();

        Assert.assertTrue(studentJdbcDao.getAll().size() == 0);
    }
}
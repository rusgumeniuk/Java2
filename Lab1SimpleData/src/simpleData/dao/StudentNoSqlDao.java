package simpleData.dao;

import com.mongodb.client.*;
import org.bson.Document;
import simpleData.Student;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class StudentNoSqlDao implements StudentDao{

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase = null;
    private final String MONGO_URL = "mongodb://localhost:27017";

    private MongoCollection<Document> students;

    public StudentNoSqlDao() {
        mongoClient = MongoClients.create(MONGO_URL);
        if(mongoClient != null){
            mongoDatabase = mongoClient.getDatabase("studentsdb");
            students = mongoDatabase.getCollection("students");
        }
    }

    @Override
    public void closeConnection(){
            mongoClient.close();
            mongoClient = null;
    }

    public boolean isClosed(){
        return mongoClient == null;
    }

    public void drop() {
        if(mongoClient != null){
            students.drop();
        }
    }

    @Override
    public void insert(Student model) {
        if(model != null){
            students.insertOne(toDocument(model));
        }
    }

    @Override
    public Student getById(UUID id) {
        if(id != null) {
            try{
                return fromDocument(students.find(eq("id", id)).first());
            }
            catch (NullPointerException ex)
            {
                return null;
            }
        }
        return null;
    }

    @Override
    public void update(Student model) {
        if(model != null){
            students.replaceOne(eq("id", model.getId()), toDocument(model));
        }
    }

    @Override
    public void delete(Student model) {
        if(model != null){
            students.deleteOne(eq("id", model.getId()));
        }
    }

    @Override
    public List<Student> getAll() {
        ArrayList<Student> result = new ArrayList<>();
        if(students!= null){
            for (Document document : students.find()) {
                result.add(fromDocument(document));
            }
        }
        return result;
    }

    private Student fromDocument(Document document){
        return new Student(
                (UUID)document.get("id"),
                document.getString("name"),
                new Date(document.getDate("birthDay").getTime())
        );
    }

    private Document toDocument(Student student){
        return new Document()
                .append("id", student.getId())
                .append("name", student.getName())
                .append("birthDay", student.getBirthDay());
    }
}

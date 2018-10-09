package simpleData.dao;

import simpleData.models.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;


import com.mongodb.*;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

public class StudentNoSqlDao implements StudentDao{
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    private MongoCollection<Document> students;

    public static final String NOSQL_MONGODB_URL = "";
    public static final String DB_NAME = "";
    public static final String TABLE_NAME = "";

    public StudentNoSqlDao(){
        mongoClient = MongoClients.create(Dao.NOSQL_MONGODB_URL);
        if(mongoClient != null){
            mongoDatabase = mongoClient.getDatabase(DB_NAME);
            students = mongoDatabase.getCollection(TABLE_NAME);
        }
    }

    @Override
    public Student getById(UUID id){
        if(students != null){
            return fromDocument(students.find(eq("id", id)).first());
        }
        return null;
    }

    @Override
    public void insert(Student student){
        if(student != null){
            students.insertOne(toDocument(student));
        }
    }

    @Override
    public void update(Student student){
        if(students != null){
            students.updateOne(eq("id", student.getId()), toDocument(student));
        }
    }

    @Override
    public void delete(Student student){
        if(students != null){
            students.deleteOne(eq("id", student.getId()));
        }
    }

    @Override
    public ArrayList<Student> getAll() {
        if (students != null) {
            ArrayList<Student> result = new ArrayList<>();
            Iterator iterator = students.find().iterator();
            while(iterator.hasNext()){
                result.add(fromDocument((Document)iterator.next()));
            }
        }
        return null;
    }

    @Override
    public void closeConnection(){
        mongoClient.close();
    }

    public Student fromDocument(Document document){
        return new Student(
                UUID.fromString(document.get("id")),
                (String) document.get("name"),
                (Date) document.get("birthdayDate"),
                (String) document.get("groupId")
                );
    }
    public Document toDocument(Student student){
        Document document = new Document("id", student.getId())
                .append("name", student.getName())
                .append("birthdayDate")
                .append("groupId");
        return document;

    }
}

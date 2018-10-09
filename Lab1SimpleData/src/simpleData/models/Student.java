package simpleData.models;

import java.util.Date;
import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private Date birthdayDate;
    private String groupId;

    public Student(){
        id = UUID.randomUUID();
        name = "unknown";
    }
    public Student(String name){
        this();
        setName(name);
    }
    public Student(String name, Date birthdayDate){
        this(name);
        setBirthdayDate(birthdayDate);
    }
    public Student(String name, Date birthdayDate, String groupId){
        this(name, birthdayDate);
        setGroupId(groupId);
    }
    public Student(UUID id, String name, Date birthdayDate, String groupId){
        this.id = id;
        setName(name);
        setBirthdayDate(birthdayDate);
        setGroupId(groupId);
    }

    public void setName(String name) {
       if(name != null && !name.isEmpty()){
           this.name = name;
       }
    }
    public void setGroupId(String groupId) {
        if(groupId != null && !groupId.isEmpty()){
            this.groupId = groupId;
        }
    }
    public void setBirthdayDate(Date birthdayDate) {
        if(birthdayDate != null && birthdayDate.before(new Date())){
            this.birthdayDate = birthdayDate;
        }
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }
    public String getGroupId() {
        return groupId;
    }
    public String getName() {
        return name;
    }
    public UUID getId() {
        return id;
    }
}

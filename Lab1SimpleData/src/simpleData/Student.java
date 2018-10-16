package simpleData;

import java.sql.Date;
import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private Date birthDay;

    public Student(){
        id = UUID.randomUUID();
        name = "name";
        birthDay = new Date(System.currentTimeMillis());
    }
    public Student(String name) {
        this();
        setName(name);
    }
    public Student(String name, Date birthDay){
        this(name);
        setBirthDay(birthDay);
    }
    public Student(UUID id, String name, Date birthDay){
        setId(id);
        setName(name);
        setBirthDay(birthDay);
    }
    public Student(String id, String name, Date birthDay){
        setId(id);
        setName(name);
        setBirthDay(birthDay);
    }

    public UUID getId() {
        return id;
    }

    private void setId(String id){
        if(id != null && !id.trim().isEmpty() && id.trim().length() == 36){//String.Format
            this.id = UUID.fromString(id);
        }
        else if(this.id == null){
            this.id = UUID.randomUUID();
        }
    }
    private void setId(UUID id){
        if(id != null){
            this.id = id;
        }
        else if(this.id == null){
            this.id = UUID.randomUUID();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null && !name.trim().isEmpty()){
            this.name = name;
        }
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        if(birthDay == null){
            this.birthDay = new Date(System.currentTimeMillis());
        }
        else if (birthDay.before(new Date(System.currentTimeMillis()))){
            this.birthDay = birthDay;
        }
    }

    @Override
    public String toString() {
        return name + " : " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) return false;
        Student compareStudent = (Student) obj;
        return getId() == compareStudent.getId() && getName().equals(compareStudent.getName());
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (getId().hashCode() | getName().hashCode() | getBirthDay().hashCode());
    }
}

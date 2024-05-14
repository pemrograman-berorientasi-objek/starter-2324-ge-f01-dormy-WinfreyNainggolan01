package pbo.f01.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name = "dorms")
public class Dorm {

    @Id
    @Column(name = "name", length = 225, nullable = false)
    private String name;
    @Column(name = "capacity", nullable = false)
    private String capacity;
    @Column(name = "gender", length = 225, nullable = false)
    private String gender;
    @Column(name = "resident", nullable = false)
    private int resident;

    @ManyToMany(mappedBy = "dorms", cascade = CascadeType.ALL)
    private List<Student> students;

    public Dorm() {

    }
    
    public Dorm(String name, String capacity, String gender) {
        this.name = name;
        this.capacity = capacity;
        this.gender = gender;
        this.resident = 0;
    }

    public Dorm(String name, String capacity, String gender, List<Student> students) {
        this.name = name;
        this.capacity = capacity;
        this.gender = gender;
        this.students = students;
        this.resident = students.size();
    }

    public String getName() {
        return name;
    }
    
    public String getCapacity() {
        return capacity;
    }

    public String getGender() {
        return gender;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getResident() {
        return resident;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }


    public void setStudents(List<Student> students) {
        this.students = students;
        this.resident = students.size();
    }

    @Override
    public String toString() {
        return name + "|" + gender + "|"+ capacity + "|" + resident;
    }

}
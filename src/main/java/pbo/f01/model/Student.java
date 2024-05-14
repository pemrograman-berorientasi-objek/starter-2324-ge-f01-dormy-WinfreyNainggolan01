package pbo.f01.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "nim", nullable = false, length = 255)
    private String nim;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "year_of_enrollment", nullable = false, length = 255)
    private String yearOfEnrollment;

    @Column(name = "gender", nullable = false, length = 255)
    private String gender;

    @ManyToMany(targetEntity = Dorm.class, cascade = CascadeType.ALL)
    @JoinTable(name = "student_dorm", joinColumns = @JoinColumn(name = "dorm_nim", referencedColumnName = "nim"), 
    inverseJoinColumns = @JoinColumn(name = "dorm_name", referencedColumnName = "name"))
    private List<Dorm> dorms;

    // No-argument constructor
    public Student() {
        // Empty constructor needed by JPA
    }

    public Student(String nim, String name, String yearOfEnrollment, String gender) {
        this.nim = nim;
        this.name = name;
        this.yearOfEnrollment = yearOfEnrollment;
        this.gender = gender;
    }

    public Student(String nim, String name, String yearOfEnrollment, String gender, List<Dorm> dorms) {
        this.nim = nim;
        this.name = name;
        this.yearOfEnrollment = yearOfEnrollment;
        this.gender = gender;
        this.dorms = dorms;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearOfEnrollment() {
        return yearOfEnrollment;
    }

    public void setYearOfEnrollment(String yearOfEnrollment) {
        this.yearOfEnrollment = yearOfEnrollment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Dorm> getDorms() {
        return dorms;
    }

    public void setDorms(List<Dorm> dorms) {
        this.dorms = dorms;
    }

    @Override
    public String toString() {
        return nim + "|" + name + "|" + yearOfEnrollment;
    }
}

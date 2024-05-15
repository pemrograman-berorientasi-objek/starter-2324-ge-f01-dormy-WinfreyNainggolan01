package pbo.f01.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.*;


public class Executor {

    private EntityManager entityManager;


    public Executor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cleanUpTables() {
        String deleteStudentJpql = "DELETE FROM Student";
        String deleteDormJpql = "DELETE FROM Dorm ";

        entityManager.getTransaction().begin();
        entityManager.createQuery(deleteStudentJpql).executeUpdate();
        entityManager.createQuery(deleteDormJpql).executeUpdate();
        entityManager.getTransaction().commit();

    }

    public void addDorm(String[] data) {
        entityManager.getTransaction().begin();
        Dorm dorm = new Dorm(data[1], data[2], data[3]);
        entityManager.persist(dorm);
        entityManager.getTransaction().commit();
    }

    public void addStudent(String[] data){
        entityManager.getTransaction().begin();
        Student tempStudent;
        if((tempStudent = entityManager.find(Student.class, data[1])) == null){
            Student student = new Student(data[1], data[2], data[3], data[4]);
            entityManager.persist(student);
        }else{
            if(!tempStudent.getNim().equals(data[1])){
                Student student = new Student(data[1], data[2], data[3], data[4]);
                entityManager.persist(student);
            }
        }
        entityManager.getTransaction().commit();
    }

    public void assignStudentToDorm(String[] data) {
        entityManager.getTransaction().begin();
        Student student = entityManager.find(Student.class, data[1]);
        Dorm dorm = entityManager.find(Dorm.class, data[2]);
        if (student != null && dorm != null && student.getGender().equals(dorm.getGender()) && Integer.parseInt(dorm.getCapacity()) > dorm.getResident()) {
            student.getDorms().add(dorm);
            dorm.getStudents().add(student);
            dorm.setResident(dorm.getResident() + 1);
            entityManager.persist(student);
            entityManager.persist(dorm);
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }
    }


    public void displayAllDorms() {
        String dormSql = "SELECT g FROM Dorm g ORDER BY g.name";

        List<Dorm> dorms = entityManager.createQuery(dormSql, Dorm.class).getResultList();
        for (Dorm dorm : dorms) {
            System.out.println(dorm);
            
            List<Student> students = dorm.getStudents();
            Collections.sort(students, Comparator.comparing(Student::getName));

            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

}

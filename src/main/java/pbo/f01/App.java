package pbo.f01;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * 12S22001 - Winfrey Nainggolan
 * 12S22012 - Reinhard Batubara
 */

import javax.persistence.*;

import pbo.f01.model.Dorm;
import pbo.f01.model.Student;

public class App {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] _args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("dormy_pu");
        entityManager = entityManagerFactory.createEntityManager();

        cleanUpTables();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String input = sc.nextLine();
            if(input.equals("---")){
                break;
            }else{
                String data[] = input.split("#");
                switch (data[0]){
                    case "dorm-add":
                        addDorm(data);
                        break;
                    case "student-add":
                        addStudent(data);
                        break;
                    case "assign":
                        assignStudentToDorm(data);
                        break;
                    case "display-all":
                        displayAllDorms();
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            }
        }
        sc.close();
        entityManager.close();
        entityManagerFactory.close();
    }

    private static void cleanUpTables() {
        String deleteStudentJpql = "DELETE FROM Student";
        String deleteDormJpql = "DELETE FROM Dorm ";

        entityManager.getTransaction().begin();
        entityManager.createQuery(deleteStudentJpql).executeUpdate();
        entityManager.createQuery(deleteDormJpql).executeUpdate();
        entityManager.getTransaction().commit();

    }

    private static void addDorm(String[] data) {
        entityManager.getTransaction().begin();
        Dorm dorm = new Dorm(data[1], data[2], data[3]);
        entityManager.persist(dorm);
        entityManager.getTransaction().commit();
    }

    private static void addStudent(String[] data){
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

    private static void assignStudentToDorm(String[] data) {
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


    private static void displayAllDorms() {
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
package pbo.f01;

import java.util.Scanner;

/**
 * 12S22001 - Winfrey Nainggolan
 * 12S22012 - Reinhard Batubara
 */

import javax.persistence.*;
import pbo.f01.model.Executor;

public class App {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] _args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("dormy_pu");
        entityManager = entityManagerFactory.createEntityManager();
        
        Executor executor = new Executor(entityManager);
        executor.cleanUpTables();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String input = sc.nextLine();
            if(input.equals("---")){
                break;
            }else{
                String data[] = input.split("#");
                switch (data[0]){
                    case "dorm-add":
                        executor.addDorm(data);
                        break;
                    case "student-add":
                        executor.addStudent(data);
                        break;
                    case "assign":
                        executor.assignStudentToDorm(data);
                        break;
                    case "display-all":
                        executor.displayAllDorms();
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
}
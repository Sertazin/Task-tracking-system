import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC"); // Подключение драйвера JDBC
        Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\SystemTaskDB.sqlite"); /*Создание
        подключения к БД */


        try {
            DataReader dr = new DataReader(conn);
            dr.readData();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command: \n USERS - access to users \n" +
                    "PROJECTS - access to projects \n TASKS - access to tasks \n ASSIGNING - assigning matches \n " +
                    "REPORT - generate report \n EXIT - exit");
            String command = s.next();

            switch (command) {

                case "USERS":
                    UserOperations uo = new UserOperations(s, conn);
                    uo.run();
                    break;
                case "PROJECTS":
                    ProjectOperations po = new ProjectOperations(s, conn);
                    po.run();
                    break;
                case "TASKS":
                    TaskOperations to = new TaskOperations(s, conn);
                    to.run();
                    break;
                case "ASSIGNING":
                    Assigning a = new Assigning(s, conn);
                    a.run();
                    break;
                case "REPORT":
                    Report r = new Report(s,conn);
                    r.run();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}






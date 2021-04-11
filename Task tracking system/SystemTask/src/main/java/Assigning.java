import java.sql.*;
import java.util.Scanner;

public class Assigning {
    private Scanner scanner;
    private Connection conn;

    public Assigning(Scanner scanner, Connection conn) {
        this.scanner = scanner;
        this.conn = conn;
    }

    public void run() {
        while (true) {
            System.out.println("ASSIGN_USER - assign user to project \n ASSIGN_TASK - assign task to user" +
                    " \n EXIT - go back");
            String command = scanner.next();

            switch (command) {
                case "ASSIGN_USER":
                    this.assignUserToProject();
                    break;
                case "ASSIGN_TASK":
                    this.assignTaskToUser();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private void assignTaskToUser() {                      // метод для назначения задачи пользователю
        System.out.println("Enter task_id:");
        int taskId = scanner.nextInt();
        System.out.println("Enter user_id:");
        int userId = scanner.nextInt();
        try {
            if (!(this.taskExists(taskId))) {
                System.out.println("This task doesnt exists \n");
                return;
            }
            if (!(this.userExists(userId))) {
                System.out.println("This user doesnt exists \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE Tasks set user_id=" + userId + " where id=" + taskId);
            stmt.close();
            System.out.println("Appointment successfully \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean taskExists(int taskId) throws SQLException {   // проверка на существование задачи
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks where id =" + taskId)) {
                if (rs.next()) {
                    return true;
                } else
                    return false;
            }
        }
    }


    private void assignUserToProject() {           // метод для назначения пользователя проекту
        System.out.println("Enter project_id:");
        int projectId = scanner.nextInt();
        System.out.println("Enter user_id:");
        int userId = scanner.nextInt();
        try {
            if (!(this.projectExists(projectId))) {
                System.out.println("This project doesnt exists \n");
                return;
            }
            if (!(this.userExists(userId))) {
                System.out.println("This user doesnt exists \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE Projects set user_id=" + userId + " where id=" + projectId);
            stmt.close();
            System.out.println("Appointment successfully \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean userExists(int userId) throws SQLException {    // проверка на существование пользователя
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE id = " + userId)) {
                if (rs.next()) {
                    return true;
                } else
                    return false;
            }
        }
    }

    private boolean projectExists(int projectId) throws SQLException {  // проверка на существование проекта
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Projects where id=" + projectId)) {
                if (rs.next()) {
                    return true;
                } else
                    return false;

            }
        }
    }
}


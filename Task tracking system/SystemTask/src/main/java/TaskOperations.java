import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TaskOperations {
    private Scanner scanner;
    private Connection conn;

    public TaskOperations(Scanner scanner, Connection conn) {
        this.scanner = scanner;
        this.conn = conn;
    }

    public void run() {
        while (true) {
            System.out.println("CREATE - create a new task \n SHOW - show task \n DELETE - delete task " +
                    "\n EXIT - go back");
            String command = scanner.next();

            switch (command) {
                case "CREATE":
                    this.createTask();
                    break;
                case "SHOW":
                    this.showTask();
                    break;
                case "DELETE":
                    this.deleteTask();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private void deleteTask() {
        System.out.println("Enter task_name:");
        String taskName = scanner.next();

        try {
            if (!(this.taskNotExists(taskName))) {
                System.out.println("This task doesnt exists \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from Tasks where  name = '" + taskName + "'");
            stmt.close();
            System.out.println("Task deleted successfully \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean taskNotExists(String taskName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select * FROM Tasks WHERE name = '" + taskName + "'")) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }
    }

    private void showTask() {
        System.out.println("Enter task_id:");
        int taskId = scanner.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * FROm Tasks where id = " + taskId);

            if (rs.next() == false) {
                System.out.println("This task is not found \n");
                showTask();
            } else {
                do {
                    System.out.println("task_id: " + rs.getInt("id"));
                    System.out.println("task_name: " + rs.getString("name") + "\n");
                }
                while (rs.next());
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTask() {
        System.out.print("Enter task_name:");
        String taskName = Utilites.nextLine(scanner);
        System.out.println("Enter project_id:");
        int projectId = scanner.nextInt();
        try {
            if (!(this.projectExists(projectId))) {
                System.out.println("This project doesnt exists");
                return;
            }
             if (this.taskExists(taskName)) {
                System.out.println("Such task has already been created \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("insert INTO Tasks (name, project_id) values ('%s',%d)",
                    taskName, projectId));
            stmt.close();
            System.out.println("Task created successfully \n");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private boolean projectExists(int projectId) throws SQLException {
        try (Statement stmt = conn.createStatement()){
            try (ResultSet rs = stmt.executeQuery(String.format("Select * From Projects Where id = %d", projectId))){
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }

    }

    private boolean taskExists(String taskName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks WHERE name='" + taskName + "'")) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }
    }
}

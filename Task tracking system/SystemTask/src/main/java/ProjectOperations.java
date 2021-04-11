import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProjectOperations {

    private Scanner scanner;
    private Connection conn;

    public ProjectOperations(Scanner scanner, Connection conn) {
        this.scanner = scanner;
        this.conn = conn;
    }

    public void run() {
        while (true) {
            System.out.println("CREATE - create a new project \n SHOW - show project \n DELETE - delete project " +
                    "\n EXIT - go back");
            String command = scanner.next();

            switch (command) {
                case "CREATE":
                    this.createProject();
                    break;
                case "SHOW":
                    this.showProject();
                    break;
                case "DELETE":
                    this.deleteProject();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }


    private void deleteProject() {                             //метод для удаления проекта из "Projects"
        System.out.println("Enter project_name:");
        String projectName = scanner.next();

        try {
            if (!(this.projectNotExist(projectName))) {
                System.out.println("This project doesnt exists \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from Projects where  name = '" + projectName + "'");
            stmt.close();
            System.out.println("Project deleted successfully \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean projectNotExist(String projectName) throws SQLException {    //проверка на существование проекта
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select * FROM Projects WHERE name = '" + projectName + "'")) {
            if (rs.next()) {
                return true;
            }
            return false;
        }

    }
    }
    private void showProject() {                              //метод для отображения проекта из "Projects"
        System.out.println("Enter project_id:");
        int projectId = scanner.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * FROM Projects where id = " + projectId);

            if (rs.next() == false) {
                System.out.println("This project is not found \n");
                showProject();
            } else {
                do {
                    System.out.println("project_id: " + rs.getInt("id"));
                    System.out.println("project_name: " + rs.getString("name") + "\n");
                }
                while (rs.next());
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createProject() {                              //метод для создания проекта в "Projects"
        System.out.println("Enter project_name:");
        String projectName = scanner.next();

        try {
            if (this.projectExists(projectName)) {
                System.out.println("Such project has already been created \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert INTO Projects (name) values ('" + projectName + "')");
            stmt.close();
            System.out.println("Project created successfully \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean projectExists(String projectName) throws SQLException {        //проверка на существование проекта
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Projects WHERE name='" + projectName + "'")) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }
    }
}
import java.sql.*;
import java.util.Scanner;

public class UserOperations {
    private Scanner scanner;
    private Connection conn;

    public UserOperations(Scanner scanner, Connection conn) {
        this.scanner = scanner;
        this.conn = conn;
    }

    public void run() {
        while (true) {
            System.out.println("CREATE - create a new user \n SHOW - show user \n DELETE - delete user " +
                    "\n EXIT - go back");
            String command = scanner.next();

            switch (command) {
                case "CREATE":
                    this.createUser();
                    break;
                case "SHOW":
                    this.showUser();
                    break;
                case "DELETE":
                    this.deleteUser();
                    break;
                case "EXIT":
                    return;
                default:
                    System.out.println("Unknown command");
            }

        }

    }

    private void deleteUser() {
        System.out.println("Enter user_name:");
        String userName = scanner.next();

        try {
            if (!(this.userNotExists(userName))) {
                System.out.println("This user doesnt exists \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE from Users where  name = '" + userName + "'");
            stmt.close();
            System.out.println("User deleted successfully \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean userNotExists(String userName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select * FROM Users WHERE name = '" + userName + "'")) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }
    }

    private void showUser() {
        System.out.println("Enter user_id:");
        int userId = scanner.nextInt();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * FROm Users where id = " + userId);

            if (rs.next() == false) {
                System.out.println("This user is not found \n");
                showUser();
            } else {
                do {
                    System.out.println("user_id: " + rs.getInt("id"));
                    System.out.println("user_name: " + rs.getString("name") + "\n");
                }
                while (rs.next());
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createUser() {
        System.out.print("Enter user_name:");
        String userName = scanner.next();

        try {
            if (this.userExists(userName)) {
                System.out.println("Such user has already been created \n");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert INTO Users (name) values ('" + userName + "')");
            stmt.close();
            System.out.println("User created successfully \n");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private boolean userExists(String userName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE name='" + userName + "'")) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }
    }
}

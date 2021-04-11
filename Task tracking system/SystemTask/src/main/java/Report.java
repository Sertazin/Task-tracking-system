import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Report {
    private Scanner scanner;
    private Connection conn;

    public Report(Scanner scanner, Connection conn) {
        this.scanner = scanner;
        this.conn = conn;
    }

    public void run() {
        System.out.println("Enter user_id:");
        int userId = scanner.nextInt();
        System.out.println("Enter project_ids");
        String projectIdString = Utilites.nextLine(scanner);
        String[] projectIdList = projectIdString.split(",");
        int[] projectIdIntList = new int[projectIdList.length];
        for (int i = 0; i < projectIdList.length; i++) {
            projectIdIntList[i] = Integer.parseInt(projectIdList[i]);
        }
        try {
            for (int i = 0; i < projectIdIntList.length; i++) {
                if (!(this.projectExist(projectIdIntList[i]))) {
                    System.out.println("Project " + projectIdIntList[i] + " doesnt exists");
                    return;
                }
                if (!(this.userExist(userId))) {
                    System.out.println("This user doesnt exists");
                    return;
                }
            }
            String query = "Select t.id,t.name as taskName,p.name as projectName from TASKS t INNER JOIN projects p ON t.project_id=p.id " +
                    "where t.user_id=" + userId + " AND t.project_id IN (" + String.join(",", projectIdList) + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(String.format("%d-%s-%s", rs.getInt("id"),
                        rs.getString("taskName"), rs.getString("projectName")));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean userExist(int userId) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM Users WHERE id = %d", userId))) {
                if (rs.next()) {
                    return true;
                } else
                    return false;
            }
        }
    }

    private boolean projectExist(int projectId) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("Select * FROM Projects where id=" + projectId)) {
                if (rs.next()) {
                    return true;
                } else
                    return false;
            }
        }
    }
}



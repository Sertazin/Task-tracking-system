import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataReader {
    private Connection conn;

    public DataReader(Connection conn){
        this.conn = conn;
    }

    public void readData() throws IOException, SQLException {
        clearData();    // метод для очистки данных в БД
        readUsers();    // метод для добавления данных в  таблицу "Users"
        readProjects(); // метод для добавления данных в  таблицу "Projects"
        readTasks();    // метод для добавления данных в  таблицу "Tasks"
    }

    private void clearData() throws SQLException {
        executeQuery("Delete From Tasks");
        executeQuery("Delete FRom Projects");
        executeQuery("Delete From Users");
    }

    private void readTasks() throws IOException, SQLException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(
                "C:\\Users\\User\\IdeaProjects\\SystemTask\\Data\\main_Tasks.tsv"));
        String line = reader.readLine();
        while (line != null) {
            String[] parts = line.split("\t",-1);
            String query = String.format ("Insert into Tasks (id,name, project_id, user_id) VALUES " +
                    "(%s, '%s', %s, %s)", parts[0], parts[1], stringOrNull(parts[2]),stringOrNull(parts[3]));
            executeQuery(query);
            line = reader.readLine();
        }
        reader.close();
    }

    private void readProjects() throws IOException, SQLException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(
                "C:\\Users\\User\\IdeaProjects\\SystemTask\\Data\\main_Projects.tsv"));
        String line = reader.readLine();
        while (line != null) {
            String[] parts = line.split("\t",-1);

            String query = String.format ("Insert into Projects (id,name,description, user_id) VALUES (%s, '%s', '%s', %s)",
                    parts[0], parts[1], parts[2], stringOrNull(parts[3]));
            executeQuery(query);
            line = reader.readLine();
        }
        reader.close();
    }

    private void readUsers() throws IOException, SQLException {
        BufferedReader reader;
            reader = new BufferedReader(new FileReader(
                    "C:\\Users\\User\\IdeaProjects\\SystemTask\\Data\\main_Users.tsv"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("\t");
                String query = String.format ("Insert into Users (id,name) VALUES (%s, '%s')",
                        parts[0], parts[1]);
                executeQuery(query);
                line = reader.readLine();
            }
            reader.close();
    }

    private void executeQuery(String query) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        }
    }
    private String stringOrNull(String string){
        if (!(string.isEmpty())){
            return string;
        }  return "NULL";
    }
}

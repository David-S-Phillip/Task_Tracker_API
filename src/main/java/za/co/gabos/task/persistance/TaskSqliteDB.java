package za.co.gabos.task.persistance;

import za.co.gabos.task.Task;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskSqliteDB implements TaskDB {

    private final String dbUrl;

    public TaskSqliteDB(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public void init() {
        try (InputStream is = getClass().getResourceAsStream("/schema.sql")) {
            if (is == null) {
                throw new RuntimeException("schema.sql not found in resources!");
            }
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            try (Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, title, description, is_completed FROM tasks";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task task = new Task(rs.getString("title"), rs.getString("description"));
                task.setId(rs.getInt("id"));
                task.setCompleted(rs.getBoolean("is_completed"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT id, title, description, is_completed FROM tasks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Task task = new Task(rs.getString("title"), rs.getString("description"));
                    task.setId(rs.getInt("id"));
                    task.setCompleted(rs.getBoolean("is_completed"));
                    return task;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // this method resets and deletes the task table, this is for testing
    public void clearDatabase() {
        String sql = "DROP TABLE IF EXISTS tasks";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear database", e);
        }
    }

    @Override
    public Task addTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, is_completed) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());

            stmt.executeUpdate();

            // Retrieve auto-generated SQLite primary key
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

}
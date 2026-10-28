package za.co.gabos.task;

import io.javalin.Javalin;
import za.co.gabos.task.persistance.TaskDB;
import za.co.gabos.task.persistance.TaskSqliteDB;

public class App {

    private static final String DB_URL = "jdbc:sqlite:task_tracker.db";

    public static void main(String[] args){
        // 1. Initialize SQLite Database & Table directly here
        TaskDB db = new TaskSqliteDB(DB_URL);
        db.init(); // Executes CREATE TABLE IF NOT EXISTS

        // Optional: Add a sample task so GET /tasks isn't returning an empty array []
        if (db.getAllTasks().isEmpty()) {
            db.addTask(new Task("Test Javalin", "Verify JSON output from GET request"));
        }
        db.addTask(new Task("bruv","no_bruv"));

        // 2. Pass DB into Service
        TaskService taskService = new TaskService(db);

        // 4. Create & Start Javalin Server
        Javalin app = Javalin.create().start(7070);

        // 5. Register HTTP GET Endpoint
        app.get("/tasks", ctx -> {
            ctx.json(taskService.fetchAllTasks());
        });

        System.out.println("Server running on http://localhost:7070");
    }
}


package za.co.gabos.task;

import io.javalin.Javalin;
import za.co.gabos.task.persistance.TaskDB;
import za.co.gabos.task.persistance.TaskSqliteDB;
import za.co.gabos.task.server.TaskController;

public class App {

    private static final String DB_URL = "jdbc:sqlite:task_tracker.db";


    public static void main(String[] args) {
        // 1. Initialize SQLite Database & Table directly here
        TaskDB db = new TaskSqliteDB(DB_URL);
        db.init(); // Executes CREATE TABLE IF NOT EXISTS

        // Optional: Add a sample task so GET /tasks isn't returning an empty array []
        if (db.getAllTasks().isEmpty()) {
            db.addTask(new Task("Test Javalin", "Verify JSON output from GET request"));
        }

        TaskService taskService = new TaskService(db);

        TaskController controller = new TaskController(taskService);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(7070);
//        Javalin app = Javalin.create().start(7070);

        app.get("/tasks", controller::getAllTasks);
        app.get("/task/{id}", controller::getTaskById);
        app.post("/tasks", controller::createTask);

        System.out.println("Server running on http://localhost:7070/tasks");
    }

}


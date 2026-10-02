package za.co.gabos.task;

import za.co.gabos.task.Task;
import za.co.gabos.task.persistance.TaskSqliteDB;
import java.util.List;

public class TaskService {
    // 1. Declare as the INTERFACE, not the concrete SQLite class
    private final TaskSqliteDB taskDAO;

    // 2. Inject via constructor
    public TaskService(TaskSqliteDB taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> fetchAllTasks() {
        return taskDAO.getAllTasks();
    }

    public Task createNewTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return taskDAO.addTask(task);
    }
}
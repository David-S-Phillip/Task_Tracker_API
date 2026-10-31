package za.co.gabos.task;

import za.co.gabos.task.Task;
import za.co.gabos.task.persistance.TaskDB;
import za.co.gabos.task.persistance.TaskSqliteDB;
import java.util.List;

public class TaskService {
    // 1. Declare as the INTERFACE, not the concrete SQLite class
    private final TaskDB taskDAO;

    // 2. Inject via constructor
    public TaskService(TaskDB taskDAO) {
        this.taskDAO = taskDAO;    }

    public List<Task> fetchAllTasks() {
        return taskDAO.getAllTasks();
    }

    public Task createNewTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (!task.getTitle().matches("^[a-zA-Z0-9 ]+$")){
            throw new IllegalArgumentException("Title contains illegal special characters");
        }
        return taskDAO.addTask(task);
    }

    public Task getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }

}
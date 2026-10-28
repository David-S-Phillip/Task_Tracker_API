package za.co.gabos.task.persistance;

import za.co.gabos.task.Task;

import java.util.List;

public interface TaskDB {
    /**
     * Initialized the SQLite table if it does not exist.
     */
    void init();

    /**
     * this method is meant to clear any entries in the database i had to add it in the interface bc when i called the method on the instance it didnt recognize the method.
     */
    void clearDatabase();

    /**
     * Fetches all tasks currently stored in SQLite
     */
    List<Task> getAllTasks();

    /**
     * Fetches a single task by its primary key ID
     */
    Task getTaskById(int id);

    /**
     * Inserts a new task and returns the task populated with its ID
     */
    Task addTask(Task task);

}

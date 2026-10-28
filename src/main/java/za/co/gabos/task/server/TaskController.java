package za.co.gabos.task.server;

import io.javalin.http.Context;
import za.co.gabos.task.Task;
import za.co.gabos.task.TaskService;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    public void getAllTasks(Context ctx){
        ctx.json(taskService.fetchAllTasks());
    }

    public void getTaskById(Context ctx){
        int taskId = Integer.parseInt(ctx.pathParam("id"));

        Task task = taskService.getTaskById(taskId);

        if (task != null){
            ctx.json(task);
        }else{
            ctx.status(404).json("Task not found brudda");
        }
    }

    public void createTask(Context ctx){
        Task incomingTask = ctx.bodyAsClass(Task.class);

        Task createdTask = taskService.createNewTask(incomingTask);

        ctx.status(201).json(createdTask);
    }
}

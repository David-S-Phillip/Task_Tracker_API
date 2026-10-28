package za.co.gabos.task.server;

import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.co.gabos.task.Task;
import za.co.gabos.task.TaskService;

import java.util.List;

import static org.mockito.Mockito.*;

public class TaskControllerTest {

    private TaskService mockService;
    private Context mockCtx;
    private TaskController controller;

    @BeforeEach
    public void setUp(){
        mockService = Mockito.mock(TaskService.class);
        mockCtx = Mockito.mock(Context.class);
        controller = new TaskController(mockService);
    }

    @Test
    @DisplayName("getAllTasks should fetch tasks from service and render as JSON")
    void getAllTasks_CallsServiceAndReturnsJson() {
        // Arrange
        List<Task> mockTasks = List.of(
                new Task("Task 1", "Desc 1"),
                new Task("Task 2", "Desc 2")
        );
        when(mockService.fetchAllTasks()).thenReturn(mockTasks);

        // Act
        controller.getAllTasks(mockCtx);

        // Assert
        verify(mockService, times(1)).fetchAllTasks();
        verify(mockCtx, times(1)).json(mockTasks);
    }

    @Test
    @DisplayName("getTaskById should return HTTP 404 when task does not exist")
    void getTaskById_NonExistingTask_Returns404() {
        // Arrange
        when(mockCtx.pathParam("id")).thenReturn("99");
        when(mockService.getTaskById(99)).thenReturn(null);

        // Mock method chaining for ctx.status(404).json(...)
        when(mockCtx.status(404)).thenReturn(mockCtx);

        // Act
        controller.getTaskById(mockCtx);

        // Assert
        verify(mockCtx, times(1)).status(404);
        verify(mockCtx, times(1)).json("Task not found brudda");
    }

    @Test
    @DisplayName("createTask should return HTTP 201 and created task JSON")
    void createTask_ValidTask_Returns201AndTask() {
        // Arrange
        Task incomingTask = new Task("New Task", "Description");
        Task createdTask = new Task("New Task", "Description");
        createdTask.setId(5);

        when(mockCtx.bodyAsClass(Task.class)).thenReturn(incomingTask);
        when(mockService.createNewTask(incomingTask)).thenReturn(createdTask);

        // Mock method chaining for ctx.status(201).json(...)
        when(mockCtx.status(201)).thenReturn(mockCtx);

        // Act
        controller.createTask(mockCtx);

        // Assert
        verify(mockService, times(1)).createNewTask(incomingTask);
        verify(mockCtx, times(1)).status(201);
        verify(mockCtx, times(1)).json(createdTask);
    }


}

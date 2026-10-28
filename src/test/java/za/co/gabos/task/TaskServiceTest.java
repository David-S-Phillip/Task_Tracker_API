package za.co.gabos.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import za.co.gabos.task.persistance.TaskDB;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskDB mockDAO;
    private TaskService taskService;

    @BeforeEach
    public void setUp(){
        mockDAO = Mockito.mock(TaskDB.class);
        taskService = new TaskService(mockDAO);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "TASK@WORK !",
            "DELETE * FROM tasks;",
            "<scriot>alert('xss')</script>",
            "Task #1"
    })
    @DisplayName("createNewTask should throw a exception when the title includes special characters")
    public void createNewTaskWithSpecialChars(String invalidTitle){
        Task taskWithSpecialCharacters = new Task(invalidTitle, "Testing special char rejection");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createNewTask(taskWithSpecialCharacters)
        );

        // Assert
        assertEquals("Title contains illegal special characters", exception.getMessage());

        // Verify mockDAO.addTask() was NEVER invoked
        verify(mockDAO, never()).addTask(any());
    }

    @Test
    @DisplayName("createNewTask should throw IllegalArgumentException when title is blank")
    public void createNewTaskBlankTitle(){
        Task invalidTask = new Task("", "No title provided");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createNewTask(invalidTask)
        );

        assertEquals("Title cannot be empty", exception.getMessage());
        verify(mockDAO,never()).addTask(any());
    }

    @Test
    @DisplayName("createNewTask should delegate to DAO when task is valid")
    void createNewTask_ValidTask_CallsDAO() {
        Task validTask = new Task("Write Unit Tests", "Test TaskService validation logic");
        Task expectedSavedTask = new Task("Write Unit Tests", "Test TaskService validation logic");
        expectedSavedTask.setId(1);

        when(mockDAO.addTask(validTask)).thenReturn(expectedSavedTask);

        Task result = taskService.createNewTask(validTask);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(mockDAO, times(1)).addTask(validTask);
    }
}


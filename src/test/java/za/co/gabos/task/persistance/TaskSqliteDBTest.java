package za.co.gabos.task.persistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.gabos.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskSqliteDBTest {

    //Using Sqlite in memory database URL for testing
    private static final String TEST_DB_URL = "jdbc:sqlite:file:test.db"; //this just means dont make a hardcopy of the database it will live in the ram
    private TaskSqliteDB db;

    @BeforeEach
    void setUp(){
        db = new TaskSqliteDB(TEST_DB_URL);
        db.clearDatabase();
        db.init();
    }

    @Test
    @DisplayName("addTask should insert a task and auto-generate an ID")
    void testAddTask(){
        Task newTask = new Task("Buy groceries", "milk, eggs, bread");
        Task savedTask = db.addTask(newTask);

        assertNotNull(savedTask, "Saved task should not be null");
        assertTrue(savedTask.getId() > 0, "the generated id should be greater than 0");
        assertEquals("Buy groceries", savedTask.getTitle());
        assertEquals("milk, eggs, bread", savedTask.getDescription());
        assertFalse(savedTask.isCompleted(), "New task should default to uncompleted");
    }

    @Test
    @DisplayName("getTaskById should retrieve existing task")
    void testGetTaskById() {
        Task created = db.addTask(new Task("Study Java", "Practice SQLite & JUnit"));

        Task fetched = db.getTaskById(created.getId());

        assertNotNull(fetched);
        assertEquals(created.getId(), fetched.getId());
        assertEquals("Study Java", fetched.getTitle());
    }

    @Test
    @DisplayName("getTaskById should return null for non-existent ID")
    void testGetTaskByIdNotFound() {
        Task task = db.getTaskById(999);
        assertNull(task, "Fetching non-existent ID should return null");
    }

    @Test
    @DisplayName("getAllTasks should return all inserted tasks")
    void testGetAllTasks() {
        db.addTask(new Task("Task 1", "Desc 1"));
        db.addTask(new Task("Task 2", "Desc 2"));

        List<Task> tasks = db.getAllTasks();

        assertEquals(2, tasks.size(), "Should return exactly 2 tasks");
    }
}

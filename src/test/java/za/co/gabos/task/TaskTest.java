package za.co.gabos.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    @DisplayName("Constructor should set title, description, null id, and false completion status")
    void testCustomConstructor() {
        Task task = new Task("Finish API", "Implement Javalin routes");

        assertNull(task.getId(), "ID must explicitly default to null before DB insertion");
        assertEquals("Finish API", task.getTitle());
        assertEquals("Implement Javalin routes", task.getDescription());
        assertFalse(task.isCompleted(), "New tasks should default to incomplete");
    }

    @Test
    @DisplayName("No-args constructor should instantiate a task for Jackson deserialization")
    void testNoArgsConstructor() {
        Task task = new Task();

        assertNotNull(task);
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    @DisplayName("Setters should correctly update all Task fields")
    void testSetters() {
        Task task = new Task("Old Title", "Old Description");

        task.setId(10);
        task.setTitle("Updated Title");
        task.setDescription("Updated Description");
        task.setCompleted(true);

        assertEquals(10, task.getId());
        assertEquals("Updated Title", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertTrue(task.isCompleted());
    }
}
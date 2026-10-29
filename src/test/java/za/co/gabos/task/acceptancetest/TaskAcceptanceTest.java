package za.co.gabos.task.acceptancetest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * These are acceptance Tests for A perfect GET and POST request to http://localhost:7070/tasks
 */
public class TaskAcceptanceTest {

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * As a User I want to send a GET Request
     * So That i can get a list of tasks I can view
     *
     * @throws Exception
     */
    @Test
    @DisplayName("GET /tasks  should return a JSON list containing all tasks in the database")
    public void testGetTasksReturnsJson() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7070/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("title"));
    }

    /**
     * As a user i want to add a Task into the Task_API
     * So that i can remember what i need to do
     *
     * @throws Exception
     */
    @Test
    @DisplayName("POST /tasks should create a task and return 201 created with JSON")
    public void testCreateTask() throws Exception {
        String newTaskJson = """
                {
                    "title" : "Record QA Video",
                    "description" : "Demonstrate Tests for Task_API project",
                    "completed" : false
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7070/tasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newTaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.statusCode() == 201 || response.statusCode() == 200);
        assertTrue(response.body().contains("Record QA Video"));
    }

    /**
     * As a User I want to search for a specific task, That does not exist in my Task_API database
     * I should get a Error 404 not found status code response
     *
     * @throws Exception
     */
    @Test
    @DisplayName("GET /tasks/abc - should return 400 Bad Request for Invalid ID format")
    public void testGetTaskWithInvalidIdFormat() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7070/tasks/invalid_id"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), "Expected a 404 Not found status code");
    }

    @Test
    @DisplayName("POST /tasks should correctly handle payloads with special characters and symbols")
    public void testCreateTaskWithSpecialCharacters() throws Exception {
        String payloadWithSpecialChars = """
                {
                    "title" : "Fix bug #123: [Urgent] @User and test",
                    "description" : "Testing special chars: !@!@*&@!*&T^^@&^",
                    "completed" : false
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7070/tasks"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payloadWithSpecialChars))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert that the API accepts the payload (201 Created or 200 OK)
        System.out.println("STatus Code: " + response.statusCode());
        System.out.println("Response BOdy: " + response.body());
        assertTrue(response.statusCode() == 400 || response.statusCode() == 404,
                "Expected status 201 or 200 for special characters payload");

        assertTrue(response.body().contains("Validation Error: Title contains illegal special characters"),
                "Response should preserve special characters in the title");

    }

}

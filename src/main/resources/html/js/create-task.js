document.getElementById("submit-btn").addEventListener("click", async () => {

    // 1. Extract data from the input fields into variables
    const titleValue = document.getElementById('task-title').value;
    const descValue = document.getElementById('task-desc').value;

    const statusMsg = document.getElementById('status-message');

    // Basic validation check
    if (!titleValue || !descValue) {
        statusMsg.innerText = "Please fill in both fields!";
        return;
    }

    // 2. Package the variables into a JS object
    const newTask = {
        title: titleValue,
        description: descValue
    };

    try {
        // 3. Build and send the HTTP POST request to Javalin
        const response = await fetch('http://localhost:7070/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Tells Javalin that JSON data is coming
            },
            body: JSON.stringify(newTask) // Converts the JS object into a JSON string
        });

        if (response.ok) {
            statusMsg.innerText = "Task created successfully!";
            // Clear input fields
            document.getElementById('task-title').value = '';
            document.getElementById('task-desc').value = '';
        } else {
            statusMsg.innerText = "Failed to create task. Server responded with error.";
        }
    } catch (error) {
        statusMsg.innerText = "Error connecting to Javalin server.";
    }

})
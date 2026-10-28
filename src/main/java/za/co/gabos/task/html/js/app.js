// Fetch tasks from Javalin backend running on port 7070
fetch('http://localhost:7070/tasks')
    .then(response => response.json()) // Parse JSON body
    .then(tasks => {
        const container = document.getElementById('tasks-container');
        container.innerHTML = ''; // Clear "Loading tasks..." text

        // Loop over tasks returned from SQLite and append cards
        tasks.forEach(task => {
            const taskDiv = document.createElement('div');
            taskDiv.className = 'task-card';
            taskDiv.innerHTML = `
                <h3>#${task.id} - ${task.title}</h3>
                <p>${task.description}</p>
            `;
            container.appendChild(taskDiv);
        });
    })
    .catch(error => {
        document.getElementById('tasks-container').innerText =
            'Could not connect to Javalin server. Make sure App.main() is running on port 7070.';
    });
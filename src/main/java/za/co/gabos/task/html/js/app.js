// Marked 'async' so JS knows it will handle non-blocking asynchronous calls inside
async function loadTasks() {
    try {
        const response = await fetch('http://localhost:7070/tasks'); // Pauses line execution until response arrives
        const tasks = await response.json();                         // Pauses until JSON is fully parsed

        const container = document.getElementById('tasks-container');
        container.innerHTML = '';

        tasks.forEach(task => {
            const taskDiv = document.createElement('div');
            taskDiv.className = 'task-card';
            taskDiv.innerHTML = `
                <h3>#${task.id} ${task.title}</h3>
                <p>${task.description}</p>
            `;
            container.appendChild(taskDiv);
        });
    } catch (error) {
        document.getElementById('tasks-container').innerText =
            'Could not connect to Javalin server. Make sure App.main() is running on port 7070.';
    }
}

loadTasks();
async function getData() {
    const url = "http://localhost:7070/tasks"
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        const task = await response.json();

        // Target a <ul> tag in the html
        const taskContainer = document.getElementById("task-list");
        taskContainer.innerHTML= "";

        // loop through each object in the array then add it to the list
        task.forEach(task => {
            const li = document.createElement("li");
            li.textContent = task.title;
            taskContainer.appendChild(li);
        })
    } catch (error) {
        console.error(error.message);
    }
}

const fetchButton = document.getElementById("load-tasks-btn")
fetchButton.addEventListener("click", getData);
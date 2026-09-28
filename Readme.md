# Task Tracker Web API

A lightweight RESTful Web API built to practice layered backend architecture, HTTP routing, JSON serialization, and database persistence.

## Project Purpose
This project serves as a hands-on exercise to demonstrate:
- Handling core HTTP methods (`GET`, `POST`) using Javalin.
- Applying the Data Access Object (DAO) pattern to separate domain logic from persistent storage.
- Automating JSON serialization and deserialization using Jackson.
- Persisting application state using an embedded SQLite database via JDBC.

## Tech Stack
- **Language:** Java 
- **Framework:** Javalin
- **JSON Parser:** Jackson (`jackson-databind`)
- **Database:** SQLite (`sqlite-jdbc`)
- **Build Tool:** Maven

## API Endpoints
| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/tasks` | Retrieves a list of all tasks from the SQLite database |
| `GET` | `/task/{id}` | Retrieves a single task by its unique ID |
| `POST` | `/tasks` | Accepts a JSON payload and creates a new task in SQLite |
# Curl Commands
Use the following commands in terminal, curl is a way to turn your terminal into a client that sends a Http Request to a API endpoint.

## POST Request
sends a Post request that adds a Task to the database.
```bash
curl -i -X POST http://localhost:7070/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Record QA Video",
    "description": "Demonstrate REST API endpoints and acceptance tests",
    "completed": false
  }'
```

## GET Request
sends a Get request, to retrieve all tasks listed in the database 
```bash
curl -s http://localhost:7070/tasks | jq
```



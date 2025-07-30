mvn clean package -DskipTests
# Note that we add the "-DskipTests" flag to avoid errors related to Postgres, which is not running at this pt (only begins running once we run docker compose)

# In your project root (with the JAR already built), run:
docker-compose up --build

# HTTP request: POST to /users:
curl -i -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "______",
    "password": "______", 
    "email": "______"
  }'


# HTTP request: POST to /tasks:
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "_____",
    "description": "_____",
    "dueDate": "_____",
    "priority": "_____",
    "userId": "_____"
  }'

# HTTP request: GET /tasks?userId=


# pgadmin: http://localhost:5050/

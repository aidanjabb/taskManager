mvn clean package -DskipTests
# note that we add the -DskipTests flag to avoid errors related to Postgres, which is not running at this pt (only begins running once we run docker compose)


# In your project root (with the JAR already built), run:
docker-compose up --build

# HTTP request: POST to /users:
curl -i -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username": "______", "password": "______", "email": "______"}'
# note that -i shows response headers; without it, we would simply get the raw response body returned by Spring, ie the created user, w/ no status code or headers shown because curl hides them by default

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

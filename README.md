Before docker compose: 

After you’ve run mvn clean package -DskipTests and the JAR exists in target/, build and run with:
 - note that we add the -DskipTests flag to avoid errors related to Postgres, which is not running at this pt (only begins running once we run docker compose)
# Build Docker image
docker build -t task-manager-api .

# Run the container
docker run -p 8080:8080 task-manager-api

After docker compose:

In your project root (with the JAR already built), run:
docker-compose up --build

# for testing HTTP requests on terminal
curl -i -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username": "______", "password": "______", "email": "______"}'
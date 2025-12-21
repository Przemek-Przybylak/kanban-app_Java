# Kanban App – Java Spring Boot

A simple Kanban board backend built with **Spring Boot**, allowing management of projects and their tasks.  
This API follows REST principles and is fully containerized with **Docker / docker‑compose**, making it easy to run locally or in production‑like environments.

---

## 🚀 Features

✔ Create, read, update, delete **projects**  
✔ Create, read, update, delete **tasks**  
✔ Tasks are associated with projects  
✔ Partial updates with PATCH  
✔ Input validation  
✔ Global exception handling  
✔ API documentation with **Swagger / OpenAPI**  
✔ Unit tests for service layer (JUnit + Mockito)  
✔ Dockerized backend and PostgreSQL database

---

## 📦 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java |
| Framework | Spring Boot |
| Persistence | Spring Data JPA |
| Database | PostgreSQL |
| API Docs | Swagger (OpenAPI) |
| Testing | JUnit5, Mockito |
| Containerization | Docker, docker‑compose |

---

## 📁 Project Structure
````
src/
├── main/
│ ├── java/
│ │ └── com/example/kanban/
│ │ ├── controller # REST endpoints
│ │ ├── service # Business logic
│ │ ├── repository # JPA repositories
│ │ ├── model # Entities
│ │ └── exception # Global exception handling
│ └── resources/
│ └── application.yml
└── test/
└── java/ # Unit tests
````



---

## 📄 API Documentation

All REST endpoints are documented with **Swagger (OpenAPI)**.

After running the application, open a browser and go to:

http://localhost:8080/swagger-ui/index.html

You can explore and test all HTTP endpoints directly from the UI.

---

## 🐳 Running with Docker (recommended)

This project is containerized using Docker:

1. Build and start containers:

```bash
docker compose up --build
The app will be available at:

arduino

http://localhost:8080
PostgreSQL will be running on port 5432.

Docker will launch:

backend (Spring Boot API)

postgres (database)

No local installations needed.

🛠 Environment Variables (used in docker-compose)
Env	Purpose
SPRING_DATASOURCE_URL	Database connection URL
SPRING_DATASOURCE_USERNAME	Database user
SPRING_DATASOURCE_PASSWORD	Database password
POSTGRES_USER	DB user created by Postgres
POSTGRES_PASSWORD	DB password
POSTGRES_DB	Database name

These values are configured inside docker-compose.yml for smooth integration.

🔧 Running Locally
Alternatively, if you want to run the app locally (without Docker):

Configure PostgreSQL on your machine.

Update application.yml with correct DB credentials.

Use Maven:

bash

./mvnw clean spring‑boot:run
🧪 Tests
Unit tests exist for the service layer using Mockito:

bash

./mvnw test
Tests validate behavior of:

retrieving data

business logic

exception flows

💡 Example Endpoints
Method	Endpoint	Description
GET	/projects	List all projects
POST	/projects	Create a project
GET	/projects/{id}	Get project by ID
PATCH	/tasks/{id}	Partial task update
DELETE	/projects/{id}	Delete project

Explore more in Swagger UI.

📝 Notes
✔ Suitable for junior backend portfolios
✔ Containerization simplifies deployment and testing
✔ Designed to be clean and easy to understand

🗂 Next Steps / TODO
Potential improvements:

Frontend + backend full stack deployment

User authentication (JWT)

Integration tests

📬 Contact
If you have questions or feedback, feel free to open an issue or contact me.
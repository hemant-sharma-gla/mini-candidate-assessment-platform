# Assessment System

A Spring Boot application for managing online assessments and quizzes. This system allows administrators to create and
manage questions, while candidates can take assessments and receive immediate feedback.

## Features

- User Authentication using JWT
- Role-based access control (Admin and User roles)
- Question Management
- Candidate Registration and Assessment
- Real-time Score Calculation
- Swagger UI for API Documentation
- PostgreSQL Database with Docker support

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher
- Spring Boot 3.1.12
- Docker and Docker Compose (for containerized deployment)

## Getting Started

### Option 1: Running with Docker (Recommended)

1. Clone the repository:

```bash
git clone https://github.com/user-name/mini-candidate-assessment-platform
cd mini-candidate-assessment-platform
```

2. Start the PostgreSQL container using Docker Compose:

```bash
docker-compose up -d postgres
```

3. Build and run the application using Docker:

```bash
docker-compose up -d app
```

The application will start on `http://localhost:8080`

### Option 2: Running Locally

1. Clone the repository:

```bash
git clone https://github.com/yourusername/assessment-system.git
cd assessment-system
```

2. Start PostgreSQL using Docker:

```bash
docker run --name assessment-postgres -e POSTGRES_DB=assessmentdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15
```

3. Build the project:

```bash
./gradlew clean build
```

4. Run the application:

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI documentation is available at:

```
http://localhost:8080/api-docs
```

## Database

The application uses PostgreSQL as the database. When running with Docker, the database configuration is automatically
handled through Docker Compose.

### Docker Compose Configuration

The `docker-compose.yml` file includes:

- PostgreSQL service with persistent volume
- Application service with proper networking

Default database credentials (when using Docker):

- Database URL: `jdbc:postgresql://postgres:5432/candidate_assessment`
- Username: `postgres`
- Password: `postgres`

### Manual Database Setup (if not using Docker)

If running locally without Docker, configure the database connection in `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/candidate_assessment
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Security

The application uses JWT (JSON Web Token) for authentication. To access protected endpoints:

1. Login using the `/api/auth/login` endpoint
2. Include the received JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Project Structure

```
src/main/java/com/assessment/
├── config/           # Configuration classes
├── controller/       # REST controllers
├── dto/             # Data Transfer Objects
├── exception/       # Custom exceptions
├── model/           # Entity classes
├── repository/      # JPA repositories
├── security/        # Security related classes
└── service/         # Business logic
```

## Dependencies

The project uses Gradle for dependency management. Key dependencies include:

- Spring Boot Web
- Spring Boot Data JPA
- Spring Boot Security
- Spring Boot Validation
- PostgreSQL Driver
- JWT (JSON Web Token)
- OpenAPI/Swagger
- Lombok

All dependencies are managed through the `build.gradle` file.

## Docker Commands

### Useful Docker Commands

```bash
# Start all services
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f

# Rebuild and restart services
docker-compose up -d --build

# Remove volumes (will delete database data)
docker-compose down -v
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 

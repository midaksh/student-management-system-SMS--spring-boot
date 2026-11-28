# Student Management System (SMS) with Spring Boot

A RESTful Student Management System built using **Java, Spring Boot, Spring Data JPA, and MySQL**. This project was developed incrementally through practical experiments, for the Enterprise Java course at IIIT Soneapt.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.2.0
- **Build Tool:** Maven
- **Database:** MySQL
- **ORM:** Hibernate / Spring Data JPA
- **Security:** Spring Security
- **Validation:** Jakarta Bean Validation
- **Testing:** JUnit 5, Spring Boot Test, MockMvc
- **API Testing:** Postman, cURL
- **Version Control:** Git and GitHub

## Project Structure

```text
student-management/
├── src/
│   ├── main/
│   │   ├── java/com/example/ucs067/studentmanagement/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── StudentController.java
│   │   │   │   └── CourseController.java
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java
│   │   │   │   └── CourseRepository.java
│   │   │   ├── Student.java
│   │   │   ├── Course.java
│   │   │   └── StudentManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/ucs067/studentmanagement/
│           └── StudentManagementApplicationTests.java
├── pom.xml
└── README.md
```

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/midaksh/student-management-system-SMS--spring-boot.git
cd student-management
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE student_management;
```

### 3. Configure Environment Variables

The application uses an environment variable for the MySQL password.

In macOS Terminal:

```bash
export DB_PASSWORD='your_mysql_password'
```

### 4. Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.application.name=student-management
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 5. Run the Application

Start the MySQL server, then execute:

```bash
./mvnw spring-boot:run
```

The application will run at:

`http://localhost:8081`

## Experiments Completed

### 1: Spring Boot Project Setup
- Created a Spring Boot project using Maven.
- Understood the project structure and application entry point.

### 2: First REST Endpoint
- Created a REST controller.
- Implemented a basic `/hello` endpoint.
- Understood `@RestController`, `@GetMapping`, and request mappings.

### 3: CRUD Operations Using ArrayList
- Implemented in-memory student management.
- Created GET, POST, PUT, and DELETE endpoints.

### 4: MySQL Integration Using Spring Data JPA
- Connected the Spring Boot application to MySQL.
- Created the `Student` entity.
- Implemented `StudentRepository` using `JpaRepository`.
- Performed persistent CRUD operations.

### 5: Derived Queries, Pagination and Sorting
- Implemented Spring Data JPA derived query methods.
- Added student search functionality.
- Implemented pagination and sorting.

Features:
- Search students by name.
- Search students using partial name matches.
- Search students by email.
- Retrieve paginated student records.
- Retrieve sorted student records.

### 6: Student–Course One-to-Many Relationship
- Implemented a one-to-many relationship between Student and Course.
- Used `@OneToMany` and `@ManyToOne`.
- Prevented duplicate student emails.
- Prevented duplicate course codes for the same student.
- Added database-level unique constraints.

### 7: Input Validation and Global Exception Handling
- Implemented Jakarta Bean Validation.
- Used `@NotBlank` to validate student names and emails.
- Used `@Email` to validate email formats.
- Applied `@Valid` to POST and PUT request bodies.
- Created a centralized exception handler using `@RestControllerAdvice`.
- Handled validation errors using `@ExceptionHandler`.

### 8: REST API Testing and Automated Testing
- Tested invalid request payloads and validation responses.
- Implemented automated tests using `JUnit 5` and `MockMvc`

## API Endpoints

Base URL: `http://localhost:8081`

### Student APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/students` | Retrieve all students |
| POST | `/students` | Create a student |
| GET | `/students/{id}` | Retrieve a student by ID |
| PUT | `/students/{id}` | Update a student |
| DELETE | `/students/{id}` | Delete a student |
| GET | `/students/search/name?name=Midaksh` | Search by exact name |
| GET | `/students/search?keyword=ak` | Search by partial name |
| GET | `/students/search/email?email=midaksh@example.com` | Search by email |
| GET | `/students/paginated?page=0&size=5` | Retrieve paginated students |
| GET | `/students/sorted` | Retrieve sorted students |

### Course APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/courses` | Retrieve all courses |
| GET | `/courses/{id}` | Retrieve a course by ID |
| POST | `/courses/student/{studentId}` | Add a course to a student |
| GET | `/courses/student/{studentId}` | Retrieve courses belonging to a student |
| DELETE | `/courses/{id}` | Delete a course |

### Hello API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/hello` | Returns a welcome message |

## Example API Requests

### Create a Student

**POST** `/students`

```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

Expected response: `201 Created`

### Update a Student

**PUT** `/students/1`

```json
{
  "name": "John Updated",
  "email": "john.updated@example.com"
}
```

Expected response: `200 OK`

### Add a Course to a Student

**POST** `/courses/student/1`

```json
{
  "courseName": "Data Structures and Algorithms",
  "courseCode": "CS201"
}
```

### Example Validation Error

Sending an invalid student payload:

```json
{
  "name": "",
  "email": "invalid-email"
}
```

Expected response: `400 Bad Request`

```json
{
  "name": "Student name is required",
  "email": "Please provide a valid email address"
}
```

## HTTP Status Codes

| Status Code | Meaning | Usage |
|---|---|---|
| 200 | OK | Successful GET or PUT |
| 201 | Created | Student successfully created |
| 204 | No Content | Successful deletion |
| 400 | Bad Request | Invalid request or validation failure |
| 404 | Not Found | Requested resource does not exist |
| 409 | Conflict | Duplicate student email or course code |

## Automated Testing

Run the test suite using:

```bash
./mvnw test
```

For a clean build and test execution:

```bash
./mvnw clean test
```

The current automated tests cover:

- Application context loading.
- Hello endpoint response.
- Invalid student data and validation errors.

## Database Design

The application currently uses two main entities:

**Student**
- `id` – Primary key, auto-generated.
- `name` – Student name.
- `email` – Unique student email.
- `courses` – Collection of associated courses.

**Course**
- `id` – Primary key, auto-generated.
- `courseName` – Course name.
- `courseCode` – Course code.
- `student_id` – Foreign key referencing the student.

Relationship:

```text
Student (1) ─────────── (N) Course
```

Each student can have multiple courses, while each course belongs to one student.

## Current Project Status

- [x] Experiments 1–8 completed
- [x] REST API CRUD operations
- [x] MySQL database integration
- [x] Spring Data JPA
- [x] Derived queries, pagination and sorting
- [x] Student–Course relationship
- [x] Input validation
- [x] Global exception handling
- [x] cURL API testing
- [x] JUnit and MockMvc automated testing
- [ ] Experiment 9: Spring Security Basic Authentication

---

**Author:** Midaksh Pandita  
**GitHub:** [@midaksh](https://github.com/midaksh)

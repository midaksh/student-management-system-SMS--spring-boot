# Student Management System (SMS) – Spring Boot

A RESTful Student Management System built using **Java and Spring Boot**. This project is being developed incrementally as part of the IIIT Sonepat Assignment work, with each experiment introducing new backend development concepts and functionalities.

## Project Overview

The Student Management System provides REST APIs to manage student records and implements the fundamental CRUD (Create, Read, Update, Delete) operations.

The project will progressively evolve to incorporate MySQL database integration, Spring Data JPA, derived queries, entity relationships, validation, exception handling, automated testing, and Spring Security.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language target |
| Spring Boot 4.2.0 | Backend application framework |
| Spring Web MVC | REST API development |
| Spring Security | Application security configuration |
| Maven | Dependency and build management |
| ArrayList | In-memory data storage |
| Git & GitHub | Version control |

## Project Structure

```text
student-management/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/ucs067/studentmanagement/
│   │   │       │
│   │   │       ├── StudentManagementApplication.java
│   │   │       ├── Student.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── HelloController.java
│   │   │       │   └── StudentController.java
│   │   │       │
│   │   │       └── config/
│   │   │           └── SecurityConfig.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

## Implemented Experiments

### Experiment 1 & 2: Initial Spring Boot Project Setup

**Verification Endpoint:**

| Method | Endpoint | Description |
|---|---|---|
| GET | `/hello` | Returns a Hello World response |

**Sample Response:**

```text
Hello, World!
```

### Experiment 3: Student CRUD Operations in-memory

Implemented basic CRUD operations using Java's `ArrayList` as an in-memory data store.

**Student Model**

The Student class contains the following attributes:

| Attribute | Data Type | Description |
|---|---|---|
| id | int | Unique student identifier |
| name | String | Student name |
| email | String | Student email address |

**Initial Student Records**

The application initializes with three student records:

| ID | Name | Email |
|---|---|---|
| 1 | Midaksh | midaksh@example.com |
| 2 | Aahan | aahan@example.com |
| 3 | Azam | azam@example.com |

**REST API Endpoints**

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/students` | Retrieve all students |
| POST | `/students` | Add a new student |
| GET | `/students/{id}` | Retrieve a student by ID |
| PUT | `/students/{id}` | Update an existing student |
| DELETE | `/students/{id}` | Delete a student |

**HTTP Response Codes**

| Status Code | Meaning |
|---|---|
| 200 OK | Request completed successfully |
| 201 Created | Student successfully added |
| 204 No Content | Student successfully deleted |
| 404 Not Found | Student record does not exist |

**Implementation Details**

- Used `@RestController` to define REST API endpoints.
- Used `@RequestMapping` to define the common `/students` URL.
- Used `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping` for CRUD operations.
- Used `@RequestBody` to convert incoming JSON into Java objects.
- Used `@PathVariable` to extract student IDs from request URLs.
- Used `ResponseEntity` to return appropriate HTTP status codes and response bodies.
- Used `ArrayList<Student>` for temporary in-memory storage.

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/midaksh/student-management-system-SMS--spring-boot
```

**2. Navigate to the project directory**

```bash
cd student-management
```

**3. Run the Spring Boot application**

MacOS/Linux:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start at:

```text
http://localhost:8081
```

## API Testing

The following commands can be used to test the implemented endpoints.

**1. Retrieve all students**

```bash
curl -i http://localhost:8081/students
```

**2. Retrieve a student by ID**

```bash
curl -i http://localhost:8081/students/1
```

**3. Add a new student**

```bash
curl -i -X POST http://localhost:8081/students \
-H "Content-Type: application/json" \
-d '{"id":4,"name":"Neha","email":"neha@example.com"}'
```

**4. Update an existing student**

```bash
curl -i -X PUT http://localhost:8081/students/2 \
-H "Content-Type: application/json" \
-d '{"id":2,"name":"Aahan Sharma","email":"aahan.sharma@example.com"}'
```

**5. Delete a student**

```bash
curl -i -X DELETE http://localhost:8081/students/3
```

**6. Retrieve a non-existent student**

```bash
curl -i http://localhost:8081/students/99
```

The expected response is `404 Not Found`.

## MySQL Integration using Spring Data JPA

### Objective
Integrate MySQL with the Student Management System using Spring Data JPA and Hibernate to provide persistent storage for student records.

### Technologies Used
- Hibernate ORM
- MySQL 9.7.4

### Implementation

1. Created a MySQL database named `student_management`.
2. Configured the database connection through `application.properties`.
3. Converted the Student class into a JPA Entity using `@Entity`, `@Table`, `@Id`, and `@GeneratedValue`.
4. Created `StudentRepository` by extending `JpaRepository<Student, Integer>`.
5. Updated `StudentController` to perform CRUD operations through the repository.
6. Used Hibernate to automatically create the `students` table.
7. Configured the database password through an `.env` variable.

## Advanced Spring Data JPA derived queries

### Objective
Implement advanced database operations using Spring Data JPA, including derived query methods, pagination, and sorting.

### Implementation
1. Added derived query methods to `StudentRepository`.
2. Implemented exact name search using `findByName()`.
3. Implemented partial name search using `findByNameContaining()`.
4. Implemented email search using `findByEmail()`.
5. Implemented pagination using `Page`, `Pageable`, and `PageRequest`.
6. Implemented sorting using Spring Data JPA's `Sort` class.

### API Endpoints

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/students/search/name?name=Midaksh` | Search by exact name |
| GET | `/students/search?keyword=ak` | Search by partial name |
| GET | `/students/search/email?email=midaksh@example.com` | Search by email |
| GET | `/students/paginated?page=0&size=5` | Retrieve paginated students |
| GET | `/students/sorted` | Retrieve students sorted by name |

## One-to-Many Relationship Using Spring Data JPA

### Objective
To implement a One-to-Many relationship between Student and Course entities using Spring Data JPA and Hibernate.

The relationship is implemented using:
- `@OneToMany` in the Student entity.
- `@ManyToOne` in the Course entity.
- `@JoinColumn(name = "student_id")` to establish the foreign key relationship.
- `@JsonManagedReference` and `@JsonBackReference` to prevent infinite recursion during JSON serialization.

### API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/courses` | Retrieve all courses |
| GET | `/courses/{id}` | Retrieve a course by ID |
| POST | `/courses/student/{studentId}` | Add a course to a student |
| GET | `/courses/student/{studentId}` | Retrieve courses of a student |
| DELETE | `/courses/{id}` | Delete a course |

### Sample Request

**POST** `/courses/student/1`

```json
{
  "courseName": "Data Structures and Algorithms",
  "courseCode": "CSE201"
}
```

## Future Enhancements

The project will be extended through the following experiments:

- **Experiment 7:** Input validation and global exception handling.
- **Experiment 8:** REST API testing and automated testing using JUnit and MockMvc.
- **Experiment 9:** Authentication and authorization using Spring Security.

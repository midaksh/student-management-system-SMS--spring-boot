
package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Student;
import com.example.ucs067.studentmanagement.repository.StudentRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // GET: Retrieve all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    // POST: Add a new student
    @PostMapping
    public ResponseEntity<?> addStudent(@Valid @RequestBody Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("A student with this email already exists.");
        }

        student.setId(null);

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // GET: Retrieve student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {

        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Update student details
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int id,
            @Valid @RequestBody Student updatedStudent) {

        return studentRepository.findById(id)
                .map(student -> {

                    if (!student.getEmail().equalsIgnoreCase(updatedStudent.getEmail())
                            && studentRepository.existsByEmail(updatedStudent.getEmail())) {

                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body("A student with this email already exists.");
                    }

                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());

                    Student savedStudent = studentRepository.save(student);

                    return ResponseEntity.ok(savedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // GET: Search students by exact name
    @GetMapping("/search/name")
    public ResponseEntity<List<Student>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(studentRepository.findByName(name));
    }

    // GET: Search students by name keyword
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByNameContaining(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                studentRepository.findByNameContaining(keyword)
        );
    }

    // GET: Search students by email
    @GetMapping("/search/email")
    public ResponseEntity<List<Student>> searchByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(studentRepository.findByEmail(email));
    }

    // GET: Retrieve students with pagination
    @GetMapping("/paginated")
    public ResponseEntity<Page<Student>> getPaginatedStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(studentRepository.findAll(pageable));
    }

    // GET: Retrieve students sorted by name
    @GetMapping("/sorted")
    public ResponseEntity<List<Student>> getSortedStudents() {

        return ResponseEntity.ok(
                studentRepository.findAll(
                        Sort.by(Sort.Direction.ASC, "name")
                )
        );
    }
}

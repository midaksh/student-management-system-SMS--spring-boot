
package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Student;
import com.example.ucs067.studentmanagement.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Student> addStudent(
            @RequestBody Student student) {

        student.setId(0);

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // GET: Retrieve student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable int id) {

        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        return studentRepository.findById(id)
                .map(student -> {

                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());

                    Student savedStudent =
                            studentRepository.save(student);

                    return ResponseEntity.ok(savedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable int id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

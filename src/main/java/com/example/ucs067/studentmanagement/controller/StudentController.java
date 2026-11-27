
package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/students")
public class StudentController {

    // In-memory student storage
    private final ArrayList<Student> students = new ArrayList<>();

    // Constructor: Add some initial students
    public StudentController() {
        students.add(new Student(1, "Midaksh", "midaksh@example.com"));
        students.add(new Student(2, "Aahan", "aahan@example.com"));
        students.add(new Student(3, "Azam", "azam@example.com"));
    }

    // GET: Retrieve all students
    @GetMapping
    public ResponseEntity<ArrayList<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    // POST: Add a new student
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        students.add(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    // GET: Retrieve student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {

        for (Student student : students) {
            if (student.getId() == id) {
                return ResponseEntity.ok(student);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // PUT: Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(updatedStudent.getName());
                student.setEmail(updatedStudent.getEmail());

                return ResponseEntity.ok(student);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {

        for (Student student : students) {
            if (student.getId() == id) {
                students.remove(student);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}

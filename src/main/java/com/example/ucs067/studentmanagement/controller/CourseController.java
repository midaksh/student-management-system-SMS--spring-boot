
package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Course;
import com.example.ucs067.studentmanagement.repository.CourseRepository;
import com.example.ucs067.studentmanagement.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseController(
            CourseRepository courseRepository,
            StudentRepository studentRepository) {

        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    // GET: Retrieve all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    // GET: Retrieve course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {

        return courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Add a course to a student
    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> addCourseToStudent(
            @PathVariable int studentId,
            @RequestBody Course course) {

        return studentRepository.findById(studentId)
                .map(student -> {

                    if (courseRepository.existsByStudent_IdAndCourseCode(
                            studentId, course.getCourseCode())) {

                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body("This student is already enrolled in this course.");
                    }

                    course.setStudent(student);

                    Course savedCourse = courseRepository.save(course);

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(savedCourse);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Retrieve all courses belonging to a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudent(
            @PathVariable int studentId) {

        return studentRepository.findById(studentId)
                .map(student ->
                        ResponseEntity.ok(student.getCourses())
                )
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {

        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        courseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

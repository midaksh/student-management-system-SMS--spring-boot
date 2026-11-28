
package com.example.ucs067.studentmanagement.repository;

import com.example.ucs067.studentmanagement.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    // Check whether a student already has a course with this code
    boolean existsByStudent_IdAndCourseCode(
            Integer studentId,
            String courseCode
    );
}


package com.example.ucs067.studentmanagement.repository;

import com.example.ucs067.studentmanagement.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Check whether a student with this email already exists
    boolean existsByEmail(String email);

    // Search students by exact name
    List<Student> findByName(String name);

    // Search students whose names contain a keyword
    List<Student> findByNameContaining(String keyword);

    // Search students by exact email
    List<Student> findByEmail(String email);
}


package com.example.ucs067.studentmanagement.repository;

import com.example.ucs067.studentmanagement.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Find students by exact name
    List<Student> findByName(String name);

    // Find students whose names contain a keyword
    List<Student> findByNameContaining(String keyword);

    // Find students by exact email
    List<Student> findByEmail(String email);
}

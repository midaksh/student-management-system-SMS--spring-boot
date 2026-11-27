
package com.example.ucs067.studentmanagement.repository;

import com.example.ucs067.studentmanagement.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}

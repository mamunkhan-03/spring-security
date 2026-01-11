package com.example.mPack.student.repository;
import com.example.mPack.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
     Student findByName (String name);
     Student findByMobile(String mobile);
     List<Student> findAllByDepartment (String department);
     Student findByEmail (String email);
}


package com.example.mPack.student.repository;

import com.example.mPack.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;
    private Student student;
    private Student student2;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .name("Abrar Mahir")
                .address("Dhaka")
                .department("Computer Science")
                .email("mahir@gmail.com")
                .mobile("01892524548")
                .rank(10L)
                .build();

        student2 = Student.builder()
                .name("Meherab Mushriq")
                .address("Cumilla")
                .department("Pharmacy")
                .email("mushriq@gmail.com")
                .mobile("01520401512")
                .rank(12L)
                .build();
    }

    @Test
    void saveStudentTest (){

        Student response = studentRepository.save(student);
        assertNotNull(response.getId(), "The saved student's ID should not be null");
        assertEquals(student.getEmail(), response.getEmail(), "Expected email: mahir@gmail.com");
        assertEquals(student.getName(), response.getName(), "Expected name: Abrar Mahir");
        assertEquals(student.getAddress(), response.getAddress(), "Expected address: Dhaka");
        assertEquals(student.getDepartment(), response.getDepartment(), "Expected department: Computer Science");
        assertEquals(student.getMobile(), response.getMobile(), "Expected mobile: 01892524548");
        assertEquals(student.getRank(), response.getRank(), "Expected rank: 10");

        System.out.println("Save student test successful");
    }

    @Test
    void findByIdTest() {
        studentRepository.save(student2);
        Student foundStudent = studentRepository.findById(student2.getId()).orElse(null);
        assertNotNull(foundStudent);
        assertEquals("Meherab Mushriq", foundStudent.getName());
        assertTrue(foundStudent.getId()>0, "The student ID should be greater than 0");
        System.out.println(foundStudent);
    }

    @Test
    void findAllTest() {
        List<Student> students = studentRepository.findAll();

        assertFalse(students.isEmpty());
        assertEquals(12, students.size());
        assertTrue(students.stream().anyMatch(s -> s.getName().equals("Abrar Mahir")));
        assertTrue(students.stream().anyMatch(s -> s.getDepartment().equals("Computer Science")));
    }


    @Test
    void findByNameTest() {
        Student foundStudent = studentRepository.findByName("Abrar Mahir");
        assertNotNull(foundStudent);
        assertEquals("Dhaka", foundStudent.getAddress());
        System.out.println(foundStudent.getId());
    }

    @Test
    void findByMobileTest() {
        Student foundStudent = studentRepository.findByMobile("01892524548");
        assertNotNull(foundStudent);
        assertNotNull(foundStudent.getId());
        assertEquals("Bangla", foundStudent.getDepartment());
        assertEquals(student.getMobile(), foundStudent.getMobile());
        System.out.println(foundStudent);
    }

    @Test
    void findAllByDepartmentTest() {
        List<Student> students = studentRepository.findAllByDepartment("Computer Science");
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("Abrar Mahir", students.get(0).getName());
    }
}




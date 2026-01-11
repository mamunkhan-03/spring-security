
package com.example.mPack.student.repository;

import com.example.mPack.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepoTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        studentRepository.deleteAll();

        studentRepository.save(Student.builder()
                .name("Saifur Rahman")
                .department("Arts")
                .address("Madaripur")
                .mobile("01820545856")
                .email("saifur@gmail.com")
                .rank(10L)
                .build());

        studentRepository.save(Student.builder()
                .name("Tipu Khan")
                .department("Bangla")
                .address("Dhaka")
                .mobile("0189284546")
                .email("tipu123@gmail.com")
                .rank(12L)
                .build());


    }

    @Test
    public void testSaveStudent() {
        Student student = Student.builder()
                .name("John Doe")
                .department("Science")
                .address("Chittagong")
                .mobile("01712345678")
                .email("john.doe@gmail.com")
                .rank(5L)
                .build();

        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals("John Doe", savedStudent.getName());
        assertEquals("Science", savedStudent.getDepartment());
        assertEquals("Chittagong", savedStudent.getAddress());
        assertEquals("01712345678", savedStudent.getMobile());
        assertEquals("john.doe@gmail.com", savedStudent.getEmail());
        assertEquals(Long.valueOf(5), savedStudent.getRank());
    }

    @Test
    public void testFindById() {
        Student existingStudent = studentRepository.findAll().get(0);
        Optional<Student> foundStudent = studentRepository.findById(existingStudent.getId());

        assertTrue(foundStudent.isPresent());
        assertEquals(existingStudent.getId(), foundStudent.get().getId());
        assertEquals(existingStudent.getName(), foundStudent.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Student> foundStudent = studentRepository.findById(Long.MAX_VALUE);
        assertTrue(foundStudent.isEmpty());
    }

    @Test
    public void testFindAll() {
        List<Student> students = studentRepository.findAll();
        assertEquals(2, students.size());
    }

    @Test
    public void testFindByName() {
        Student students = studentRepository.findByName("Saifur Rahman");
        //assertFalse(students.isEmpty());
        assertEquals("Saifur Rahman", students.getName());
    }

    @Test public void testFindByNameNotFound() {
        Student students = studentRepository.findByName("Nonexistent");
        //assertTrue(students.isEmpty());
    }

    @Test
    public void testFindByMobile() {
        Student student = studentRepository.findByMobile("0189284546");
        assertNotNull(student);
        assertEquals("0189284546", student.getMobile());
    }

    @Test
    public void testFindByMobileNotFound() {
        Student students = studentRepository.findByMobile("0000000000");
        assertNotNull(students);
    }

    @Test public void testFindAllByDepartment() {
        List<Student> students = studentRepository.findAllByDepartment("Arts");
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("Saifur Rahman", students.get(0).getName());
    }

    @Test
    public void testFindAllByDepartmentNotFound() {
        List<Student> students = studentRepository.findAllByDepartment("Nonexistent");
        assertTrue(students.isEmpty());
    }
}

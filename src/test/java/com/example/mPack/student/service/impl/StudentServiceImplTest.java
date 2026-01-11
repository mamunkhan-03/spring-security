package com.example.mPack.student.service.impl;


import com.example.mPack.exception.ResourceNotFoundException;
import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import com.example.mPack.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudentServiceImplTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentService studentService;

    private Student student;
    private Student student2;
    private StudentDto studentDto;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setName("Saifur Rahman");
        student.setAddress("Dhaka");
        student.setMobile("0175484562");
        student.setDepartment("Chemistry");
        student.setEmail("saifur123@gmail.com");
        student.setRank(45L);
        studentRepository.save(student);

        student2 = new Student();
        student2.setName("John");
        student2.setAddress("Chattogram");
        student2.setMobile("0185566778");
        student2.setDepartment("Chemistry");
        student2.setEmail("john@example.com");
        student2.setRank(50L);
        studentRepository.save(student2);

        studentDto = mapper.map(student, StudentDto.class);
    }

    @Test
    void createStudent() {
        StudentDto newStudentDto = new StudentDto();
        newStudentDto.setName("Alice");
        newStudentDto.setAddress("Sylhet");
        newStudentDto.setMobile("0191234567");
        newStudentDto.setDepartment("Physics");
        newStudentDto.setEmail("alice@example.com");
        newStudentDto.setRank(60L);

        StudentDto response = studentService.createStudent(newStudentDto);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Alice", response.getName());
    }

    @Test
    void getAllStudents() {
        List<StudentDto> allStudents = studentService.getAllStudents();
        assertNotNull(allStudents);
        assertEquals(2, allStudents.size());
    }

    @Test
    void getStudentById() {
        StudentDto response = studentService.getStudentById(student.getId());
        assertNotNull(response);
        assertEquals("Saifur Rahman", response.getName());
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getMobile(), response.getMobile());
    }

    @Test
    void updateStudent() {
        StudentDto updateDto = new StudentDto();
        updateDto.setName("Saifur Rahman Updated");
        updateDto.setAddress("New Address");
        updateDto.setMobile("0181234567");
        updateDto.setDepartment("Physics");
        updateDto.setEmail("saifur456@gmail.com");
        updateDto.setRank(50L);

        StudentDto updateResponse = studentService.updateStudent(updateDto, student.getId());
        assertNotNull(updateResponse);
        assertEquals("Saifur Rahman Updated", updateResponse.getName());
        assertEquals("0181234567", updateResponse.getMobile());
    }

    @Test
    void deleteStudent() {
        StudentDto response = studentService.deleteStudent(student.getId());
        assertNotNull(response);
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(student.getId()));
    }

    @Test
    void getByName() {
        StudentDto response = studentService.getByName(student.getName());
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getMobile(), response.getMobile());
    }

    @Test
    void getAllByDepartment() {
        List<StudentDto> responses = studentService.getAllByDepartment("Chemistry");
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    void getByMobile() {
        StudentDto response = studentService.getByMobile(student.getMobile());
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getMobile(), response.getMobile());
    }

    @Test
    void getByEmail() {
        ResponseStudentDto response = studentService.getByEmail(student.getEmail());
        assertNotNull(response);
        assertEquals(100L, response.getMessageCode());
        assertEquals("Successfully retrieve the data", response.getMessage());
    }
}

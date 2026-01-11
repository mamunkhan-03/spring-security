package com.example.mPack.student.service.impl;

import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceImplTestMockito {

    @MockBean
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl studentService;

    StudentDto studentDto;

    Student student;

    @BeforeEach
    void setup (){
        student =new Student(110L,"Rafi", "CSE", "Cumilla", "01540202325", "rafi@gamil.com", 10L);
        studentDto.setId(110L);
        studentDto.setName("Rafi");
        studentDto.setAddress("Cumilla");
        studentDto.setDepartment("CSE");
        studentDto.setEmail("rafi@gamil.com");
        studentDto.setMobile("01540202325");
        studentDto.setRank(10L);

        studentRepository.save(student);


        when (studentRepository.save(student)).thenReturn(student);

    }

    @Test
    void createStudentTest (){

        StudentDto response=studentService.createStudent(studentDto);
        assertNotNull(response);
        assertEquals(response.getId(),studentDto.getId() );



    }



}

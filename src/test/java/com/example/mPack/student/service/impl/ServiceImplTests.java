package com.example.mPack.student.service.impl;

import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import jakarta.persistence.SecondaryTable;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceImplTests {

    @Autowired
    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;

    Student student;
    StudentDto studentDto;
    void setup (){

        student =new Student(110L,"Rafi", "CSE", "Cumilla", "01540202325", "rafi@gamil.com", 10L);
//        studentRepository.save(student);
//        when (studentRepository.save(student)).thenReturn(student);

        when(studentRepository.findByName(ArgumentMatchers.anyString())).thenReturn(student);
    }

    @Test
    void findByNameTest (){
        //Student response= studentService.getByName(student.getName());
        assertNotNull(studentService.getByName(student.getName()));
    }
}

package com.example.mPack.student.repository;

import com.example.mPack.student.model.Student;
import com.example.mPack.student.service.StudentService;
import org.hibernate.tool.schema.spi.SchemaTruncator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentRepoTestMockito {
    @Mock
    private StudentRepository studentRepository;
    private Student student;

    private Student newStudent;

    @BeforeEach
    void setup (){
        student =new Student();
        student.setId(999L);
        student.setName("Mr. Mamun");
        student.setEmail("mamun626@gmail.com");
        student.setAddress("Dhaka");
        student.setDepartment("IIT");
        student.setRank(1L);
        student.setMobile("01540202658");

        studentRepository.save(student);
    }

    @Test
    void findByNameTest (){
        when(studentRepository.findByName("Mr. Mamun")).thenReturn(student);

        Student response =studentRepository.findByName("Mr. Mamun");
        System.out.println(response);

        assertNotNull(response);
        assertEquals(student.getMobile(), response.getMobile());
        assertNotNull(response.getId());
        assertEquals(student.getId(), 999);
    }


    @Test
    void findByMobile(){
        when(studentRepository.findByMobile(student.getMobile())).thenReturn(student);

        Student response =studentRepository.findByMobile(student.getMobile());
        System.out.println(response);

        assertNotNull(response);
        assertEquals(student.getName(),  response.getName());
        assertEquals(student.getMobile(), response.getMobile());
    }

    @Test
    void findAllByDepartment (){
        newStudent=new Student();
        newStudent.setId(888L);
        newStudent.setName("Mr. Shishir");
        newStudent.setDepartment("IIT");
        newStudent.setAddress("Gaziput");
        newStudent.setMobile("01456595758");
        newStudent.setEmail("shishir@gmail.com");
        newStudent.setRank(2L);

        studentRepository.save(newStudent);

        List<Student> stu=new ArrayList<>();
        stu.add(student);
        stu.add(newStudent);

        when(studentRepository.findAllByDepartment("IIT")).thenReturn(stu);

        List<Student> allStudent=studentRepository.findAllByDepartment("IIT");
        System.out.println(allStudent.size());
        System.out.println(allStudent);

        assertEquals(allStudent.get(1).getDepartment(), newStudent.getDepartment());

    }

    @Test
    void findByEmail (){

        when(studentRepository.findByEmail("mamun626@gmail.com")).thenReturn(student);

        Student response =studentRepository.findByEmail("mamun626@gmail.com");
        assertNotNull(response);
        System.out.println(response);

        assertEquals(response.getEmail(), student.getEmail());


    }



}

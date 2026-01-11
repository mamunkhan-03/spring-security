package com.example.mPack.student.service;

import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest{

    @MockBean
    StudentRepository repository;

    @Autowired
    StudentService studentService;

    private Student student;

    private Student student2;
    private StudentDto studentDto;

    private List<Student> students;
    private List<StudentDto> studentDtos;

    @BeforeEach
    void setup(){

        student =new Student();
        student.setId(105L);
        student.setName("Saifur Rahman");
        student.setAddress("Dhaka");
        student.setMobile("0175484562");
        student.setDepartment("Chemistry");
        student.setEmail("saifur123@gmail.com");
        student.setRank(45L);


        student2 =new Student();
        student2.setId(106L);
        student2.setName("John");
        student2.setAddress("Chattogram");
        student2.setMobile("0185566778");
        student2.setDepartment("Chemistry");
        student2.setEmail("john.@example.com");
        student2.setRank(50L);

        studentDto=new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAddress(student.getAddress());
        studentDto.setMobile(student.getMobile());
        studentDto.setDepartment(student.getDepartment());
        studentDto.setEmail(student.getEmail());
        studentDto.setRank(student.getRank());

        repository.save(student);
        when (repository.findById(student.getId())).thenReturn(Optional.of(student));
        when(repository.save(student)).thenReturn(student);
        when (repository.findByName(student.getName())).thenReturn(student);
        when(repository.findByMobile(student.getMobile())).thenReturn(student);
        when (repository.findByEmail("saifur123@gmail.com")).thenReturn(student);

        //for getting by department

        students = Arrays.asList(student, student2);
        studentDtos = students.stream().map(student -> {
            StudentDto dto = new StudentDto();
            dto.setId(student.getId());
            dto.setName(student.getName());
            dto.setAddress(student.getAddress());
            dto.setMobile(student.getMobile());
            dto.setDepartment(student.getDepartment());
            dto.setEmail(student.getEmail());
            dto.setRank(student.getRank());
            return dto;
        }).collect(Collectors.toList());

        when(repository.findAllByDepartment("Chemistry")).thenReturn(students);


        doNothing().when(repository).delete(student);


        when(repository.findAll()).thenReturn(Arrays.asList(student, student2)); // Mock the findAll method




    }





    @Test
    void createStudent() {

        StudentDto response= studentService.createStudent(studentDtos.get(0));
        assertNotNull(response);
        assertEquals(response.getDepartment(), studentDtos.get(0).getDepartment());

    }

    @Test
    void getAllStudents() {

       List<StudentDto> allStudents= studentService.getAllStudents();
       assertNotNull(allStudents);
       assertEquals(allStudents.get(0).getEmail(), studentDtos.get(0).getEmail());
    }

    @Test
     void getStudentById (){
       StudentDto response= studentService.getStudentById(student.getId());
       assertNotNull(response);
       assertEquals("Saifur Rahman", response.getName());
       assertEquals(student.getId(), response.getId());
       assertEquals(student.getMobile(), response.getMobile());
        System.out.println(response);
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
       StudentDto updateResponse= studentService.updateStudent(updateDto, student.getId());
       assertNotNull(updateResponse);
      // assertNotEquals(updateResponse.getMobile(), student.getMobile());
        assertEquals("0181234567", updateResponse.getMobile());


    }

    @Test
    void deleteStudent() {
      StudentDto response= studentService.deleteStudent(student.getId());
      assertNotNull(response);
    }

    @Test
    void getByName() {


        StudentDto response =studentService.getByName(student.getName());
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getMobile(), response.getMobile());
        System.out.println(response);
    }

    @Test
    void getAllByDepartment() {

      List<StudentDto> responses =  studentService.getAllByDepartment("Chemistry");
      assertNotNull(responses);
      assertEquals(2, responses.size());
      assertEquals(studentDtos.get(0).getId(), responses.get(0).getId());
      assertEquals(studentDtos.get(1).getMobile(), responses.get(1).getMobile());


    }

    @Test
    void getByMobile() {

        StudentDto response =studentService.getByMobile(student.getMobile());
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getMobile(), response.getMobile());
        System.out.println(response);
    }

    @Test
    void getByEmail() {
      ResponseStudentDto response= studentService.getByEmail(student.getEmail());
      assertNotNull(response);
        System.out.println(response.getMessageCode());
        System.out.println(response.getMessage());

    }
}
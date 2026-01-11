package com.example.mPack.student.service;

import com.example.mPack.student.dto.StudentDto;

import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudentServiceTest2 {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    private StudentDto studentDto;
    private StudentDto studentDto2;
    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("John Doe");
        studentDto.setEmail("john.doe@example.com");

        studentDto2 = new StudentDto();
        studentDto2.setId(1L);
        studentDto2.setName("John Smith");
        studentDto2.setEmail("john.smith@example.com");

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");

        // Stubbing only the necessary methods
        when(modelMapper.map(any(StudentDto.class), any(Class.class))).thenReturn(student);
        when(modelMapper.map(any(Student.class), any(Class.class))).thenReturn(studentDto);
    }



    @Test
    public void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDto foundStudent = studentService.getStudentById(1L);
        assertEquals(studentDto.getName(), foundStudent.getName());
        assertEquals(studentDto.getEmail(), foundStudent.getEmail());
    }

//    @Test
//    public void testUpdateStudent() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(studentRepository.save(any(Student.class))).thenReturn(student);
//
//        StudentDto updatedStudent = studentService.updateStudent(1L , studentDto2);
//        assertEquals(studentDto2.getName(), updatedStudent.getName());
//        assertEquals(studentDto2.getEmail(), updatedStudent.getEmail());
//    }

    @Test
    public void testDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> studentList = studentService.getAllStudents();
        assertEquals(1, studentList.size());
        assertEquals(studentDto.getName(), studentList.get(0).getName());
        assertEquals(studentDto.getEmail(), studentList.get(0).getEmail());
    }
}

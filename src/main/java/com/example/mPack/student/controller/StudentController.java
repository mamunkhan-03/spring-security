package com.example.mPack.student.controller;

import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/students")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<StudentDto>  createStudent (@RequestBody StudentDto studentDto){
        StudentDto response=studentService.createStudent(studentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<StudentDto>  getAllStudents (){
        return studentService.getAllStudents();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable (name = "id")Long id){
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent (@RequestBody StudentDto studentDto, @PathVariable (name = "id")Long id ){

        StudentDto response =studentService.updateStudent(studentDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDto> deleteStudent (@PathVariable (name = "id") Long id){
        StudentDto response =studentService.deleteStudent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<StudentDto> getByName(@PathVariable (name = "name") String name) {
        StudentDto response= studentService.getByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentDto>> getAllByDepartment(@PathVariable String department) {
        List<StudentDto> students = studentService.getAllByDepartment(department);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{mobile}")
    public ResponseEntity<StudentDto> getByMobile(@PathVariable (name="mobile") String mobile){
        return new ResponseEntity<>(studentService.getByMobile(mobile),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseStudentDto> getByEmail (@PathVariable (name = "email") String email){

        return new ResponseEntity<>(studentService.getByEmail(email),HttpStatus.OK);
    }



}





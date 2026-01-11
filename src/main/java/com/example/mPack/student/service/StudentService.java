package com.example.mPack.student.service;


import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto createStudent (StudentDto studentDto);

    List<StudentDto> getAllStudents ();

    StudentDto getStudentById(Long id);

    StudentDto updateStudent (StudentDto studentDto, Long id);

    StudentDto deleteStudent (Long id);

    StudentDto getByName (String name);

    List<StudentDto> getAllByDepartment (String department);

    StudentDto getByMobile (String mobile );

    ResponseStudentDto getByEmail (String email);



}

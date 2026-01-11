package com.example.mPack.student.service.impl;

import com.example.mPack.exception.ResourceNotFoundException;
import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import com.example.mPack.student.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private ModelMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }


    @Override
    public StudentDto createStudent(StudentDto studentDto) {
       Student studentResponse = studentRepository.save(mapToEntity(studentDto));
        return mapToDto(studentResponse) ;
    }


    @Override
    public List<StudentDto> getAllStudents() {

        List<Student>  students=studentRepository.findAll();
        return students.stream().map(student -> mapToDto(student)).collect(Collectors.toList());

    }

    @Override
    public StudentDto getStudentById(Long id) {

        Student response =studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("student", "id", id));
        return mapToDto(response);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {

        Student oldStudent =studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("student","id", id));

        oldStudent.setName(studentDto.getName());
        oldStudent.setDepartment(studentDto.getDepartment());
        oldStudent.setEmail(studentDto.getEmail());
        oldStudent.setMobile(studentDto.getMobile());
        oldStudent.setRank(studentDto.getRank());
        oldStudent.setAddress(studentDto.getAddress());

        Student updateStudent =studentRepository.save(oldStudent);

        return mapToDto(oldStudent);
    }

    @Override
    public StudentDto deleteStudent(Long id) {
        Student student =studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("student", "id", id));
        studentRepository.delete(student);
        return mapToDto(student);
    }

    @Override
    public StudentDto getByName(String name) {
        return mapToDto(studentRepository.findByName(name));

    }

    @Override
    public List<StudentDto> getAllByDepartment(String department) {

        List<Student> students = studentRepository.findAllByDepartment(department);

        // Convert the list of Student entities to a list of StudentDto
        return students.stream().map(student -> mapToDto(student)).collect(Collectors.toList());

    }

    @Override
    public StudentDto getByMobile(String mobile) {
        return mapToDto(studentRepository.findByMobile(mobile));
    }

    @Override
    public ResponseStudentDto getByEmail(String email) {
        ResponseStudentDto responseStudentDto=new ResponseStudentDto();

        try {

            responseStudentDto.setMessageCode(100L);
            responseStudentDto.setMessage("Successfully retrieve the data");

            studentRepository.findByEmail(email);
        }

        catch (Exception exception){
            exception.printStackTrace();

        }

        return responseStudentDto;
    }


    //map Student to Dto

    public StudentDto mapToDto (Student student){
      StudentDto response=  mapper.map(student, StudentDto.class);
      return response;
    }

    //map Dto to Student

    public Student mapToEntity (StudentDto studentDto){
        Student response=  mapper.map(studentDto, Student.class);
        return response;
    }
}




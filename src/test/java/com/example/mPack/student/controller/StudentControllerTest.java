package com.example.mPack.student.controller;

import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    StudentService studentService;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Student student;

    StudentDto studentDto;

    StudentDto studentDto2;

    ResponseStudentDto responseStudentDto;

    List<StudentDto> getAllResponses;

    @BeforeEach
    void setup (){
        student=new Student();
        student.setId(858L);
        student.setName("Polash");
        student.setDepartment("Mathematics");
        student.setEmail("polash@gmail.com");
        student.setAddress("Dhaka");
        student.setMobile("01820104548");
        student.setRank(45L);

        studentDto=new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAddress(student.getAddress());
        studentDto.setEmail(student.getEmail());
        studentDto.setMobile(student.getMobile());
        studentDto.setRank(student.getRank());
        studentDto.setDepartment(student.getDepartment());

        studentDto2=new StudentDto();
        studentDto2.setId(602L);
        studentDto2.setDepartment("ICE");
        studentDto2.setName("Munna");
        studentDto2.setAddress("Noakhali");
        studentDto2.setEmail("munna@gmail.com");
        studentDto2.setMobile("0153026547");
        studentDto2.setRank(1L);

        when(studentService.createStudent(studentDto)).thenReturn(studentDto);

        getAllResponses=new ArrayList<>();

        getAllResponses.add(studentDto);
        getAllResponses.add(studentDto2);

        when (studentService.getAllStudents()).thenReturn(getAllResponses);

        when(studentService.updateStudent(studentDto, studentDto.getId())).thenReturn(studentDto2);

        when(studentService.getStudentById(ArgumentMatchers.anyLong())).thenReturn(studentDto);

        when(studentService.getByName(ArgumentMatchers.anyString())).thenReturn(studentDto);

        when(studentService.getByMobile(ArgumentMatchers.anyString())).thenReturn(studentDto);

        responseStudentDto =new ResponseStudentDto();
        responseStudentDto.setMessageCode(1L);
        responseStudentDto.setMessage("Successfully retrieve");
        when(studentService.getByEmail(ArgumentMatchers.anyString())).thenReturn(responseStudentDto);

        //when(studentService.updateStudent(an))

        when(studentService.deleteStudent(studentDto2.getId())).thenReturn(studentDto2);


    }



    @Test
    void createStudent() throws  Exception{
        String studentDataJson=objectMapper.writeValueAsString(studentDto);
       MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/api/students").contentType(MediaType.APPLICATION_JSON).content(studentDataJson)).andReturn();

       System.out.println(result.getResponse().getContentAsString());

       String compare=result.getResponse().getContentAsString();

        Map<String, Object> jsonMap = objectMapper.readValue(compare, Map.class);
        System.out.println(jsonMap);

        int status =result.getResponse().getStatus();
        System.out.println(status);
        assertEquals(201, status);

        String contentType=result.getResponse().getContentType();
        System.out.println(contentType);
        assertEquals(MediaType.APPLICATION_JSON_VALUE, contentType);

        assertNotNull(jsonMap.get("id"));
        assertEquals(studentDto.getDepartment(), jsonMap.get("department"));
        System.out.println(jsonMap.get("department"));

    }

    @Test
    void getAllStudents() throws  Exception {

        getAllResponses=new ArrayList<>();

        getAllResponses.add(studentDto);
        getAllResponses.add(studentDto2);

        when (studentService.getAllStudents()).thenReturn(getAllResponses);


        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/students").contentType(MediaType.APPLICATION_JSON)).andReturn();

        String compare=result.getResponse().getContentAsString();
        System.out.println(compare);
        List<Map<String, Object>> jsonResponse = objectMapper.readValue(compare, List.class);
        System.out.println(jsonResponse);

        // Validate the size of the list
        assertNotNull(jsonResponse);
        assertEquals(2, jsonResponse.size());

        // Validate the first student in the response
        Map<String, Object> firstStudent = jsonResponse.get(1);
        assertEquals(firstStudent.get("department"), getAllResponses.get(1).getDepartment());
        System.out.println(firstStudent.get("department"));

    }

    @Test
    void getStudentById() throws  Exception {
        Long id =student.getId();
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/id/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void updateStudent() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/students/{id}", studentDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Get and print response content
        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);

        // Deserialize response content
        StudentDto responseStudentDto = objectMapper.readValue(responseContent, StudentDto.class);

        // Validate the response
        assertNotNull(responseStudentDto);
        assertEquals(studentDto2.getId(), responseStudentDto.getId());
        assertEquals(studentDto2.getName(), responseStudentDto.getName());
        assertEquals(studentDto2.getDepartment(), responseStudentDto.getDepartment());
        assertEquals(studentDto2.getEmail(), responseStudentDto.getEmail());
        assertEquals(studentDto2.getAddress(), responseStudentDto.getAddress());
        assertEquals(studentDto2.getMobile(), responseStudentDto.getMobile());
        assertEquals(studentDto2.getRank(), responseStudentDto.getRank());
    }


    @Test
    void deleteStudent() throws Exception {
        Long id =studentDto2.getId();
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentDto2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(studentDto2.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(studentDto2.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(studentDto2.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(studentDto2.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(studentDto2.getRank()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void getByName() throws  Exception{
        String name=student.getName();
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/name/{name}", name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }


    @Test
    void getAllByDepartment() throws Exception {
        // Given
        String department = "ICE";
        List<StudentDto> students = Arrays.asList( studentDto2);

        when(studentService.getAllByDepartment(department)).thenReturn(students);

        // Perform the GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/students/department/{department}", department)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(studentDto2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(studentDto2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].department").value(studentDto2.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(studentDto2.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobile").value(studentDto2.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(studentDto2.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rank").value(studentDto2.getRank()))
                        .andReturn();

        // Print the response for debugging
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    void getByMobile()throws  Exception {
        String mobile=student.getMobile();
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/{mobile}", mobile).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByEmail() throws Exception{
        String email=studentDto.getEmail();
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/email/{email}", email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageCode").value(responseStudentDto.getMessageCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(responseStudentDto.getMessage()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
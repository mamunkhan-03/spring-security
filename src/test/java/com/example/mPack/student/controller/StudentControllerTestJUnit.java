package com.example.mPack.student.controller;


import com.example.mPack.student.dto.ResponseStudentDto;
import com.example.mPack.student.dto.StudentDto;
import com.example.mPack.student.model.Student;
import com.example.mPack.student.repository.StudentRepository;
import com.example.mPack.student.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

    @SpringBootTest
    @AutoConfigureMockMvc
    @Transactional
    public class StudentControllerTestJUnit {

        @Autowired
        private StudentRepository studentRepository;

        @Autowired
        private StudentService studentService;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        private Student student;
        private StudentDto studentDto;
        private StudentDto studentDto2;
        private ResponseStudentDto responseStudentDto;
        private List<StudentDto> getAllResponses;

        @BeforeEach
        void setup() {
            studentRepository.deleteAll();

            student = new Student();
            student.setName("Polash");
            student.setDepartment("Mathematics");
            student.setEmail("polash@gmail.com");
            student.setAddress("Dhaka");
            student.setMobile("01820104548");
            student.setRank(45L);
            student = studentRepository.save(student);

            studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setName(student.getName());
            studentDto.setAddress(student.getAddress());
            studentDto.setEmail(student.getEmail());
            studentDto.setMobile(student.getMobile());
            studentDto.setRank(student.getRank());
            studentDto.setDepartment(student.getDepartment());

            studentDto2 = new StudentDto();
            studentDto2.setId(602L);
            studentDto2.setDepartment("ICE");
            studentDto2.setName("Munna");
            studentDto2.setAddress("Noakhali");
            studentDto2.setEmail("munna@gmail.com");
            studentDto2.setMobile("0153026547");
            studentDto2.setRank(1L);

            getAllResponses = Arrays.asList(studentDto, studentDto2);

            responseStudentDto = new ResponseStudentDto();
            responseStudentDto.setMessageCode(1L);
            responseStudentDto.setMessage("Successfully retrieve");
        }

        @Test
        void createStudent() throws Exception {
            String studentDataJson = objectMapper.writeValueAsString(studentDto);
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(studentDataJson))
                    .andReturn();

            String compare = result.getResponse().getContentAsString();
            Map<String, Object> jsonMap = objectMapper.readValue(compare, Map.class);

            int status = result.getResponse().getStatus();
            assertEquals(201, status);

            String contentType = result.getResponse().getContentType();
            assertEquals(MediaType.APPLICATION_JSON_VALUE, contentType);

            assertNotNull(jsonMap.get("id"));
            assertEquals(studentDto.getDepartment(), jsonMap.get("department"));
        }

        @Test
        void getAllStudents() throws Exception {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/students")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String compare = result.getResponse().getContentAsString();
            List<Map<String, Object>> jsonResponse = objectMapper.readValue(compare, List.class);

            assertNotNull(jsonResponse);
            assertEquals(1, jsonResponse.size());

            Map<String, Object> firstStudent = jsonResponse.get(0);
            assertEquals(firstStudent.get("department"), studentDto.getDepartment());
        }

        @Test
        void getStudentById() throws Exception {
            Long id = student.getId();
            mockMvc.perform(MockMvcRequestBuilders.get("/api/students/id/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()));
        }

        @Test
        void updateStudent() throws Exception {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/students/{id}", studentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(studentDto2)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            StudentDto responseStudentDto = objectMapper.readValue(responseContent, StudentDto.class);

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
            Long id = studentDto.getId();
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            StudentDto responseStudentDto = objectMapper.readValue(responseContent, StudentDto.class);

            assertNotNull(responseStudentDto);
            assertEquals(studentDto.getId(), responseStudentDto.getId());
            assertEquals(studentDto.getName(), responseStudentDto.getName());
            assertEquals(studentDto.getDepartment(), responseStudentDto.getDepartment());
            assertEquals(studentDto.getEmail(), responseStudentDto.getEmail());
            assertEquals(studentDto.getAddress(), responseStudentDto.getAddress());
            assertEquals(studentDto.getMobile(), responseStudentDto.getMobile());
            assertEquals(studentDto.getRank(), responseStudentDto.getRank());
        }

        @Test
        void getByName() throws Exception {
            String name = student.getName();
            mockMvc.perform(MockMvcRequestBuilders.get("/api/students/name/{name}", name)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()));
        }

        @Test
        void getAllByDepartment() throws Exception {
            String department = student.getDepartment();
            mockMvc.perform(MockMvcRequestBuilders.get("/api/students/department/{department}", department)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(student.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].department").value(student.getDepartment()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(student.getEmail()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].mobile").value(student.getMobile()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(student.getAddress()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].rank").value(student.getRank()));
        }


        @Test
        void getByMobile() throws Exception {
            String mobile = student.getMobile();

            mockMvc.perform(MockMvcRequestBuilders.get("/api/students/mobile/{mobile}", mobile)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(student.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(student.getDepartment()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(student.getEmail()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(student.getMobile()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(student.getAddress()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(student.getRank()));
        }
    }

package com.example.mPack.employee.controller;
import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.entity.Employee;
import com.example.mPack.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private CustomizeEmpDto customizeEmpDto1;
    private CustomizeEmpDto customizeEmpDto2;
    private ResponseEmpDto responseEmpDto;
    private List<EmployeeDto> employeeDtoList;
    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();

        Employee employee1 = new Employee();
        employee1.setName("Nuh Salman");
        employee1.setEmail("nuhsalman@gmail.com");
        employee1.setDesignation("Manager, Sales");
        employee1.setDepartment("Marketing Dept.");
        employee1.setDivision("Marketing Division");
        employee1.setMobile("01820702329");
        employee1.setSalary(50000L);
        employee1.setAddress("Dhaka");
        employee1.setBloodGroup("O+");

        Employee employee2 = new Employee();
        employee2.setName("Hud Salman");
        employee2.setEmail("hudsalman@gmail.com");
        employee2.setDesignation("Senior Software Developer");
        employee2.setDepartment("Development");
        employee2.setDivision("Engineering");
        employee2.setMobile("01333722677");
        employee2.setSalary(70000L);
        employee2.setAddress("Cumilla");
        employee2.setBloodGroup("A+");

        employeeRepository.saveAll(Arrays.asList(employee1, employee2));

        employeeDto1 = new EmployeeDto();

        employeeDto1.setId(employee1.getId());
        employeeDto1.setName(employee1.getName());
        employeeDto1.setEmail(employee1.getEmail());
        employeeDto1.setDesignation(employee1.getDesignation());
        employeeDto1.setDepartment(employee1.getDepartment());
        employeeDto1.setDivision(employee1.getDivision());
        employeeDto1.setMobile(employee1.getMobile());
        employeeDto1.setSalary(employee1.getSalary());
        employeeDto1.setAddress(employee1.getAddress());
        employeeDto1.setBloodGroup(employee1.getBloodGroup());

        employeeDto2 = new EmployeeDto();
        employeeDto2.setId(employee2.getId());
        employeeDto2.setName(employee2.getName());
        employeeDto2.setEmail(employee2.getEmail());
        employeeDto2.setDesignation(employee2.getDesignation());
        employeeDto2.setDepartment(employee2.getDepartment());
        employeeDto2.setDivision(employee2.getDivision());
        employeeDto2.setMobile(employee2.getMobile());
        employeeDto2.setSalary(employee2.getSalary());
        employeeDto2.setAddress(employee2.getAddress());
        employeeDto2.setBloodGroup(employee2.getBloodGroup());

        customizeEmpDto1 = new CustomizeEmpDto(employeeDto1);
        customizeEmpDto2 = new CustomizeEmpDto(employeeDto2);

        responseEmpDto = new ResponseEmpDto();
        responseEmpDto.setResponseCode(1);
        responseEmpDto.setResponseMessage("Operation Successful");

        employeeDtoList = Arrays.asList(employeeDto1, employeeDto2);
    }

    @Test
    void createEmployeeTest() throws Exception {
        EmployeeDto newEmployeeDto = new EmployeeDto();
        newEmployeeDto.setName("Kuddus");
        newEmployeeDto.setEmail("kuddus@gmail.com");
        newEmployeeDto.setDesignation("Intern");
        newEmployeeDto.setDepartment("Development");
        newEmployeeDto.setDivision("Engineering");
        newEmployeeDto.setMobile("01234567890");
        newEmployeeDto.setSalary(10000L);
        newEmployeeDto.setAddress("Mymensingh");
        newEmployeeDto.setBloodGroup("B+");

        MvcResult result = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployeeDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newEmployeeDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(newEmployeeDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(newEmployeeDto.getMobile()))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        EmployeeDto createdEmployee = objectMapper.readValue(response, EmployeeDto.class);

        assertThat(createdEmployee.getId()).isNotNull();
        assertThat(createdEmployee.getName()).isEqualTo("Kuddus");
    }

    @Test
    void getAllEmployeeTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        List<EmployeeDto> employees = Arrays.asList(objectMapper.readValue(response, EmployeeDto[].class));

        assertThat(employees).hasSize(2);
        assertThat(employees.get(0).getName()).isEqualTo(employeeDto1.getName());
        assertThat(employees.get(1).getName()).isEqualTo(employeeDto2.getName());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/{id}", employeeDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeDto employee = objectMapper.readValue(response, EmployeeDto.class);

        assertThat(employee.getId()).isEqualTo(employeeDto1.getId());
        assertThat(employee.getName()).isEqualTo(employeeDto1.getName());
    }

    @Test
    void updateEmployeeTest() throws Exception {
        employeeDto2.setName("Mamun Khan");

        MvcResult result = mockMvc.perform(put("/api/employees/{id}", employeeDto2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeDto updatedEmployee = objectMapper.readValue(response, EmployeeDto.class);

        assertThat(updatedEmployee.getName()).isEqualTo("Mamun Khan");
        System.out.println(updatedEmployee.getName());
    }

    @Test
    void updateEmployeeByPatchTest() throws Exception {
        employeeDto1.setName("Mamun Khan");

        MvcResult result = mockMvc.perform(patch("/api/employees/{id}", employeeDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ResponseEmpDto responseEmpDto = objectMapper.readValue(response, ResponseEmpDto.class);

        assertThat(responseEmpDto.getResponseMessage()).isEqualTo("Updated successfully");

        Employee updatedEmployee = employeeRepository.findById(employeeDto1.getId()).orElseThrow();
        assertThat(updatedEmployee.getName()).isEqualTo("Mamun Khan");
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/employees/{id}", employeeDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ResponseEmpDto responseEmpDto = objectMapper.readValue(response, ResponseEmpDto.class);

        assertThat(responseEmpDto.getResponseMessage()).isEqualTo("Deleted successfully");
        assertThat(employeeRepository.existsById(employeeDto1.getId())).isFalse();
    }

    @Test
    void getByEmailTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/email/{email}", employeeDto1.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeDto employee = objectMapper.readValue(response, EmployeeDto.class);

        assertThat(employee.getEmail()).isEqualTo(employeeDto1.getEmail());
    }

    @Test
    void getByMobileTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/mobile/{mobile}", employeeDto1.getMobile())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeDto employee = objectMapper.readValue(response, EmployeeDto.class);

        assertThat(employee.getMobile()).isEqualTo(employeeDto1.getMobile());
    }

    @Test
    void getByDepartmentTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/department/{department}", employeeDto1.getDepartment())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<EmployeeDto> employees = Arrays.asList(objectMapper.readValue(response, EmployeeDto[].class));

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getDepartment()).isEqualTo(employeeDto1.getDepartment());
    }

    @Test
    void getByDesignationTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/designation/{designation}", employeeDto1.getDesignation())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<EmployeeDto> employees = Arrays.asList(objectMapper.readValue(response, EmployeeDto[].class));

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getDesignation()).isEqualTo(employeeDto1.getDesignation());
    }

    @Test
    void getByDivisionTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/division/{division}", "Engineering")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto2.getName()))
                .andReturn();

        String response =result.getResponse().getContentAsString();
        List<EmployeeDto> employees =Arrays.asList(objectMapper.readValue(response,EmployeeDto[].class));

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getDivision()).isEqualTo(employeeDto2.getDivision());
        System.out.println(employees.get(0).getDivision());

    }


    @Test
    void getByBloodGroupTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/bloodGroup/{bloodGroup}", "O+")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByDepartmentDivisionTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/twoProperties/{department},{division}", "Marketing Dept.", "Marketing Division")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        String email = jsonNode.get(0).get("email").asText();
        System.out.println("Email of first employee: " + email);
    }



}



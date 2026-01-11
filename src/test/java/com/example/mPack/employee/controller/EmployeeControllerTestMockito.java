package com.example.mPack.employee.controller;

import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTestMockito {
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private CustomizeEmpDto customizeEmpDto1;
    private CustomizeEmpDto customizeEmpDto2;
    private ResponseEmpDto responseEmpDto;
    private List<EmployeeDto> employeeDtoList;

    @BeforeEach
    void setup() {
        employeeDto1 = new EmployeeDto();
        employeeDto1.setId(1L);
        employeeDto1.setName("Nuh Salman");
        employeeDto1.setEmail("nuhsalman@gmail.com");
        employeeDto1.setDesignation("Manager, Sales");
        employeeDto1.setDepartment("Marketing Dept.");
        employeeDto1.setDivision("Marketing Division");
        employeeDto1.setMobile("01820702329");
        employeeDto1.setSalary(50000L);
        employeeDto1.setAddress("Dhaka");
        employeeDto1.setBloodGroup("O+");

        employeeDto2 = new EmployeeDto();
        employeeDto2.setId(2L);
        employeeDto2.setName("Hud Salman");
        employeeDto2.setEmail("hudsalman@gmail.com");
        employeeDto2.setDesignation("Senior Software Developer");
        employeeDto2.setDepartment("Development");
        employeeDto2.setDivision("Engineering");
        employeeDto2.setMobile("01333722677");
        employeeDto2.setSalary(70000L);
        employeeDto2.setAddress("Cumilla");
        employeeDto2.setBloodGroup("A+");

        customizeEmpDto1 = new CustomizeEmpDto(employeeDto1);
        customizeEmpDto2 = new CustomizeEmpDto(employeeDto2);

        responseEmpDto = new ResponseEmpDto();
        responseEmpDto.setResponseCode(1);
        responseEmpDto.setResponseMessage("Operation Successful");
        employeeDtoList = Arrays.asList(employeeDto1, employeeDto2);
    }
    @Test
    void createEmployeeTest() throws Exception {
        when(employeeService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(customizeEmpDto1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeDto1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeDto1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(employeeDto1.getMobile()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(MockMvcResultMatchers.jsonPath("$.name"));
    }

    @Test
    void getAllEmployeeTest() throws Exception {

        when(employeeService.getAllEmployee()).thenReturn(employeeDtoList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(employeeDto1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employeeDto2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value(employeeDto2.getEmail()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeDto1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(employeeDto1.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(employeeDto1.getAddress()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void updateEmployeeTest() throws Exception {
        when(employeeService.updateEmployee(Mockito.any(EmployeeDto.class), Mockito.anyLong())).thenReturn(employeeDto1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeDto1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(employeeDto1.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(employeeDto1.getAddress()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void updateEmployeeByPatchTest() throws Exception {
        when(employeeService.updateEmployeeByPatch(Mockito.any(EmployeeDto.class), Mockito.anyLong())).thenReturn(responseEmpDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(responseEmpDto.getResponseCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage").value(responseEmpDto.getResponseMessage()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        when(employeeService.deleteEmployee(1L)).thenReturn(responseEmpDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(responseEmpDto.getResponseCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage").value(responseEmpDto.getResponseMessage()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByEmailTest() throws Exception {
        when(employeeService.getByEmail("hudsalman@gmail.com")).thenReturn(employeeDto2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/email/{email}", "hudsalman@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto2.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(employeeDto2.getMobile()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByMobileTest() throws Exception {
        when(employeeService.getByMobile("01820702329")).thenReturn(employeeDto1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/mobile/{mobile}", "01820702329")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(employeeDto1.getMobile()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeDto1.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByDepartmentTest() throws Exception {
      //  when(employeeService.getByDepartment("Marketing Dept.")).thenReturn(employeeDtoList);
       when(employeeService.getByDepartment("Marketing Dept.")).thenAnswer(invocationOnMock -> {
            return employeeDtoList.stream().filter(emp->"Marketing Dept.".equals(emp.getDepartment())).
                    collect(Collectors.toList());
        });
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/department/{department}",
                                "Marketing Dept.")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByDesignationTest() throws Exception {
        when(employeeService.getByDesignation("Manager, Sales")).thenReturn(Arrays.asList(employeeDto1));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/designation/{designation}",
                                "Manager, Sales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByDivisionTest() throws Exception {
        //when(employeeService.getByDivision("Engineering")).thenReturn(employeeDtoList);

        when(employeeService.getByDivision("Engineering")).thenAnswer(invocationOnMock -> {
            return employeeDtoList.stream().filter(emp->"Engineering".equals(emp.getDivision())).
                    collect(Collectors.toList());
        });
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/division/{division}",
                                "Engineering")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto2.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByBloodGroupTest() throws Exception {
        when(employeeService.getByBloodGroup("O+")).thenReturn(Arrays.asList(customizeEmpDto1));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/bloodGroup/{bloodGroup}", "O+")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto1.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getByDepartmentDivisionTest() throws Exception {
        when(employeeService.getByDepartmentDivision("Development", "Engineering")).thenReturn(Arrays.asList(employeeDto2));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/twoProperties/{department},{division}",
                                "Development","Engineering")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDto2.getName()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());

        String email = jsonNode.get(0).get("email").asText();
        System.out.println("Email of first employee: " + email);
    }
}





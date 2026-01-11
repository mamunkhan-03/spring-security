package com.example.mPack.employee.service.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.entity.Employee;
import com.example.mPack.employee.repository.EmployeeRepository;
import com.example.mPack.employee.service.EmployeeService;
import com.example.mPack.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeServiceImplTestMockito {
    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmployeeServiceImpl employeeService;  // If we use EmployeeService interface it will work fine
    private Employee employee1;
    private Employee employee2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    void setup() {

        employee1 = new Employee();
        employee1.setId(101L);
        employee1.setName("Mamun Hossain");
        employee1.setEmail("mamun@gamil.com");
        employee1.setDesignation("Intern");
        employee1.setDepartment("iStelar");
        employee1.setDivision("Banking");
        employee1.setMobile("01820702329");
        employee1.setAddress("Dhaka");
        employee1.setSalary(10000L);
        employee1.setBloodGroup("A+");

        employee2 = new Employee();
        employee2.setId(102L);
        employee2.setName("Amit Roy");
        employee2.setEmail("amit@gmail.com");
        employee2.setDesignation("Associate Engineer");
        employee2.setDepartment("DATABASE ARCHITECTURE");
        employee2.setDivision("SOFTWARE ARCHITECTURE & DESIGN");
        employee2.setMobile("0190876543");
        employee2.setAddress("Khulna");
        employee2.setSalary(30000L);
        employee2.setBloodGroup("O+");

        employeeDto1 = mapper.map(employee1, EmployeeDto.class);
        employeeDto2 = mapper.map(employee2, EmployeeDto.class);
    }

    @Test
    void createEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setId(1L);
        newEmployee.setName("Mehedi Hasan");
        newEmployee.setEmail("mehedi@gmail.com");
        newEmployee.setDesignation("Executive, HR");
        newEmployee.setDepartment("H.R. & Admin");
        newEmployee.setDivision("H.R. & Admin");
        newEmployee.setMobile("0161122334");
        newEmployee.setAddress("Rajshahi");
        newEmployee.setSalary(90000L);
        newEmployee.setBloodGroup("A+");

        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        EmployeeDto newEmployeeDto =mapper.map(newEmployee, EmployeeDto.class);

        CustomizeEmpDto response = employeeService.createEmployee(newEmployeeDto);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Mehedi Hasan", response.getName());
        assertEquals("mehedi@gmail.com", response.getEmail());
        assertEquals("0161122334", response.getMobile());
        System.out.println(response);
    }

    @Test
    void getAllEmployee() {
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDto> allEmployees = employeeService.getAllEmployee();
        assertNotNull(allEmployees);
        assertEquals(2, allEmployees.size());

        EmployeeDto firstEmpDto = allEmployees.get(0);
        assertEquals("Mamun Hossain", firstEmpDto.getName());
        assertEquals("Intern", firstEmpDto.getDesignation());
        assertEquals("iStelar", firstEmpDto.getDepartment());
        assertEquals("01820702329",firstEmpDto.getMobile());

        EmployeeDto secondEmpDto = allEmployees.get(1);
        assertEquals(employee2.getName(), secondEmpDto.getName());
        assertEquals( employee2.getName(), secondEmpDto.getName());
        assertEquals(employee2.getDesignation(), secondEmpDto.getDesignation());
        assertEquals(employee2.getId(), secondEmpDto.getId());
        System.out.println(allEmployees);
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));

        EmployeeDto response = employeeService.getEmployeeById(101L);
        assertNotNull(response);
        assertEquals("Mamun Hossain", response.getName());
        assertEquals(101, response.getId());
        assertEquals("01820702329", response.getMobile());
        assertEquals("Dhaka", response.getAddress());
        assertEquals("Intern", response.getDesignation());
    }

    @Test
    void updateEmployee() {
        when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        EmployeeDto updateEmloyee = new EmployeeDto();

        updateEmloyee.setName("Akash");
        updateEmloyee.setAddress("Mymensingh");
        updateEmloyee.setMobile("0161234567");
        updateEmloyee.setDepartment("SYSTEM DESIGN");
        updateEmloyee.setEmail("akash@gmail.com");
        updateEmloyee.setDesignation("Associate Business Analyst");
        updateEmloyee.setDivision("SOFTWARE ARCHITECTURE & DESIGN");
        updateEmloyee.setSalary(60000L);
        updateEmloyee.setBloodGroup("B+");

        EmployeeDto updateResponse = employeeService.updateEmployee(updateEmloyee, employee1.getId());
        assertNotNull(updateResponse);
        assertEquals("Akash", updateResponse.getName());
        assertEquals("0161234567", updateResponse.getMobile());
        assertEquals("Associate Business Analyst", updateResponse.getDesignation());

        Employee updatedEmployee = employeeRepository.findById(employee1.getId()).orElse(null);
        assertNotNull(updatedEmployee);
        assertEquals("Mymensingh", updatedEmployee.getAddress());
        assertEquals(60000L, updatedEmployee.getSalary());

        System.out.println(updatedEmployee.getId());
    }

    @Test
    void deleteEmployee() {
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));
        doNothing().when(employeeRepository).deleteById(101L);

        ResponseEmpDto response = employeeService.deleteEmployee(101L);
        assertNotNull(response);
        assertEquals(3, response.getResponseCode());
        assertEquals("Deleted successfully", response.getResponseMessage());

        when(employeeRepository.findById(101L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(101L));
        assertFalse(employeeRepository.existsById(101L));
    }

    @Test
    void getByEmail() {
        when(employeeRepository.findByEmail(employee1.getEmail())).thenReturn(employee1);
        EmployeeDto response = employeeService.getByEmail(employee1.getEmail());
        assertNotNull(response);
        assertEquals(employee1.getId(), response.getId());
        assertEquals(employee1.getMobile(), response.getMobile());
        assertEquals(employee1.getDesignation(), response.getDesignation());
        assertEquals(employee1.getDepartment(), response.getDepartment());
        assertEquals(employee1.getDivision(), response.getDivision());
    }

    @Test
    void getByMobile() {
        when(employeeRepository.findByMobile("0190876543")).thenReturn(employee2);
        EmployeeDto response = employeeService.getByMobile("0190876543");
        assertNotNull(response);
        assertEquals(employee2.getId(), response.getId());
        assertEquals(employee2.getEmail(), response.getEmail());
        assertEquals("Amit Roy", response.getName());
        assertEquals("Associate Engineer", response.getDesignation());
        assertEquals("DATABASE ARCHITECTURE", response.getDepartment());
    }

    @Test
    void getByDesignation() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findAllByDesignation("Intern")).thenAnswer(invocationOnMock -> {
            return employees.stream().filter(emp->"Intern".equals(emp.getDesignation())).
                    collect(Collectors.toList());
        });

        List<EmployeeDto> responses = employeeService.getByDesignation("Intern");
        assertNotNull(responses);
        assertEquals(1, responses.size());

        EmployeeDto firstResponse = responses.get(0);
        assertEquals("Mamun Hossain", firstResponse.getName());
        assertEquals("Banking", firstResponse.getDivision());
        assertEquals("iStelar", firstResponse.getDepartment());
    }

    @Test
    void getByDivision() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findAllByDivision("Banking")).thenAnswer(invocationOnMock -> {
           return employees.stream().filter(employee -> "Banking".equals(employee.getDivision())).
                    collect(Collectors.toList());
        });

        List<EmployeeDto> responses = employeeService.getByDivision("Banking");
        assertNotNull(responses);
        assertEquals(1, responses.size());

        EmployeeDto firstResponse = responses.get(0);
        assertEquals("Mamun Hossain", firstResponse.getName());
        assertEquals("Banking", firstResponse.getDivision());
        assertEquals("iStelar", firstResponse.getDepartment());
    }

    @Test
    void getByDepartment() {
       Employee employee3 = new Employee();
        employee3.setId(103L);
        employee3.setName("Amit Roy");
        employee3.setEmail("amitnew@gmail.com");
        employee3.setDesignation("Associate Engineer");
        employee3.setDepartment("DATABASE ARCHITECTURE");
        employee3.setDivision("SOFTWARE ARCHITECTURE & DESIGN");
        employee3.setMobile("01983617031");
        employee3.setAddress("Khulna");
        employee3.setSalary(30000L);
        employee3.setBloodGroup("O+");

        List<Employee> employees = new ArrayList<>();
        employees.add(employee2);
        employees.add(employee3);

        when(employeeRepository.findAllByDepartment("DATABASE ARCHITECTURE")).thenReturn(employees);

        List<EmployeeDto> responses = employeeService.getByDepartment("DATABASE ARCHITECTURE");
        assertNotNull(responses);
        assertEquals(2, responses.size());
        System.out.println(responses);

        EmployeeDto firstResponse = responses.get(0);
        assertEquals("Amit Roy", firstResponse.getName());
        assertEquals("DATABASE ARCHITECTURE", firstResponse.getDepartment());
        assertEquals(102L, firstResponse.getId());

        EmployeeDto secondResponse=responses.get(1);
        assertEquals(103L, secondResponse.getId());
        assertEquals("SOFTWARE ARCHITECTURE & DESIGN", responses.get(1).getDivision());
    }

    @Test
    void getByBloodGroup() {

        List<Employee> employees = new ArrayList<>();
        employees.add(employee2);
        when(employeeRepository.findAllByBloodGroup("O+")).thenReturn(employees);
        //when(employeeRepository.findAllByBloodGroup("O+")).thenReturn(Collections.singletonList(employee2));

        List<CustomizeEmpDto> responses = employeeService.getByBloodGroup("O+");
        assertNotNull(responses);
        assertEquals(1, responses.size());
        CustomizeEmpDto response = responses.get(0);
        assertEquals("Amit Roy", response.getName());
        assertEquals("amit@gmail.com", response.getEmail());
        assertEquals("0190876543", response.getMobile());
    }


    @Test
    void getByDivisionAndDepartment() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findByDivisionDepartment("iStelar", "Banking")).thenAnswer(invocationOnMock -> {
            return employees.stream().filter(employee -> "iStelar".equals(employee.getDepartment())&& "Banking".equals(employee.getDivision())).collect(Collectors.toList());
        });

        //when(employeeRepository.findByDivisionDepartment("iStelar", "Banking")).thenReturn(employees);

        List<EmployeeDto> responses = employeeService.getByDepartmentDivision("iStelar", "Banking");
        assertNotNull(responses);
        assertEquals(1, responses.size());
        System.out.println(responses);
        EmployeeDto response = responses.get(0);
        assertEquals("Mamun Hossain", response.getName());
        assertEquals("iStelar", response.getDepartment());
        assertEquals("Banking", response.getDivision());
    }
}

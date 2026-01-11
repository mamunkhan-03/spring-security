package com.example.mPack.employee.service.impl;

import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.entity.Employee;
import com.example.mPack.employee.repository.EmployeeRepository;
import com.example.mPack.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTestMockito2 {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee1;
    private Employee employee2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<Employee> employeeList;
    private List<EmployeeDto> employeeDtoList;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);  this annotation will be needed if we don't use @ExtendWith(MockitoExtension.class) annotation

        employee1 = new Employee();
        employee1.setId(101L);
        employee1.setName("Mamun Hossain");
        employee1.setEmail("mamun@gmail.com");
        employee1.setDesignation("Intern");
        employee1.setDepartment("iStelar");
        employee1.setDivision("Banking");
        employee1.setMobile("01820702329");
        employee1.setAddress("Dhaka");
        employee1.setSalary(10000L);
        employee1.setBloodGroup("A+");

        employee2 = new Employee();
        employee2.setId(102L);
        employee2.setName("Nuh Salman");
        employee2.setEmail("nuhsalman@gmail.com");
        employee2.setDesignation("Intern");
        employee2.setDepartment("Java Development");
        employee2.setDivision("Engineering");
        employee2.setMobile("01820702330");
        employee2.setAddress("Dhaka");
        employee2.setSalary(10000L);
        employee2.setBloodGroup("A+");

        employeeDto1 = new EmployeeDto();
        employeeDto1.setId(employee1.getId());
        employeeDto1.setName(employee1.getName());
        employeeDto1.setEmail(employee1.getEmail());
        employeeDto1.setDesignation(employee1.getDesignation());
        employeeDto1.setDepartment(employee1.getDepartment());
        employeeDto1.setDivision(employee1.getDivision());
        employeeDto1.setMobile(employee1.getMobile());
        employeeDto1.setAddress(employee1.getAddress());
        employeeDto1.setSalary(employee1.getSalary());
        employeeDto1.setBloodGroup(employee1.getBloodGroup());

        employeeDto2 = new EmployeeDto();
        employeeDto2.setId(employee2.getId());
        employeeDto2.setName(employee2.getName());
        employeeDto2.setEmail(employee2.getEmail());
        employeeDto2.setDesignation(employee2.getDesignation());
        employeeDto2.setDepartment(employee2.getDepartment());
        employeeDto2.setDivision(employee2.getDivision());
        employeeDto2.setMobile(employee2.getMobile());
        employeeDto2.setAddress(employee2.getAddress());
        employeeDto2.setSalary(employee2.getSalary());
        employeeDto2.setBloodGroup(employee2.getBloodGroup());

        employeeList = Arrays.asList(employee1, employee2);
        employeeDtoList = Arrays.asList(employeeDto1, employeeDto2);
    }

    @Test
    void createEmployeeTest() {
        when(mapper.map(any(EmployeeDto.class), eq(Employee.class))).thenReturn(employee1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto1);

        CustomizeEmpDto result = employeeService.createEmployee(employeeDto1);
        assertNotNull(result);
        assertEquals("Mamun Hossain", result.getName());
        assertEquals("mamun@gmail.com", result.getEmail());
    }

    @Test
    void getAllEmployeeTest() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                .thenReturn(employeeDto1, employeeDto2);
        List<EmployeeDto> result = employeeService.getAllEmployee();
        assertEquals(2, result.size());
        assertEquals("nuhsalman@gmail.com",result.get(1).getEmail());
        assertEquals("01820702329", result.get(0).getMobile());

    }

    @Test
    void getEmployeeByIdTest() {
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto1);
        EmployeeDto result = employeeService.getEmployeeById(101L);
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        System.out.println(result.getDesignation());
    }


    @Test
    void getByEmailTest() {
        when(employeeRepository.findByEmail("mamun@gmail.com")).thenReturn(employee1);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto1);
        EmployeeDto result = employeeService.getByEmail("mamun@gmail.com");
        assertNotNull(result);
        assertEquals("mamun@gmail.com", result.getEmail());
    }

    @Test
    void getByMobileTest() {
        when(employeeRepository.findByEmail("01820702330")).thenReturn(employee2);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto2);
        EmployeeDto result = employeeService.getByEmail("01820702330");
        assertNotNull(result);
        assertEquals("nuhsalman@gmail.com", result.getEmail());
    }

    @Test
    void updateEmployeeTest() {
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto1);
        EmployeeDto result = employeeService.updateEmployee(employeeDto1, 101L);
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        System.out.println(result);
    }

    @Test
    void deleteEmployeeTest() {
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));
        ResponseEmpDto result = employeeService.deleteEmployee(101L);
        verify(employeeRepository, times(1)).delete(employee1);
        assertEquals(3, result.getResponseCode());
        assertEquals("Deleted successfully", result.getResponseMessage());
    }


    @Test
    void getByDesignationTest() {
        when(employeeRepository.findAllByDesignation("Intern")).thenReturn(employeeList);
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                .thenReturn(employeeDto1, employeeDto2);
        List<EmployeeDto> result = employeeService.getByDesignation("Intern");
        assertEquals(2, result.size());
        assertEquals(result.get(0).getEmail(), "mamun@gmail.com");
        assertEquals(result.get(1).getEmail(), "nuhsalman@gmail.com");
    }

    @Test
    void getByDivisionTest() {
        when(employeeRepository.findAllByDivision("Banking")).thenReturn(Collections.singletonList(employee1));
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                .thenReturn(employeeDto1);
        List<EmployeeDto> result = employeeService.getByDivision("Banking");
        assertEquals(1, result.size());
        assertEquals(result.get(0).getName(), employee1.getName());
        System.out.println(result.get(0).getId());
    }

    @Test
    void getByDepartmentTest() {
        when(employeeRepository.findAllByDepartment("Java Development")).thenReturn(Collections.singletonList(employee2));
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                .thenReturn( employeeDto2);
        List<EmployeeDto> result = employeeService.getByDepartment("Java Development");
        assertEquals(1, result.size());
        System.out.println(result.get(0).getName());
        System.out.println(result.get(0).getId());
    }

    @Test
    void getByBloodGroupTest() {
            when(employeeRepository.findAllByBloodGroup("A+")).thenReturn(employeeList);
            lenient().when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                    .thenReturn(employeeDto1, employeeDto2);
            List<CustomizeEmpDto> result = employeeService.getByBloodGroup("A+");
            assertEquals(2, result.size());
            System.out.println(result.get(0).getEmail());
            System.out.println(result.get(1).getMobile());
    }

    @Test
    void getByDepartmentDivisionTest() {
        when(employeeRepository.findByDivisionDepartment("iStelar", "Banking")).
                thenReturn(Collections.singletonList(employee1));
        when(mapper.map(any(Employee.class), eq(EmployeeDto.class)))
                .thenReturn(employeeDto1);
        List<EmployeeDto> result = employeeService.getByDepartmentDivision("iStelar", "Banking");
        assertEquals(1, result.size());
        System.out.println(result.get(0).getDepartment());
        System.out.println(result.get(0).getDivision());
    }
}

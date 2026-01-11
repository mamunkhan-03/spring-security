package com.example.mPack.employee.repository;
import com.example.mPack.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryTestMockito {
    @Mock
    EmployeeRepository employeeRepository;
    private Employee employee1;
    private Employee employee2;
    @BeforeEach
    public void setup() {
        employee1 = Employee.builder()
                .id(1L).name("Bijoy Ahmed")
                .email("bijoy@gmail.com")
                .designation("Associate Engineer")
                .department("JAVA DEV. PLATFORMS")
                .division("SOFTWARE ENGINEERING")
                .mobile("01711289334")
                .salary(60000L)
                .address("Rajshahi")
                .bloodGroup("B+")
                .build();
        employee2 = Employee.builder()
                .id(2L).name("Wakil Ahmed")
                .email("wakil@gmail.com")
                .designation("Data Analyst")
                .department("Analytics")
                .division("Research")
                .mobile("01722334445")
                .salary(50000L)
                .address("Dhaka")
                .bloodGroup("O+")
                .build();
    }
    @Test
    void saveEmployeeTest() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee response = employeeRepository.save(employee1);
        assertNotNull(response, "The saved employee should not be null");
        assertNotNull(response.getId(), "The saved employee's ID should not be null");
        assertEquals(employee1.getEmail(), response.getEmail());
        assertEquals(employee1.getName(), response.getName());
        assertEquals(employee1.getMobile(), response.getMobile());
    }

    @Test
    void findByIdTest() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));

        Employee foundEmployee = employeeRepository.findById(2L).orElse(null);
        assertNotNull(foundEmployee, "The employee should be found by ID");
        assertEquals("Wakil Ahmed", foundEmployee.getName());
    }

    @Test
    void testFindByIdNotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Employee> foundEmployee = employeeRepository.findById(999L);
        assertFalse(foundEmployee.isPresent(), "Employee with ID 999 should not be found");
    }

    @Test
    void findAllTest() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        List<Employee> employees = employeeRepository.findAll();
        assertFalse(employees.isEmpty(), "The employee list should not be empty");
        assertTrue(employees.size() > 1, "The employee list should contain at least 2 employees");
    }

    @Test
    void findByEmailTest() {
        when(employeeRepository.findByEmail("bijoy@gmail.com")).thenReturn(employee1);

        Employee foundEmployee = employeeRepository.findByEmail("bijoy@gmail.com");
        assertNotNull(foundEmployee, "Employee with the given email should be found");
        assertEquals("Bijoy Ahmed", foundEmployee.getName());
        assertEquals("Rajshahi", foundEmployee.getAddress());
    }

    @Test
    void testFindByEmailNotFound() {
        when(employeeRepository.findByEmail("none@gmail.com")).thenReturn(null);

        Employee foundEmployee = employeeRepository.findByEmail("none@gmail.com");
        assertNull(foundEmployee, "Employee with the given email should not be found");
    }

    @Test
    void findByMobileTest() {
        when(employeeRepository.findByMobile("01722334445")).thenReturn(employee2);

        Employee foundEmployee = employeeRepository.findByMobile("01722334445");
        assertNotNull(foundEmployee, "Employee with the given mobile should be found");
        assertEquals("Wakil Ahmed", foundEmployee.getName());
        assertEquals(employee2.getDepartment(), foundEmployee.getDepartment());
    }

    @Test
    void testFindByMobileNotFound() {
        when(employeeRepository.findByMobile("0000000000")).thenReturn(null);

        Employee foundEmployee = employeeRepository.findByMobile("0000000000");
        assertNull(foundEmployee, "Employee with the given mobile should not be found");
    }

    @Test
    void findAllByDesignationTest() {
        when(employeeRepository.findAllByDesignation("Associate Engineer")).thenReturn(List.of(employee1));

        List<Employee> associateEngineers = employeeRepository.findAllByDesignation("Associate Engineer");
        assertFalse(associateEngineers.isEmpty());
        assertEquals(1, associateEngineers.size(), "Expected 1 Associate Engineer");
        assertEquals("Bijoy Ahmed", associateEngineers.get(0).getName());
    }

    @Test
    void findAllByDepartmentTest() {
        when(employeeRepository.findAllByDepartment("JAVA DEV. PLATFORMS")).thenReturn(List.of(employee1));

        List<Employee> responses = employeeRepository.findAllByDepartment("JAVA DEV. PLATFORMS");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
    }

    @Test
    void findAllByDivisionTest() {
        when(employeeRepository.findAllByDivision("SOFTWARE ENGINEERING")).thenReturn(List.of(employee1));

        List<Employee> responses = employeeRepository.findAllByDivision("SOFTWARE ENGINEERING");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
    }

    @Test
    void findAllByBloodGroupTest() {
        when(employeeRepository.findAllByBloodGroup("B+")).thenReturn(List.of(employee1));

        List<Employee> response = employeeRepository.findAllByBloodGroup("B+");
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("Bijoy Ahmed", response.get(0).getName());
    }

    @Test
    void findByDivisionDepartmentTest() {
        when(employeeRepository.findByDivisionDepartment("SOFTWARE ENGINEERING", "JAVA DEV. PLATFORMS")).
                thenReturn(List.of(employee1));
        List<Employee> employees = employeeRepository.findByDivisionDepartment("SOFTWARE ENGINEERING", "JAVA DEV. PLATFORMS");
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals("Bijoy Ahmed", employees.get(0).getName());
    }
}

package com.example.mPack.employee.repository;
import com.example.mPack.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class EmployeeRepositoryTestDataJpa {
    @Autowired
    EmployeeRepository employeeRepository;
    private Employee employee1;
    private Employee employee2;
    @BeforeEach
    public void setup() {
        employee1 = Employee.builder()
                .name("Bijoy Ahmed")
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
                .name("Wakil Ahmed")
                .email("wakil@gmail.com")
                .designation("Data Analyst")
                .department("Analytics")
                .division("Research")
                .mobile("01722334445")
                .salary(50000L)
                .address("Dhaka")
                .bloodGroup("O+")
                .build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }

    @Test
    void saveEmployeeTest() {
        Employee response = employeeRepository.save(employee1);
        assertNotNull(response.getId(), "The saved employee's ID should not be null");
        assertEquals(employee1.getEmail(), response.getEmail());
        assertEquals(employee1.getName(), response.getName());
        assertEquals(employee1.getMobile(), response.getMobile());
        System.out.println("Save employee successfully");
        System.out.println(response);
    }

    @Test
    void findByIdTest() {
        Employee foundEmployee = employeeRepository.findById(employee2.getId()).orElse(null);
        assertNotNull(foundEmployee, "The employee should be found by ID");
        assertEquals("Wakil Ahmed", foundEmployee.getName());
        System.out.println("Find by ID test successful");
        System.out.println(foundEmployee);
        System.out.println(foundEmployee.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Employee> foundEmployee = employeeRepository.findById(999L);
        assertFalse(foundEmployee.isPresent(), "Employee with ID 999 should not be found");
    }


    @Test
    void findAllTest() {
        List<Employee> employees = employeeRepository.findAll();
        assertFalse(employees.isEmpty(), "The employee list should not be empty");
        assertTrue(employees.size() >= 2, "The employee list should contain at least 2 employees");
        System.out.println("Find all employees test successful");
        System.out.println(employees);
    }

    @Test
    void findByEmailTest() {
        Employee foundEmployee = employeeRepository.findByEmail("bijoy@gmail.com");
        assertNotNull(foundEmployee, "Employee with the given email should be found");
        assertNotNull(foundEmployee.getId());
        assertEquals("Bijoy Ahmed", foundEmployee.getName());
        assertEquals("Rajshahi", foundEmployee.getAddress());
        System.out.println("Find by email test successful");
        System.out.println(foundEmployee);
    }

    @Test
    void testFindByEmailNotFound() {
        Employee foundEmployee = employeeRepository.findByEmail("none@gmail.com");
        assertNull(foundEmployee, "Employee with the given email should not be found");
    }

    @Test
    void findByMobileTest() {
        Employee foundEmployee = employeeRepository.findByMobile("01722334445");
        assertNotNull(foundEmployee, "Employee with the given mobile should be found");
        assertNotNull(foundEmployee.getId());
        assertEquals("Wakil Ahmed", foundEmployee.getName());
        assertEquals(employee2.getDepartment(), foundEmployee.getDepartment());
        System.out.println("Find by mobile test successful");
        System.out.println(foundEmployee);
    }

    @Test
    void testFindByMobileNotFound() {
        Employee foundEmployee = employeeRepository.findByMobile("0000000000");
        assertNull(foundEmployee, "Employee with the given mobile should not be found");
    }

    @Test
    void findAllByDesignationTest() {
        List<Employee> associateEngineers = employeeRepository.findAllByDesignation("Associate Engineer");
        assertFalse(associateEngineers.isEmpty());
        assertEquals(1, associateEngineers.size(), "Expected 1 Associate Engineer");
        assertEquals("Bijoy Ahmed", associateEngineers.get(0).getName());
        assertEquals(employee1.getEmail(), associateEngineers.get(0).getEmail());
        System.out.println("Find by designation test successful");
        System.out.println(associateEngineers);
    }

    @Test
    void findAllByDepartmentTest() {
        List<Employee> responses = employeeRepository.findAllByDepartment("JAVA DEV. PLATFORMS");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
        System.out.println("Find by department test successful");
        System.out.println(responses);
    }

    @Test
    void findAllByDivisionTest() {
        List<Employee> responses = employeeRepository.findAllByDivision("SOFTWARE ENGINEERING");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
        System.out.println("Find by division test successful");
        System.out.println(responses);
    }

    @Test
    void findAllByBloodGroupTest() {
        List<Employee> response = employeeRepository.findAllByBloodGroup("B+");
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("Bijoy Ahmed", response.get(0).getName());
        System.out.println("Find by blood group test successful");
        System.out.println(response);
    }

    @Test
    void findByDivisionDepartmentTest() {
        List<Employee> employees = employeeRepository.findByDivisionDepartment("SOFTWARE ENGINEERING", "JAVA DEV. PLATFORMS");
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals("Bijoy Ahmed", employees.get(0).getName());
        System.out.println("Find by division and department test successful");
        System.out.println(employees);
    }
}

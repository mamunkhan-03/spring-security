package com.example.mPack.employee.repository;
import com.example.mPack.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {
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
    }
    @Test
    void saveEmployeeTest() {
        Employee response = employeeRepository.save(employee1);
        assertNotNull(response.getId(), "The saved employee's ID should not be null");
        assertEquals("Bijoy Ahmed", response.getName());
        assertEquals("bijoy@gmail.com", response.getEmail());
        assertEquals("01711289334", response.getMobile());
        System.out.println(response);
    }



    @Test
    void findByIdTest() {
        employeeRepository.save(employee2);
        Employee foundEmployee = employeeRepository.findById(employee2.getId()).orElse(null);
        assertNotNull(foundEmployee, "The employee should be found by ID");
        assertEquals("Wakil Ahmed", foundEmployee.getName());
        assertNotNull(foundEmployee.getId());
        System.out.println("Find by ID test successful");
        System.out.println(foundEmployee);
    }

    @Test
    public void findByIdNotFound() {
        Optional<Employee> foundEmployee = employeeRepository.findById(999L);
        assertFalse(foundEmployee.isPresent());
    }

    @Test
    void findAllTest() {
        List<Employee> employees = employeeRepository.findAll();
        assertFalse(employees.isEmpty(), "The employee list should not be empty");
        assertTrue(employees.size()>5, "The employee list should contain at least 2 employees");
        assertEquals(employees.size(), 6);
        System.out.println(employees.size());
        System.out.println("Find all employees test successful");
    }

    @Test
    void findByEmailTest() {
        Employee foundEmployee = employeeRepository.findByEmail("bijoy@gmail.com");
        assertNotNull(foundEmployee);
        assertNotNull(foundEmployee.getId());
        assertEquals("Bijoy Ahmed", foundEmployee.getName());
        assertEquals("Rajshahi", foundEmployee.getAddress());
        System.out.println("Find by email test successful");
        System.out.println(foundEmployee);
    }

    @Test public void findByEmailNotFound() {
        Employee foundEmployee = employeeRepository.findByEmail("none@gmail.com");
        assertNull(foundEmployee);
    }

    @Test
    void findByMobileTest() {
        Employee foundEmployee = employeeRepository.findByMobile("01722334445");
        assertNotNull(foundEmployee);
        assertNotNull(foundEmployee.getId());
        assertEquals("Wakil Ahmed", foundEmployee.getName());
        assertEquals(employee2.getDepartment(), foundEmployee.getDepartment());
        System.out.println("Find by mobile test successful");
        System.out.println(foundEmployee);
    }

    @Test public void findByMobileNotFound() {
        Employee foundEmployee = employeeRepository.findByMobile("0000000000");
        assertNull(foundEmployee);
    }

    @Test
    void findAllByDesignationTest() {
        List<Employee> softwareEngineers = employeeRepository.findAllByDesignation("Associate Engineer");
        assertFalse(softwareEngineers.isEmpty());
        assertEquals(1, softwareEngineers.size(), "Expected 1 Software Engineer");
        assertEquals("Bijoy Ahmed", softwareEngineers.get(0).getName());
        assertEquals(employee1.getEmail(), softwareEngineers.get(0).getEmail() );
        System.out.println("Find by designation test successful");
    }

    @Test
    void findAllByDepartmentTest() {
        List<Employee> responses = employeeRepository.findAllByDepartment("JAVA DEV. PLATFORMS");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        //There are one employee in the table of IT department
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
        assertEquals(employee1.getEmail(),responses.get(0).getEmail());
        System.out.println(responses.get(0));
        System.out.println("Find by department test successful");
    }

    @Test
    void findAllByDivisionTest() {
        List<Employee> responses = employeeRepository.findAllByDivision("SOFTWARE ENGINEERING");
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Bijoy Ahmed", responses.get(0).getName());
        assertEquals(employee1.getMobile(), responses.get(0).getMobile());
        assertEquals(employee1.getSalary(), responses.get(0).getSalary());
    }

    @Test
    void findAllByBloodGroupTest() {
        List<Employee> response= employeeRepository.findAllByBloodGroup("B+");
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        System.out.println(response);
    }

    @Test
    void findByDivisionDepartmentTest() {
        List<Employee> employees = employeeRepository.findByDivisionDepartment("Research", "Analytics");
        assertFalse(employees.isEmpty());
        System.out.println(employees);

    }

}


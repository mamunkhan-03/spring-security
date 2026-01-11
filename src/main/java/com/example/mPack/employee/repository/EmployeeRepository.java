package com.example.mPack.employee.repository;
import com.example.mPack.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail (String email);

    @Query(value = "select e from Employee e where e.mobile=:mobile")
    Employee findByMobile (@Param("mobile")String mobile);
    List<Employee> findAllByDesignation(String designation);

    List<Employee> findAllByDepartment(String department);
    List<Employee> findAllByDivision(String division);
    List<Employee> findAllByBloodGroup (String bloodGroup);
    @Query("select e from Employee e where e.division=:division and e.department=:department")
    List<Employee> findByDivisionDepartment(@Param("department") String department,@Param("division") String division);

}







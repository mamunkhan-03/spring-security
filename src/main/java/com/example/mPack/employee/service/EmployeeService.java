package com.example.mPack.employee.service;

import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import java.util.List;
public interface EmployeeService {

    CustomizeEmpDto createEmployee (EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee ();
    EmployeeDto getEmployeeById (Long id);
    EmployeeDto updateEmployee (EmployeeDto employeeDto, Long id);
    ResponseEmpDto updateEmployeeByPatch (EmployeeDto employeeDto, Long id);
    ResponseEmpDto deleteEmployee (Long id);
    EmployeeDto getByEmail (String email);
    EmployeeDto getByMobile(String mobile);
    List<EmployeeDto> getByDesignation (String designation);
    List<EmployeeDto>  getByDivision (String division);
    List<EmployeeDto> getByDepartment (String department);
    List<CustomizeEmpDto> getByBloodGroup (String bloodGroup);
    List<EmployeeDto> getByDepartmentDivision (String department, String division);

}
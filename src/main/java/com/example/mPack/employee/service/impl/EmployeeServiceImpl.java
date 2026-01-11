package com.example.mPack.employee.service.impl;

import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.entity.Employee;
import com.example.mPack.employee.repository.EmployeeRepository;
import com.example.mPack.employee.service.EmployeeService;
import com.example.mPack.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper=mapper;
    }
    @Override
    public CustomizeEmpDto createEmployee(EmployeeDto employeeDto) {
        Employee employee=employeeRepository.save(mapToEntity(employeeDto));
        CustomizeEmpDto customizeEmpDto=new CustomizeEmpDto(mapToDto(employee));
        return customizeEmpDto;
    }
    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees= employeeRepository.findAll();  //collect all the employees
        return employees.stream().map(employee -> mapToDto(employee)).collect(Collectors.toList());
    }
    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee", "id",id));
        return mapToDto(employee);
    }
    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee", "id",id));

        employee.setName(employeeDto.getName());
        employee.setAddress(employeeDto.getAddress());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setDivision(employeeDto.getDivision());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setBloodGroup(employeeDto.getBloodGroup());
        employee.setMobile(employeeDto.getMobile());

        Employee updatedEmployee=employeeRepository.save(employee);
        return mapToDto(updatedEmployee);
    }
    @Override
    public ResponseEmpDto updateEmployeeByPatch(EmployeeDto employeeDto, Long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee", "id",id));
        ResponseEmpDto responseEmpDto=new ResponseEmpDto();
        employee.setName(employeeDto.getName());
        employee.setAddress(employeeDto.getAddress());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setDivision(employeeDto.getDivision());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setBloodGroup(employeeDto.getBloodGroup());
        employee.setMobile(employeeDto.getMobile());
        try {
            Employee updateEmployee = employeeRepository.save(employee);
            responseEmpDto.setResponseCode(2);
            responseEmpDto.setResponseMessage("Updated successfully");
        }
        catch (Exception exce){
            exce.printStackTrace();
        }
        return responseEmpDto;
    }
    @Override
    public ResponseEmpDto deleteEmployee(Long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee", "id",id));
        ResponseEmpDto responseEmpDto=new ResponseEmpDto();
        try {
            employeeRepository.delete(employee);
            responseEmpDto.setResponseCode(3);
            responseEmpDto.setResponseMessage("Deleted successfully");
        } catch (Exception exce){
            exce.printStackTrace();
        }
        return responseEmpDto;}
    @Override
    public EmployeeDto getByEmail(String email) {
       return mapToDto(employeeRepository.findByEmail(email));
    }

    @Override
    public EmployeeDto getByMobile(String mobile) {
        return mapToDto(employeeRepository.findByMobile(mobile));
    }
    @Override
    public List<EmployeeDto> getByDesignation(String designation) {
        List<Employee> employees=employeeRepository.findAllByDesignation(designation);
        List<EmployeeDto> employeeDtos= employees.stream().map(employee -> mapToDto(employee)).collect(Collectors.toList());
        return employeeDtos;
    }
    @Override
    public List<EmployeeDto> getByDivision(String division) {
        List<Employee> employees =employeeRepository.findAllByDivision(division);
        return employees.stream().map(employee -> mapToDto(employee)).collect(Collectors.toList());
    }
    @Override
    public List<EmployeeDto> getByDepartment(String department) {
        List<Employee> employees=employeeRepository.findAllByDepartment(department);
        return employees.stream().map(employee -> mapToDto(employee)).collect(Collectors.toList());
    }
    @Override
    public List<CustomizeEmpDto> getByBloodGroup(String bloodGroup) {
        List<Employee> employees =employeeRepository.findAllByBloodGroup(bloodGroup);
        return employees.stream()
                .map(employee -> new CustomizeEmpDto(
                        employee.getId(),employee.getName(),
                        employee.getEmail(),
                        employee.getMobile())).
                collect(Collectors.toList()); }
    @Override
    public List<EmployeeDto> getByDepartmentDivision(String department, String division) {
        List<Employee> responses=employeeRepository.findByDivisionDepartment(department,division);
        return responses.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    // Convert Entity into DTO
    private EmployeeDto mapToDto(Employee employee){
        return mapper.map(employee, EmployeeDto.class);
    }
    //Convert DTO into Entity
    private Employee mapToEntity(EmployeeDto employeeDto){
        return mapper.map(employeeDto, Employee.class);
    }
}

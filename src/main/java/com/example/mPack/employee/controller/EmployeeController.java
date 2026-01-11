package com.example.mPack.employee.controller;
import com.example.mPack.employee.dto.CustomizeEmpDto;
import com.example.mPack.employee.dto.EmployeeDto;
import com.example.mPack.employee.dto.ResponseEmpDto;
import com.example.mPack.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    EmployeeService employeeService;
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<CustomizeEmpDto> createEmployee (@RequestBody EmployeeDto employeeDto) {
        CustomizeEmpDto employeeResponse = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployee (){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById (@PathVariable (name="id")Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity <EmployeeDto>  updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                        @PathVariable (name="id")Long id ){
        System.out.println("employeeDto = " + employeeDto);
        EmployeeDto employeeResponse=  employeeService.updateEmployee(employeeDto, id);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity <ResponseEmpDto>  updateEmployee2(@RequestBody EmployeeDto employeeDto,
                                                            @PathVariable (name="id")Long id ){
        ResponseEmpDto employeeResponse=  employeeService.updateEmployeeByPatch(employeeDto, id);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEmpDto> deleteEmployee (@PathVariable (name="id")Long id ){
        ResponseEmpDto employeeResponse  =employeeService.deleteEmployee(id);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDto> getByEmail (@PathVariable (name="email")String email){
     return new ResponseEntity<>(employeeService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<EmployeeDto> getByMobile (@PathVariable (name="mobile")String mobile){
        return new ResponseEntity<>(employeeService.getByMobile(mobile), HttpStatus.OK);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getByDepartment (@PathVariable (name = "department")String department){
        List<EmployeeDto> responses=employeeService.getByDepartment(department);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/designation/{designation}")
    public ResponseEntity<List<EmployeeDto>> getByDesignation (@PathVariable (name = "designation")String designation){
        return new ResponseEntity<>(employeeService.getByDesignation(designation),HttpStatus.OK);
    }

    @GetMapping("/division/{division}")
    public ResponseEntity<List<EmployeeDto>> getByDivision (@PathVariable (name = "division")String division){
        List<EmployeeDto> responses=employeeService.getByDivision(division);
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    @GetMapping("/bloodGroup/{bloodGroup}")
    public ResponseEntity<List<CustomizeEmpDto>> getByBloogGroup (@PathVariable (name = "bloodGroup")String bloodGroup){
        List<CustomizeEmpDto> responses=employeeService.getByBloodGroup(bloodGroup);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("twoProperties/{department},{division}")
    public ResponseEntity<List<EmployeeDto>> getByDepartmentDivision (@PathVariable (name = "department")String department,
                                                                      @PathVariable (name = "division") String division){
        List<EmployeeDto> responses=employeeService.getByDepartmentDivision(department, division);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
  }

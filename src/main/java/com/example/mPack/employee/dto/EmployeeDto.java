package com.example.mPack.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private String designation;
    private String department;
    private String division;
    private String mobile;
    private Long salary;
    private String address;
    private String bloodGroup;

}

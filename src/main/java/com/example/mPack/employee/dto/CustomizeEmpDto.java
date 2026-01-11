package com.example.mPack.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomizeEmpDto {
    private Long id;
    private String name;
    private String email;
    private String mobile;

    public CustomizeEmpDto(EmployeeDto employeeDto) {
        this.id = employeeDto.getId();
        this.name = employeeDto.getName();
        this.email = employeeDto.getEmail();
        this.mobile = employeeDto.getMobile();
    }

}

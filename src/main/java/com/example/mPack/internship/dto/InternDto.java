package com.example.mPack.internship.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class InternDto {

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

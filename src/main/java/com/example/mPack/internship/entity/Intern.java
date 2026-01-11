package com.example.mPack.internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name="Internship"  , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"mobile"})
})

public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(name = "irn_seq", sequenceName = "intern_sequence", allocationSize = 1)
    @Column (name="id")
    private Long id;

    @Column (name="name")
    private String name;

    @Column (name="email" , nullable = false)
    private String email;

    @Column (name="designation")
    private String designation;

    @Column(name="department")
    private String department;

    @Column(name="division")
    private String division;

    @Column(name="mobile")
    private String mobile;

    @Column (name="salary")
    private Long salary;

    @Column(name="address")
    private String address;

    @Column(name="blood_group")
    private String bloodGroup;
}

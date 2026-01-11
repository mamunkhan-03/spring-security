package com.example.mPack.employee.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table (name="employeeManagement", uniqueConstraints = {
        @UniqueConstraint (columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"mobile"})
})
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
        @SequenceGenerator(name = "emp_seq", sequenceName = "employee_sequence", allocationSize = 1)
        @Column (name="id")
        private Long id;

        @Column (name="name")
        private String name;

        @Column (name="email" )
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

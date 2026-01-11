package com.example.mPack.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table (name="students", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mobile"}),
        @UniqueConstraint(columnNames = {"email"})
}
)

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(name = "stu_seq", sequenceName = "student_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private String department;

    private String address;

    private String mobile ;

    private String email;

    private Long rank;
}

package com.payroll.employee_mangement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private String designation;
    private LocalDate dateOfJoining;
    private LocalDate dateOfExit;
    private List<EmployeeEvent> events;
}

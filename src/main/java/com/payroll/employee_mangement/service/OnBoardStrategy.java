package com.payroll.employee_mangement.service;

import com.payroll.employee_mangement.domain.Employee;
import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.repository.EventStrategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class OnBoardStrategy implements EventStrategy {
    @Override
    public void process(EmployeeEvent event, PayrollProcessor payrollProcessor) {
        Employee employee = new Employee(
                event.getEmpId(),
                event.getEmpFName(),
                event.getEmpLName(),
                event.getDesignation(),
                LocalDate.parse(event.getValue(), DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                null,
                new ArrayList<>()
        );
        employee.getEvents().add(event);
        payrollProcessor.addEmployee(employee);

    }
}

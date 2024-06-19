package com.payroll.employee_mangement.service;

import com.payroll.employee_mangement.domain.Employee;
import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.repository.EventStrategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExitStrategy implements EventStrategy {
    @Override
    public void process(EmployeeEvent event, PayrollProcessor payrollProcessor) {
        Employee employee = payrollProcessor.getEmployee(event.getEmpId());
        if (employee != null) {
            employee.setDateOfExit(LocalDate.parse(event.getValue(), DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            employee.getEvents().add(event);
        }
    }
}

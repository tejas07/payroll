package com.payroll.employee_mangement.service;

import com.payroll.employee_mangement.domain.Employee;
import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.repository.EventStrategy;

public class ReimbursementStrategy implements EventStrategy {
    @Override
    public void process(EmployeeEvent event, PayrollProcessor payrollProcessor) {
        Employee employee = payrollProcessor.getEmployee(event.getEmpId());
        if (employee != null) {
            employee.getEvents().add(event);
        }
    }
}

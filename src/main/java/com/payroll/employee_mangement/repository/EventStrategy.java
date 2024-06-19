package com.payroll.employee_mangement.repository;

import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.service.PayrollProcessor;

public interface EventStrategy {
    void process(EmployeeEvent event, PayrollProcessor payrollProcessor);
}

package com.payroll.employee_mangement.controller;

import com.payroll.employee_mangement.domain.Employee;
import com.payroll.employee_mangement.service.PayrollProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    PayrollProcessor payrollProcessor;

    @PostMapping("/upload")
    public ResponseEntity employeeEventParser(@RequestParam("file") MultipartFile file) {
        try {
            payrollProcessor.processFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("File Successfully uploaded");
    }

    @GetMapping("/total-employees")
    public int getTotalEmployees() {
        return payrollProcessor.getTotalEmployees();
    }

    @GetMapping("/monthly/report")
    public Map<Month, Double> getMonthlyReport() {
        return payrollProcessor.getMonthlyTotalAmount();
    }

    @GetMapping("/monthly/salary-details")
    public Map<Month, Double> getMonthlySalaryReport() {
        return payrollProcessor.getMonthlySalaryReport();
    }

    @GetMapping("/monthly/exit-list/{month}")
    public List<Employee> getExitSalaryReport(@PathVariable("month") int month) {
        return payrollProcessor.getExitedEmployeesByMonth(month);
    }
}

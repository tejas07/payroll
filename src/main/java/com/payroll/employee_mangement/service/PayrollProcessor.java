package com.payroll.employee_mangement.service;

import com.payroll.employee_mangement.domain.Employee;
import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.repository.EventStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayrollProcessor {
    @Autowired
    FileParser fileParser;

    private List<Employee> employees;
    private Map<String, Employee> employeeMap;
    private Map<String, EventStrategy> eventStrategies;

    public PayrollProcessor(List<EmployeeEvent> events) {
        this.employees = new ArrayList<>();
        this.employeeMap = new HashMap<>();
        this.eventStrategies = new HashMap<>();
        initializeStrategies();
        processEvents(events);
    }

    public void processFile(MultipartFile file) throws IOException {
        List<EmployeeEvent> events = fileParser.parseFile(file);
        processEvents(events);
    }

    @PostConstruct
    private void initializeStrategies() {
        eventStrategies.put("ONBOARD", new OnBoardStrategy());
        eventStrategies.put("SALARY", new SalaryStrategy());
        eventStrategies.put("BONUS", new BonusStrategy());
        eventStrategies.put("EXIT", new ExitStrategy());
        eventStrategies.put("REIMBURSEMENT", new ReimbursementStrategy());
    }

    private void processEvents(List<EmployeeEvent> events) {
        for (EmployeeEvent event : events) {
            EventStrategy strategy = eventStrategies.get(event.getEvent());
            if (strategy != null) {
                strategy.process(event, this);
            }
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employeeMap.put(employee.getEmpId(), employee);
    }

    public Employee getEmployee(String empId) {
        return employeeMap.get(empId);
    }

    public int getTotalEmployees() {
        return employees.size();
    }

    public List<Employee> getJoinedEmployeesByMonth(Month month) {
        return employees.stream()
                .filter(emp -> emp.getDateOfJoining().getMonth() == month)
                .collect(Collectors.toList());
    }

    public List<Employee> getExitedEmployeesByMonth(int month) {
        return employees.stream()
                .filter(emp -> emp.getDateOfExit() != null && emp.getDateOfExit().getMonth() == Month.of(month))
                .collect(Collectors.toList());
    }

    public Map<Month, Double> getMonthlySalaryReport() {
        Map<Month, Double> report = new HashMap<>();
        for (Employee employee : employees) {
            for (EmployeeEvent event : employee.getEvents()) {
                if (event.getEvent().equals("SALARY")) {
                    Month month = LocalDate.parse(event.getEventDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).getMonth();
                    report.put(month, report.getOrDefault(month, 0.0) + Double.parseDouble(event.getValue()));
                }
            }
        }
        return report;
    }

    public Map<String, Double> getEmployeeFinancialReport() {
        Map<String, Double> report = new HashMap<>();
        for (Employee employee : employees) {
            double total = 0.0;
            for (EmployeeEvent event : employee.getEvents()) {
                switch (event.getEvent()) {
                    case "SALARY":
                    case "BONUS":
                    case "REIMBURSEMENT":
                        total += Double.parseDouble(event.getValue());
                        break;
                }
            }
            report.put(employee.getEmpId(), total);
        }
        return report;
    }

    public Map<Month, Double> getMonthlyTotalAmount() {
        Map<Month, Double> report = new HashMap<>();
        for (Employee employee : employees) {
            for (EmployeeEvent event : employee.getEvents()) {
                if (Arrays.asList("SALARY", "BONUS", "REIMBURSEMENT").contains(event.getEvent())) {
                    Month month = LocalDate.parse(event.getEventDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).getMonth();
                    report.put(month, report.getOrDefault(month, 0.0) + Double.parseDouble(event.getValue()));
                }
            }
        }
        return report;
    }

    public List<EmployeeEvent> getYearlyFinancialReport(int year) {
        List<EmployeeEvent> report = new ArrayList<>();
        for (Employee employee : employees) {
            for (EmployeeEvent event : employee.getEvents()) {
                if (LocalDate.parse(event.getEventDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).getYear() == year) {
                    report.add(event);
                }
            }
        }
        return report;
    }
}
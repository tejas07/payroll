package com.payroll.employee_mangement.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeEvent {
    private int sequenceNo;
    private String empId;
    private String empFName;
    private String empLName;
    private String designation;
    private String event;
    private String value;
    private String eventDate;
    private String notes;
}

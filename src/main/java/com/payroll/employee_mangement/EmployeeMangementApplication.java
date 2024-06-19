package com.payroll.employee_mangement;

import com.payroll.employee_mangement.domain.EmployeeEvent;
import com.payroll.employee_mangement.service.FileParser;
import com.payroll.employee_mangement.service.PayrollProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
public class EmployeeMangementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeMangementApplication.class, args);
        FileParser parser = new FileParser();
        /*try {
            List<EmployeeEvent> events = parser.parseFile("/home/tejas/Downloads/software-resources/emp.txt");
            PayrollProcessor processor = new PayrollProcessor(events);

            // Output the results
            System.out.println("Total number of employees: " + processor.getTotalEmployees());
            // Output other required reports similarly

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}

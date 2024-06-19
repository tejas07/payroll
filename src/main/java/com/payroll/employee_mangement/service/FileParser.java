package com.payroll.employee_mangement.service;


import com.payroll.employee_mangement.domain.EmployeeEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileParser {

    /*public List<EmployeeEvent> parseFile(String path) throws IOException {
        List<EmployeeEvent> events = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] data = line.split(",");
            EmployeeEvent event = new EmployeeEvent(
                    Integer.parseInt(data[0].trim()),
                    data[1].trim(),
                    data[2].trim(),
                    data[3].trim(),
                    data.length > 4 ? data[4].trim() : "",
                    data.length > 5 ? data[5].trim() : "",
                    data.length > 6 ? data[6].trim() : "",
                    data.length > 7 ? data[7].trim() : "",
                    data.length > 8 ? data[8].trim() : ""
            );
            events.add(event);
        }
        return events;
    }*/
    public List<EmployeeEvent> parseFile(MultipartFile file) throws IOException {
        List<EmployeeEvent> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                EmployeeEvent event = null;
                if (parts.length > 6) {
                            event = EmployeeEvent.builder()
                                    .sequenceNo( Integer.parseInt(parts[0].trim()))
                                    .empId(parts[1].trim())
                                    .empFName(parts[2].trim())
                                    .empLName(parts[3].trim())
                                    .designation(parts[4].trim())
                                    .value(parts[5].trim())
                                    .event(parts[6].trim())
                                    .eventDate(parts[7].trim())
                                    .notes(parts[8].trim())
                                    .build();
                } else {
                    event =EmployeeEvent.builder()
                            .sequenceNo( Integer.parseInt(parts[0].trim()))
                            .empId(parts[1].trim())
                            .value(parts[2].trim())
                            .event(parts[3].trim())
                            .eventDate(parts[4].trim())
                            .notes(parts[5].trim())
                            .build();
                }
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}

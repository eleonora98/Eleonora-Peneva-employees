package com.example.backend.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.model.EmployeeProject;

@Service
public class CsvService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<EmployeeProject> parse(MultipartFile file) throws Exception {

        List<EmployeeProject> result = new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(file.getInputStream()));
        
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = format.parse(reader);

        for (CSVRecord record : records) {

            Long empId = Long.parseLong(record.get("EmpID").trim());
            Long projectId = Long.parseLong(record.get("ProjectID").trim());
            
            System.out.println(empId);

            LocalDate from =
                    LocalDate.parse(record.get("DateFrom").trim(), FORMATTER);

            String dateToText = record.get("DateTo").trim();

            LocalDate to;

            if ("NULL".equalsIgnoreCase(dateToText)) {
                to = LocalDate.now();
            } else {
                to = LocalDate.parse(dateToText, FORMATTER);
            }

            result.add(new EmployeeProject(
                    empId,
                    projectId,
                    from,
                    to
            ));
        }

        return result;
    }
}

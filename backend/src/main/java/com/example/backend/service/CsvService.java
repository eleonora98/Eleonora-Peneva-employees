package com.example.backend.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.model.EmployeeProject;
import com.example.backend.util.DateFormatterUtil;

@Service
public class CsvService {

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
                        
            String fromDateAsText = record.get("DateFrom").trim();
            String toDateAsText = record.get("DateTo").trim();
            
            LocalDate from = DateFormatterUtil.parseDate(fromDateAsText);
            LocalDate to;

            if ("NULL".equalsIgnoreCase(toDateAsText)) {
                to = LocalDate.now();
            } else {
                to = DateFormatterUtil.parseDate(toDateAsText);
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

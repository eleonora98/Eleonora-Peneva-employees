package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.PairResult;
import com.example.backend.model.EmployeeProject;
import com.example.backend.service.CsvService;
import com.example.backend.service.EmployeePairService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private CsvService csvService;

    @Autowired
    private EmployeePairService employeePairService;

    @PostMapping("/upload")
    public List<PairResult> upload(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        List<EmployeeProject> records =
                csvService.parse(file);

        return employeePairService
                .findLongestWorkingPair(records);
    }
}

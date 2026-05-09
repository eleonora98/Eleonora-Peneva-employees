package com.example.backend.model;

import java.time.LocalDate;

public class EmployeeProject {

    private Long employeeId;
    private Long projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public EmployeeProject(Long employeeId, Long projectId,
                           LocalDate dateFrom, LocalDate dateTo) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}

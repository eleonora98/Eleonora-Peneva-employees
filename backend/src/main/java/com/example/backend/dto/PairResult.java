package com.example.backend.dto;

public class PairResult {

    private Long employeeId1;
    private Long employeeId2;
    private Long projectId;
    private long daysWorked;

    public PairResult(Long employeeId1, Long employeeId2,
                      Long projectId, long daysWorked) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
        this.projectId = projectId;
        this.daysWorked = daysWorked;
    }

    public Long getEmployeeId1() {
        return employeeId1;
    }

    public Long getEmployeeId2() {
        return employeeId2;
    }

    public Long getProjectId() {
        return projectId;
    }

    public long getDaysWorked() {
        return daysWorked;
    }
}

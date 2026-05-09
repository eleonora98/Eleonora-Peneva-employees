package com.example.backend.dto;

public class PairResult {

    private Long employee1;
    private Long employee2;
    private Long projectId;
    private long daysWorked;

    public PairResult(Long employee1, Long employee2,
                      Long projectId, long daysWorked) {
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.projectId = projectId;
        this.daysWorked = daysWorked;
    }

    public Long getEmployee1() {
        return employee1;
    }

    public Long getEmployee2() {
        return employee2;
    }

    public Long getProjectId() {
        return projectId;
    }

    public long getDaysWorked() {
        return daysWorked;
    }
}

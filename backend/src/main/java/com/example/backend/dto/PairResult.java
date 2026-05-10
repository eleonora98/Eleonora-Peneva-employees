package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PairResult {

    private Long employeeId1;
    private Long employeeId2;
    private Long projectId;
    private long daysWorked;

}

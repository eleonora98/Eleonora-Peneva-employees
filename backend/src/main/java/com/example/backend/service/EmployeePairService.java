package com.example.backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.PairResult;
import com.example.backend.model.EmployeeProject;

@Service
public class EmployeePairService {

	/**
	 * Gets the pairs that have been working the longest on a project, together
	 * @param records
	 * @return List of DTOs
	 */
	public List<PairResult> getLongestWorkingPair(List<EmployeeProject> records) {
		
	    List<PairResult> details = calculatePairDetails(records);

	    String bestPair = findBestPair(details);

	    if (bestPair == null) {
	        return Collections.emptyList();
	    }

	    String[] ids = bestPair.split("-");

	    Long emp1 = Long.parseLong(ids[0]);
	    Long emp2 = Long.parseLong(ids[1]);

	    return details.stream()
	            .filter(r ->
	                    r.getEmployeeId1().equals(emp1)
	                            && r.getEmployeeId2().equals(emp2))
	            .collect(Collectors.toList());
	}
	
	/**
	 * Compares employees working on the same project
	 * and calculates the number of overlapping days.
	 * @param records
	 * @return List of DTOs
	 */
	private List<PairResult> calculatePairDetails(List<EmployeeProject> records) {
		
	    Map<Long, List<EmployeeProject>> projects =
	            records.stream()
	                    .collect(Collectors.groupingBy(
	                            EmployeeProject::getProjectId
	                    ));

	    List<PairResult> details = new ArrayList<>();

	    for (List<EmployeeProject> employees : projects.values()) {
	    	
	        for (int i = 0; i < employees.size(); i++) {
	        	
	            for (int j = i + 1; j < employees.size(); j++) {
	                EmployeeProject empProject1 = employees.get(i);
	                EmployeeProject empProject2 = employees.get(j);

	                long overlap = calculateOverlap(empProject1, empProject2);

	                if (overlap > 0) {
	                    Long empA = Math.min(empProject1.getEmployeeId(), empProject2.getEmployeeId());

	                    Long empB = Math.max(empProject1.getEmployeeId(), empProject2.getEmployeeId());

	                    details.add(new PairResult(
	                            empA,
	                            empB,
	                            empProject1.getProjectId(),
	                            overlap
	                    ));
	                }
	            }
	        }
	    }

	    return details;
	}
	
	private String findBestPair(List<PairResult> details) {

	    Map<String, Long> pairTotalDays = new HashMap<>();

	    for (PairResult result : details) {
	        String key = result.getEmployeeId1() + "-" + result.getEmployeeId2();

	        pairTotalDays.put(
	                key,
	                pairTotalDays.getOrDefault(key, 0L) + result.getDaysWorked()
	        );
	    }

	    String bestPair = null;
	    long maxDays = 0;

	    for (Map.Entry<String, Long> entry : pairTotalDays.entrySet()) {

	        if (entry.getValue() > maxDays) {
	            maxDays = entry.getValue();
	            bestPair = entry.getKey();
	        }
	    }

	    return bestPair;
	}

    private long calculateOverlap(EmployeeProject empProject1, EmployeeProject empProject2) {
        LocalDate start = empProject1.getDateFrom().isAfter(empProject2.getDateFrom())
                ? empProject1.getDateFrom()
                : empProject2.getDateFrom();

        LocalDate end = empProject1.getDateTo().isBefore(empProject2.getDateTo())
                ? empProject1.getDateTo()
                : empProject2.getDateTo();

        if (start.isAfter(end)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(start, end);
    }
}

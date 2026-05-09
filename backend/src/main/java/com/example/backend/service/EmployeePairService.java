package com.example.backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.backend.dto.PairResult;
import com.example.backend.model.EmployeeProject;

@Service
public class EmployeePairService {

    public List<PairResult> findLongestWorkingPair(
            List<EmployeeProject> records) {

        Map<String, Long> pairTotalDays = new HashMap<>();
        List<PairResult> details = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {

            for (int j = i + 1; j < records.size(); j++) {

                EmployeeProject e1 = records.get(i);
                EmployeeProject e2 = records.get(j);

                if (!e1.getProjectId().equals(e2.getProjectId())) {
                    continue;
                }

                long overlap = calculateOverlap(e1, e2);

                if (overlap > 0) {

                    Long empA = Math.min(e1.getEmployeeId(), e2.getEmployeeId());
                    Long empB = Math.max(e1.getEmployeeId(), e2.getEmployeeId());

                    String key = empA + "-" + empB;

                    pairTotalDays.put(
                            key,
                            pairTotalDays.getOrDefault(key, 0L) + overlap
                    );

                    details.add(new PairResult(
                            empA,
                            empB,
                            e1.getProjectId(),
                            overlap
                    ));
                }
            }
        }

        String bestPair = null;
        long maxDays = 0;

        for (Map.Entry<String, Long> entry : pairTotalDays.entrySet()) {

            if (entry.getValue() > maxDays) {
                maxDays = entry.getValue();
                bestPair = entry.getKey();
            }
        }

        if (bestPair == null) {
            return Collections.emptyList();
        }

        String[] ids = bestPair.split("-");

        Long emp1 = Long.parseLong(ids[0]);
        Long emp2 = Long.parseLong(ids[1]);

        List<PairResult> result = new ArrayList<>();

        for (PairResult r : details) {

            if (r.getEmployee1().equals(emp1)
                    && r.getEmployee2().equals(emp2)) {

                result.add(r);
            }
        }

        return result;
    }

    private long calculateOverlap(
            EmployeeProject e1,
            EmployeeProject e2) {

        LocalDate start = e1.getDateFrom().isAfter(e2.getDateFrom())
                ? e1.getDateFrom()
                : e2.getDateFrom();

        LocalDate end = e1.getDateTo().isBefore(e2.getDateTo())
                ? e1.getDateTo()
                : e2.getDateTo();

        if (start.isAfter(end)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(start, end);
    }
}

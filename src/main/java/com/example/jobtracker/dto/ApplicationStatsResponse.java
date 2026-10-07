package com.example.jobtracker.dto;

import com.example.jobtracker.domain.ApplicationStatus;

import java.util.Map;

/** Number of applications per status, plus the overall total. */
public record ApplicationStatsResponse(long total, Map<ApplicationStatus, Long> byStatus) {
}

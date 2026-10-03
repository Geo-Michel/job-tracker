package com.example.jobtracker.dto;

import com.example.jobtracker.domain.ApplicationStatus;
import com.example.jobtracker.domain.JobApplication;

import java.time.Instant;
import java.time.LocalDate;

/** Job application data returned to clients; never includes the owner's details. */
public record JobApplicationResponse(
        Long id,
        String company,
        String position,
        ApplicationStatus status,
        String location,
        String jobUrl,
        LocalDate appliedDate,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {

    /** Builds a response from a stored application. */
    public static JobApplicationResponse from(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getPosition(),
                application.getStatus(),
                application.getLocation(),
                application.getJobUrl(),
                application.getAppliedDate(),
                application.getNotes(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}
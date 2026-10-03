package com.example.jobtracker.dto;

import com.example.jobtracker.domain.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


/** Data a client sends to create a job application. */
public record CreateJobApplicationRequest(
        @NotBlank @Size(max = 150) String company,
        @NotBlank @Size(max = 150) String position,
        @NotNull ApplicationStatus status,
        @Size(max = 150) String location,
        @Size(max = 500) String jobUrl,
        LocalDate appliedDate,
        String notes
) {
}
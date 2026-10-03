package com.example.jobtracker.controller;

import com.example.jobtracker.domain.ApplicationStatus;
import com.example.jobtracker.dto.CreateJobApplicationRequest;
import com.example.jobtracker.dto.JobApplicationResponse;
import com.example.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Exposes the job application endpoints. */
@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    // Temporary: replaced by the logged-in user once authentication exists
    private static final Long TEMP_USER_ID = 1L;

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    /** Creates a new job application. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse create (@Valid @RequestBody CreateJobApplicationRequest request){
      return service.create(TEMP_USER_ID, request);
    }

    /** Lists the user's applications, optionally filtered by status. */
    @GetMapping
    public Page<JobApplicationResponse> list(@RequestParam(required = false) ApplicationStatus status, @ParameterObject Pageable pageable){
        return service.list(TEMP_USER_ID, status, pageable);
    }
}

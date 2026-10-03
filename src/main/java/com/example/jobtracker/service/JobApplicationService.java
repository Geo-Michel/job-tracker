package com.example.jobtracker.service;

import com.example.jobtracker.domain.ApplicationStatus;
import com.example.jobtracker.domain.JobApplication;
import com.example.jobtracker.domain.User;
import com.example.jobtracker.dto.CreateJobApplicationRequest;
import com.example.jobtracker.dto.JobApplicationResponse;
import com.example.jobtracker.exception.ResourceNotFoundException;
import com.example.jobtracker.repository.JobApplicationRepository;
import com.example.jobtracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository applicationRepository,
                                 UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public JobApplicationResponse create(Long userId, CreateJobApplicationRequest request){
        User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        JobApplication application = new JobApplication(user, request.company(), request.position(), request.status());
        application.setLocation(request.location());
        application.setJobUrl(request.jobUrl());
        application.setAppliedDate(request.appliedDate());
        application.setNotes(request.notes());
        JobApplication saved = applicationRepository.save(application);
        return JobApplicationResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public Page<JobApplicationResponse> list(Long userId, ApplicationStatus status, Pageable pageable){
        Page<JobApplication> page = (status==null)
                ? applicationRepository.findByUserId(userId,pageable)
                :applicationRepository.findByUserIdAndStatus(userId, status, pageable);
        return page.map(JobApplicationResponse::from);
    }

}

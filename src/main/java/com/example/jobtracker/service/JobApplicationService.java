package com.example.jobtracker.service;

import com.example.jobtracker.domain.ApplicationStatus;
import com.example.jobtracker.domain.JobApplication;
import com.example.jobtracker.domain.User;
import com.example.jobtracker.dto.CreateJobApplicationRequest;
import com.example.jobtracker.dto.UpdateJobApplicationRequest;
import com.example.jobtracker.dto.JobApplicationResponse;
import com.example.jobtracker.exception.ResourceNotFoundException;
import com.example.jobtracker.dto.repository.JobApplicationRepository;
import com.example.jobtracker.dto.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.jobtracker.exception.BadRequestException;

/** Handles the logic for creating, updating and listing a user's job applications. */
@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private static final List<String> SORTABLE_FIELDS =
            List.of("createdAt", "updatedAt", "company", "position", "status", "appliedDate");

    public JobApplicationService(JobApplicationRepository applicationRepository,
                                 UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    /** Creates an application owned by the given user and returns it as a response. */
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

    /** Returns one page of the user's applications, optionally filtered by status. */
    @Transactional(readOnly = true)
    public Page<JobApplicationResponse> list(Long userId, ApplicationStatus status, Pageable pageable){
        validateSort(pageable);
        Page<JobApplication> page = (status==null)
                ? applicationRepository.findByUserId(userId,pageable)
                :applicationRepository.findByUserIdAndStatus(userId, status, pageable);
        return page.map(JobApplicationResponse::from);
    }

    private void validateSort(Pageable pageable) {
        pageable.getSort().forEach(order -> {
            if (!SORTABLE_FIELDS.contains(order.getProperty())) {
                throw new BadRequestException("Cannot sort by '" + order.getProperty()
                        + "'. Allowed fields: " + String.join(", ", SORTABLE_FIELDS));
            }
        });
    }

    private JobApplication findOwned(Long userId, Long id) {
        return applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));
    }

    /** Returns one of the user's applications, or fails with 404 if it does not exist. */
    @Transactional(readOnly = true)
    public JobApplicationResponse get(Long userId, Long id){
        return JobApplicationResponse.from(findOwned(userId,id));
    }

    /** Replaces the fields of one of the user's applications. */
    @Transactional
    public JobApplicationResponse update(Long userId, Long id, UpdateJobApplicationRequest request){
        JobApplication application = findOwned(userId,id);
        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setStatus(request.status());
        application.setLocation(request.location());
        application.setJobUrl(request.jobUrl());
        application.setAppliedDate(request.appliedDate());
        application.setNotes(request.notes());

        JobApplication saved = applicationRepository.saveAndFlush(application);
        return JobApplicationResponse.from(saved);
    }

    /** Deletes one of the user's applications. */
    @Transactional
    public void delete(Long userId, Long id){
        applicationRepository.delete(findOwned(userId,id));
    }


}

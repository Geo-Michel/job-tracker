package com.example.jobtracker.repository;

import com.example.jobtracker.domain.ApplicationStatus;
import com.example.jobtracker.domain.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


/** Database access for job applications; every query is scoped to one user. */
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Every query is scoped by user id so one user can never read another's data.
    Page<JobApplication> findByUserId(Long userId, Pageable pageable);

    Page<JobApplication> findByUserIdAndStatus(Long userId, ApplicationStatus status, Pageable pageable);

    Optional<JobApplication> findByIdAndUserId(Long id, Long userId);

    /** Returns pairs of status and count for the user's applications. */
    @Query("select a.status, count(a) from JobApplication a where a.user.id = :userId group by a.status")
    List<Object[]> countByStatusForUser(@Param("userId") Long userId);
}

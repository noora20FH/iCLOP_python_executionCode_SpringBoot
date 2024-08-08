package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    int countByUserIdAndTaskId(Long userId, Long taskId);
    List<Submission> findByUserIdAndTaskId(Long userId, Long taskId);
}
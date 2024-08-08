package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private Long taskId;
    private String filePath;
    private int submissionCount;
    @Column(columnDefinition = "TEXT")
    private String testResult;
    private LocalDateTime submittedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public int getSubmissionCount() {
        return submissionCount;
    }
    public void setSubmissionCount(int submissionCount) {
        this.submissionCount = submissionCount;
    }
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    // Getter dan setter
}
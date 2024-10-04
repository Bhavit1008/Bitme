package com.bithub.bitme.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private String details;
    private LocalDateTime timestamp;

    public LogEntity(Long id, String operation, String details, LocalDateTime timestamp) {
        this.id = id;
        this.operation = operation;
        this.details = details;
        this.timestamp = timestamp;
    }

    public LogEntity() {
    }
// Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

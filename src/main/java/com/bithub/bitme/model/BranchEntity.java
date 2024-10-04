package com.bithub.bitme.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active;

    @OneToMany(mappedBy = "branch")
    private List<CommitEntity> commits;

    public BranchEntity(){}

    public BranchEntity(Long id, String name, boolean active, List<CommitEntity> commits) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.commits = commits;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<CommitEntity> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitEntity> commits) {
        this.commits = commits;
    }
}

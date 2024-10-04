package com.bithub.bitme.model;

import jakarta.persistence.*;

@Entity
public class HeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BranchEntity currentBranch;

    public HeadEntity(Long id, BranchEntity currentBranch) {
        this.id = id;
        this.currentBranch = currentBranch;
    }

    public HeadEntity() {
    }
// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchEntity getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(BranchEntity currentBranch) {
        this.currentBranch = currentBranch;
    }
}

package com.bithub.bitme.repository;

import com.bithub.bitme.model.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository extends JpaRepository<CommitEntity, Long> {}

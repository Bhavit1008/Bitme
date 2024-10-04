package com.bithub.bitme.repository;

import com.bithub.bitme.model.HeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadRepository extends JpaRepository<HeadEntity, Long> {
}
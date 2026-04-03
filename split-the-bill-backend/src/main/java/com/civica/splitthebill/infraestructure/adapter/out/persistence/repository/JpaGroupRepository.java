package com.civica.splitthebill.infraestructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public interface JpaGroupRepository extends JpaRepository <GroupEntity, Long> {
    Optional<GroupEntity> findByName(String name);
}

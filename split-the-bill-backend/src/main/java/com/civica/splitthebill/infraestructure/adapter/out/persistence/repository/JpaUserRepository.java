package com.civica.splitthebill.infraestructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByGroups_Id(Long groupId);
}

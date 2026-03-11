package com.civica.splitthebill.infraestructure.adapter.out.persistence.repository;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByGroupId(Long groupId);
}

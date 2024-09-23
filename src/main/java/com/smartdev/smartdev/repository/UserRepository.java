package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    Optional<UserEntity> findByEmail(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}


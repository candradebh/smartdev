package com.smartdev.repository;

import com.smartdev.entity.MessageEntity;
import com.smartdev.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByProject(ProjectEntity project);

    List<MessageEntity> findByProjectIsNull();
}

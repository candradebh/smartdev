package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.TableMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableMetadataRepository extends JpaRepository<TableMetadataEntity, Long> {

    Optional<TableMetadataEntity> findByTableName(String tableName);
}

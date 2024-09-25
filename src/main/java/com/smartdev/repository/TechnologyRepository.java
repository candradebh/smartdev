package com.smartdev.repository;

import com.smartdev.entity.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {

}

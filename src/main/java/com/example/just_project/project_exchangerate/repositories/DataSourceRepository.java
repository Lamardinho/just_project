package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.enums.ESource;
import com.example.just_project.project_exchangerate.model.exchangerate.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

    Optional<DataSource> findBySource(ESource source);
}

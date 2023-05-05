package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.enums.ESource;
import com.example.just_project.project_exchangerate.model.exchangerate.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "exchangeRateDataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

    Optional<DataSource> findBySource(ESource source);
}

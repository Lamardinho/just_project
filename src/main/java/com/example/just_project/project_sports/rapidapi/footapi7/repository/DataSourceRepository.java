package com.example.just_project.project_sports.rapidapi.footapi7.repository;

import com.example.just_project.project_exchangerate.enums.ESource;
import com.example.just_project.project_sports.rapidapi.footapi7.entity.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "sportsDataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

    Optional<DataSource> findBySource(ESource source);
}

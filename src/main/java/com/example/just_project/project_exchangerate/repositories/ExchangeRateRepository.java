package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstByOrderByIdDesc();

    Optional<ExchangeRate> findByBaseAndDate(ERate currency, LocalDate date);
}

package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.model.exchangerate.DataSource;
import com.example.just_project.project_exchangerate.model.exchangerate.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstByOrderByIdDesc();

    Optional<ExchangeRate> findByCurrencyAndDateRating(ERate currency, LocalDate date);

    Page<ExchangeRate> findAllByCurrencyAndDataSource(ERate currency, DataSource dataSource, Pageable pageable);
}

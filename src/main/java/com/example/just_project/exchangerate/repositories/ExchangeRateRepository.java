package com.example.just_project.exchangerate.repositories;

import com.example.just_project.exchangerate.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}

package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.model.exchangerate.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
}

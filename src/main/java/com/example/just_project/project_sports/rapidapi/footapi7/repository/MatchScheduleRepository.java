package com.example.just_project.project_sports.rapidapi.footapi7.repository;

import com.example.just_project.project_sports.rapidapi.footapi7.entity.MatchSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MatchScheduleRepository extends JpaRepository<MatchSchedule, Integer> {

    Optional<MatchSchedule> findByDate(LocalDate date);
}

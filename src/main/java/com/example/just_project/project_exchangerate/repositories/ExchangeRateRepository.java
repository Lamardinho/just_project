package com.example.just_project.project_exchangerate.repositories;

import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.model.exchangerate.DataSource;
import com.example.just_project.project_exchangerate.model.exchangerate.ExchangeRate;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findFirstByOrderByIdDesc();

    Optional<ExchangeRate> findByCurrencyAndDateRating(ERate currency, LocalDate date);

    Page<ExchangeRate> findAllByCurrencyAndDataSource(ERate currency, DataSource dataSource, Pageable pageable);

    @Query(
            "select er from ExchangeRate er" +
                    " left join ExchangeRateHasRate erhr on er.id = erhr.id.exchangeRateId" +
                    " left join Rate r on r.id = erhr.id.rateId" +
                    " where r.charCode in (:rates)" +
                    " order by er.dateRating desc"
    )
    List<ExchangeRate> f(
            @NonNull Pageable pageable,
            // @NonNull ERate currency,
            // @NonNull DataSource dataSource,
            @NonNull List<ERate> rates
    );
}

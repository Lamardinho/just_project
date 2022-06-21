package com.example.just_project.project_exchangerate.model;

import com.example.just_project.project_exchangerate.enums.ERate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Курс валют по отношению к рублю.
 * <p>
 * Получаем данные с сайта: <a href="https://www.cbr-xml-daily.ru/latest.js">cbr-xml-daily.ru</a>
 */
@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "exchange_rate", name = "exchange_rate")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_source")
    private DataSource dataSource;

    @Column(name = "date_rating", nullable = false)
    private LocalDate dateRating;

    @Column(name = "time_request", nullable = false)
    private Instant timeRequest = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private ERate currency;

    @Column(name = "rates", nullable = false, length = 7000)
    private String rates;
}

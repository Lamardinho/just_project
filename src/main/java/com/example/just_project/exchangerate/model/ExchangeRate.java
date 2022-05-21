package com.example.just_project.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    private Long id;

    @Column(name = "disclaimer", nullable = false)
    private String disclaimer;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "base", nullable = false)
    private String base;

    @Column(name = "rates", nullable = false, length = 1024)
    private String rates;
}

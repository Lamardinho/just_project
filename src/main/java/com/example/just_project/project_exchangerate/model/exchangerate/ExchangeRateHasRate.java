package com.example.just_project.project_exchangerate.model.exchangerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "exchange_rate", name = "exchange_rate_has_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateHasRate {

    @EmbeddedId
    private ExchangeRateHasRateId id;
}

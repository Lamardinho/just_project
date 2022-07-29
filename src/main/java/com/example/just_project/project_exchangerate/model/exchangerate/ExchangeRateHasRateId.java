package com.example.just_project.project_exchangerate.model.exchangerate;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExchangeRateHasRateId implements Serializable {

    @Column(name = "exchange_rate_id")
    private long exchangeRateId;

    @Column(name = "rate_Id")
    private long rateId;
}

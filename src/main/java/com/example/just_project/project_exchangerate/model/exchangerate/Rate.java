package com.example.just_project.project_exchangerate.model.exchangerate;

import com.example.just_project.project_exchangerate.enums.ERate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "exchange_rate", name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valute_id", nullable = false)
    private String valuteId;

    @Column(name = "num_code", nullable = false)
    private int numCode = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "char_code", nullable = false)
    private ERate charCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private Double value;
}

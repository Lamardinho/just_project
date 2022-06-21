package com.example.just_project.project_exchangerate.model;

import com.example.just_project.project_exchangerate.enums.ESource;
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
@Table(schema = "exchange_rate", name = "data_source")
public class DataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_short_name", nullable = false)
    private ESource source;

    @Column(name = "source_url", nullable = false)
    private String sourceUrl;

    @Column(name = "description", nullable = false)
    private String description;
}

package com.example.just_project.project_sports.rapidapi.footapi7.entity;

import com.example.just_project.project_exchangerate.enums.ESource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name = "sportsDataSource")
@Table(schema = "sports", name = "data_source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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

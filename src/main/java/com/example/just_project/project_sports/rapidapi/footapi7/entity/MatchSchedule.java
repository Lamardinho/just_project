package com.example.just_project.project_sports.rapidapi.footapi7.entity;

import com.example.just_project.project_sports.rapidapi.footapi7.dto.EventsDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "sports", name = "match_schedule")
@Getter
@Setter
@Accessors(chain = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@AllArgsConstructor
@NoArgsConstructor
public class MatchSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private LocalDate date;

    @Type(type = "jsonb")
    @Column(columnDefinition = "data")
    private EventsDTO events;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_source")
    private DataSource dataSource;*/

    @Column(name = "data_source_id")
    private Integer dataSourceId;
}

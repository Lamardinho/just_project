create table sports.match_schedule
(
    id             serial primary key,
    date           date    not null,
    category_id    integer,
    data           jsonb   not null,
    data_source_id integer not null
        constraint fk_data_source references sports.data_source,
    constraint uk_match_schedule_date_category_id_data_source_id
        unique (date, category_id, data_source_id)
);

comment on table sports.match_schedule is 'расписание матчей';
comment on column sports.match_schedule.category_id is 'id чемпионата';

alter table sports.match_schedule
    owner to postgres;

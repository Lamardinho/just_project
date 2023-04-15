create table sports.match_schedule
(
    id   SERIAL PRIMARY KEY,
    date DATE  NOT NULL,
    data jsonb NOT NULL,
    data_source  integer
        constraint fk_data_source references sports.data_source
);

comment on table sports.match_schedule is 'расписание матчей';

alter table sports.match_schedule
    owner to postgres;

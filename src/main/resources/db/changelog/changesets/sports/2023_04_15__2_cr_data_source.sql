create table sports.data_source
(
    id          serial2 primary key,
    description varchar(1000) not null,
    source_name varchar(100) not null unique,
    source_url  varchar(100) not null unique
);
comment on table sports.data_source is 'источник данных';
alter table sports.data_source
    owner to postgres;

insert into sports.data_source
    (description, source_name, source_url)
values ('FootApi offers real-time football scores of all live matches that are being played. FootApi covers hundreds of soccer leagues, cups and tournaments with live updated results, statistics, league tables, video highlights and fixtures. From most popular football leagues (UEFA Champions League, UEFA Europa League, Premier League, LaLiga, Bundesliga, Serie A, Ligue 1, Brasileiro Série A), top players ratings and statistics to football matches played in a date, our FootApi covers all the information you need.
To get high API calls rate plans use: https://rapidapi.com/fluis.lacasse/api/allsportsapi2',
        'rapidapi_footapi7',
        'footapi7.p.rapidapi.com');

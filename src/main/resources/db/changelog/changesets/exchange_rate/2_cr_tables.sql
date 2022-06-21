create table exchange_rate.data_source
(
    id                integer generated by default as identity primary key,
    description       varchar(255) not null,
    source_short_name varchar(255) not null,
    source_url        varchar(255) not null
);

insert into exchange_rate.data_source (description, source_short_name, source_url)
values ('Источник - официальный сайт банка России',
        'CBR_RU_DAILY_ENG_XML',
        'https://www.cbr.ru/scripts/XML_daily_eng.asp?date_req='),
       ('Источник - некоммерческий проект, берут данные с cbr.ru',
        'CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON',
        'https://www.cbr-xml-daily.ru/latest.js');

create table exchange_rate.exchange_rate
(
    id           bigint generated by default as identity primary key,
    currency     varchar(255)  not null,
    date_rating  date          not null,
    rates        varchar(7000) not null,
    time_request timestamp     not null,
    data_source  integer
        constraint fk_data_source references exchange_rate.data_source
);

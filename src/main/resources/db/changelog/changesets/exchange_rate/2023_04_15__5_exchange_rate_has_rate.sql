create table exchange_rate.exchange_rate_has_rate
(
    exchange_rate_id bigint not null
        constraint fk_exchange_rate references exchange_rate.exchange_rate,
    rate_id          bigint not null
        constraint fk_rate references exchange_rate.rate,
    primary key (exchange_rate_id, rate_id)
);

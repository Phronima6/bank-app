create table exchange.rates
(
    id    bigint primary key generated always as identity,
    title  text  not null,
    name  text  not null,
    value  int  not null
);

insert into exchange.rates (title, name, value) values
 ('Рубли', 'RUB', 100),
 ('Доллары', 'USD', 8000),
 ('Юани', 'CNY', 1100);
alter table exchange.rates add column base boolean not null default false;
update exchange.rates set base = true where name = 'RUB';
create table airports(
id bigserial primary key,
title varchar
);
create table air_plans(
id bigserial primary key,
model varchar,
marka varchar,
count_place integer,
airport_id bigint references airports (id),
status varchar
);

create table flights(
id bigserial primary key,
title varchar,
place_of_departure bigint references airports (id),
airplane_id bigint  unique references air_plans (id),
airport_id bigint  references airports (id),
status varchar,
date DATE
);

create table roles(
id bigserial primary key,
title varchar not null default 'CLIENT'
);

create table users(
id bigserial primary key,
login varchar unique,
password varchar,
surname varchar,
airport_id bigint references airports(id),
flights_id bigint references flights(id),
airplane_id bigint references air_plans(id)
);

create table users_roles (
    id bigserial primary key,
    user_id bigint references users (id),
    roles_id bigint references roles (id),
    status varchar
);
create table requests_jobs (
    id bigserial primary key,
    login varchar,
    message varchar,
    role_id bigint references roles(id)
);
create table requests_taking_plane(
    id bigserial primary key,
    modelPlane varchar,
    placeOfDeparture bigint references airports(id),
    airport_id bigint references airports(id),
    flights_id bigint references flights(id)
);
create table reviews(
    id bigserial primary key,
    message varchar,
    flights_id bigint references flights(id),
    user_id bigint references users(id)
);
create table history_past_flights(
    id bigserial primary key,
    user_id bigint references users(id),
    flights_id bigint references flights(id),
    date date
);
create table readings(
    id bigserial primary key,
    count_clients_used_services integer,
    count_flight_used integer,
    date date
)










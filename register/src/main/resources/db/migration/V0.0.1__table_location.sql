-- auto-generated definition
create table tb_location
(
    id        bigint generated by default as identity
        constraint tb_location_pkey
            primary key,
    latitude  double precision,
    longitude double precision
);



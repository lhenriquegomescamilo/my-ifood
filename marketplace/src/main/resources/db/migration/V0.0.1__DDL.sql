CREATE TABLE location (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	latitude float8 NULL,
	longitude float8 NULL,
	CONSTRAINT location_pkey PRIMARY KEY (id)
);

CREATE TABLE restaurant (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	name varchar(50) NULL,
	location_id int8 NOT NULL,
	CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);

ALTER TABLE restaurant ADD CONSTRAINT res_loc FOREIGN KEY (location_id) REFERENCES location(id);

CREATE TABLE food (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(200) NULL,
	name varchar(50) NULL,
	price numeric(19,2) NOT NULL,
	restaurant_id int8 NOT NULL
);

ALTER TABLE food ADD CONSTRAINT food_rest FOREIGN KEY (restaurant_id) REFERENCES restaurant(id);


CREATE TABLE food_client (
	food int,
	client varchar(200)
);

INSERT INTO location (id, latitude, longitude) VALUES(1000, -15.817759, -47.836959);

INSERT INTO restaurant (id, location_id, name) VALUES(999, 1000, 'Manguai');

INSERT INTO food
(id, name, description, restaurant_id, price)
VALUES(9998, 'Cuscuz com Ovo', 'Bom demais no café da manhã', 999, 3.99);

INSERT INTO food
(id, name, description, restaurant_id, price)
VALUES(9997, 'Peixe frito', 'Agulhinha frita, excelente com Cerveja', 999, 99.99);
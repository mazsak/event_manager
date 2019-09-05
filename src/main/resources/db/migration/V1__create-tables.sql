create table if not exists event
(
	id bigserial not null
		constraint event_pkey
			primary key,
	date_time timestamp,
	description varchar(255),
	name varchar(255),
	place varchar(255),
	topic varchar(255)
);

alter table event owner to manager;

create table if not exists to_do
(
	id bigserial not null
		constraint to_do_pkey
			primary key,
	name varchar(255),
	predefined boolean not null
);

alter table to_do owner to manager;

create table if not exists event_to_do
(
	event_id bigint not null
		constraint fkdqst6glb6bro683akdapyw9at
			references event,
	to_do_id bigint not null
		constraint fkmwxlqxp6t4pjmrkw55ub4ss16
			references to_do
);

alter table event_to_do owner to manager;

create table if not exists task_status
(
	id bigserial not null
		constraint task_status_pkey
			primary key,
	name varchar(255),
	status boolean not null,
	to_do_id bigint
		constraint fktcipw68rby7mm59p0yo3wj9bt
			references to_do
);

alter table task_status owner to manager;

create table if not exists to_do_predefined
(
	id bigserial not null
		constraint to_do_predefined_pkey
			primary key,
	name varchar(255)
);

alter table to_do_predefined owner to manager;

create table if not exists task
(
	id bigserial not null
		constraint task_pkey
			primary key,
	name varchar(255),
	to_do_id bigint
		constraint fk59wa02mne8bfc1ho8tl557cmo
			references to_do_predefined
);

alter table task owner to manager;


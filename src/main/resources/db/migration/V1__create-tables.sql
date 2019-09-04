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

create table if not exists adhoc
(
	id bigint not null
		constraint fk75p73iyheipkev7h8nh988s7p
			references event,
	name varchar(255)
);

create table if not exists status
(
	id bigint not null
		constraint fk26ee4gd3k994k0e3e7l0mh18m
			references event,
	value boolean,
	key varchar(255) not null,
	constraint status_pkey
		primary key (id, key)
);

create table if not exists to_do_predefined
(
	id bigserial not null
		constraint to_do_predefined_pkey
			primary key,
	name varchar(255)
);


create table if not exists event_predefined
(
	event_id bigint not null
		constraint fktb3qy47d2e0qh0ld1pd65aam6
			references event,
	predefined_id bigint not null
		constraint fkpi8dd2uex0ep1uascyivlen2p
			references to_do_predefined
);


create table if not exists task
(
	id bigint not null
		constraint fkianbphhujjn3ykeclo52hetif
			references to_do_predefined,
	name varchar(255)
);



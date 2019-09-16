

create table task_status
(
	id bigserial not null
		constraint task_status_pkey
			primary key,
	date timestamp,
	name varchar(255),
	status boolean not null,
	task_status_type varchar(255),
	person_id bigint
		constraint fkgvwld2clxxeouw895gr630o1j
			references person
);


create table event_task_statuses
(
	event_id bigint not null
		constraint fk7r1mipvdo1xvrboq5g5qcrs7y
			references event,
	task_statuses_id bigint not null
		constraint uk_miupq79dgtolm7tcrstvy5gol
			unique
		constraint fkmh1hbqwpseotpy3uis72j8ymg
			references task_status
);






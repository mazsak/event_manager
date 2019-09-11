create table event
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

alter table event owner to postgres;

create table person
(
    id bigserial not null
        constraint person_pkey
            primary key,
    name varchar(255)
);

alter table person owner to postgres;

create table task_status
(
    id bigserial not null
        constraint task_status_pkey
            primary key,
    date timestamp,
    name varchar(255),
    status boolean not null,
    task_status_type varchar(255),
    event_id bigint
        constraint fk5j3q4o7eqdyged3hyf7wellxs
            references event
);

alter table task_status owner to postgres;

create table event_task_statuses
(
    event_id bigint not null
        constraint fk7r1mipvdo1xvrboq5g5qcrs7y
            references event,
    task_statuses_id bigint not null
        constraint uk_miupq79dgtolm7tcrstvy5gol
            unique
        constraint fkmh1hbqwpseotpy3uis72j8ymg
            references task_status,
    constraint event_task_statuses_pkey
        primary key (event_id, task_statuses_id)
);

alter table event_task_statuses owner to postgres;

create table person_task_statuses
(
    person_id bigint not null
        constraint fkimia5ixh03j63ys5f3qcect8p
            references person,
    task_statuses_id bigint not null
        constraint uk_m5ph86mru30f1syyk4p5xv2vr
            unique
        constraint fk8cr27xkb3er3qvgl6y8cqnbpc
            references task_status,
    constraint person_task_statuses_pkey
        primary key (person_id, task_statuses_id)
);

alter table person_task_statuses owner to postgres;

create table to_do_predefined
(
    id bigserial not null
        constraint to_do_predefined_pkey
            primary key,
    name varchar(255)
);

alter table to_do_predefined owner to postgres;

create table task
(
    id bigint not null
        constraint fkianbphhujjn3ykeclo52hetif
            references to_do_predefined,
    description varchar(255)
);

alter table task owner to postgres;


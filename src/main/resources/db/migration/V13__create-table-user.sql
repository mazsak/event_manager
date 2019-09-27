create table app_user
(
	id bigserial not null
		constraint app_user_pkey
			primary key,
	login varchar(255)
		constraint uk_irayhia1ygarvmv7apksctnqn
			unique,
	password varchar(255)
);
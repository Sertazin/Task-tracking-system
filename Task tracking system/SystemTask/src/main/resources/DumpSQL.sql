create table Users
(
	id integer not null
		primary key autoincrement,
	name varchar(255) not null
);

create unique index Users_name_uindex
	on Users (name);


create table Projects
(
	id integer not null
		primary key autoincrement,
	name varchar(255) not null,
	description text,
	user_id integer
		constraint Projects_Users_id_fk
			references Users
);

create unique index Projects_name_uindex
	on Projects (name);


create table Tasks
(
	id integer not null
		primary key autoincrement,
	name varchar(255) not null,
	project_id integer not null
		constraint Tasks_Projects_id_fk
			references Projects,
	user_id int
		constraint Tasks_Users_id_fk
			references Users
);


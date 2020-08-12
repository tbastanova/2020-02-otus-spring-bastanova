create table user(
    id bigserial,
    username varchar (255),
--     password varchar (255),
    last_name varchar (255),
    first_name varchar (255),
    middle_name varchar (255),
    primary key (id)
    );

create table category(
    id bigserial,
    name varchar (255),
    primary key (id)
    );

create table product(
    id bigserial,
    name varchar(255),
    category_id bigint references category(id) on delete cascade,
    primary key (id)
    );

create table user_product(
    id bigserial,
    user_id bigint references user(id) on delete cascade,
    product_id bigint references product(id) on delete cascade,
    vote integer,
    primary key (id),
    unique key (user_id,product_id)
    );

create table team(
    id bigserial,
    name varchar (255),
    skipper_id  bigint references user(id) on delete cascade,
    primary key (id)
    );

create table user_team(
    user_id bigint references user(id) on delete cascade,
    team_id bigint references team(id) on delete cascade,
    primary key (user_id,team_id)
    );

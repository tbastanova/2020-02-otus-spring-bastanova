create table user(
    id bigserial,
    username varchar (255),
    password varchar (255),
    primary key (id)
    );

create table role(
    id bigserial,
    name varchar (255),
    primary key (id)
    );

create table user_role(
    user_id bigint references user(id) on delete cascade,
    role_id bigint references role(id) on delete cascade,
    primary key (user_id,role_id)
    );

create table book(
    id bigserial,
    name varchar(255),
    primary key (id)
    );

create table author(
    id bigserial,
    name varchar (255),
    primary key (id)
    );

create table book_author(
    book_id bigint references book(id) on delete cascade,
    author_id bigint references author(id) on delete cascade,
    primary key (book_id,author_id)
    );

create table category(
    id bigserial,
    name varchar (255),
    primary key (id)
    );

create table book_category(
    book_id bigint references book(id) on delete cascade,
    category_id bigint references category(id) on delete cascade,
    primary key (book_id,category_id)
    );

create table comment(
    id bigserial,
    book_id bigint references book(id) on delete cascade,
    text varchar(1000),
    primary key (id)
);
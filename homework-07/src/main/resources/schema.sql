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
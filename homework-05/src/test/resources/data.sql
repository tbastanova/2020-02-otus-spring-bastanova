insert into book (id, name) values (1, 'Test Book1');
insert into book (id, name) values (2, 'Test Book2');

insert into author (id, name) values (1, 'Test Author1');
insert into author (id, name) values (2, 'Test Author2');

insert into category (id, name) values (1, 'Test Category1');
insert into category (id, name) values (2, 'Test Category2');

insert into book_author (id, book_id,author_id) values (1,1,1);

insert into book_category (id, book_id,category_id) values (1,1,1);
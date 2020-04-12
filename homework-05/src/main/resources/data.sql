insert into book (id, name) values (1, 'Атлант затарил гречи');
insert into book (id, name) values (2, 'Трое в лодке, нищета и собаки');
insert into book (id, name) values (3, 'Над пропастью моржи');
insert into book (id, name) values (4, 'Трое на четырех колесах');
insert into book (id, name) values (5, 'Происхождение химических элементов');

insert into author (id, name) values (1, 'Айн Рэнд');
insert into author (id, name) values (2, 'Джером Клапка Джером');
insert into author (id, name) values (3, 'Джером Сэлинджер');
insert into author (id, name) values (4, 'Ральф Альфер');
insert into author (id, name) values (5, 'Джордж Гамов');
insert into author (id, name) values (6, 'Ганс Бэт');

insert into book_author (id, book_id,author_id) values (1,1,1);
insert into book_author (id, book_id,author_id) values (2,2,2);
insert into book_author (id, book_id,author_id) values (3,3,3);
insert into book_author (id, book_id,author_id) values (4,4,2);
insert into book_author (id, book_id,author_id) values (5,5,4);
insert into book_author (id, book_id,author_id) values (6,5,5);
insert into book_author (id, book_id,author_id) values (7,5,6);

insert into category (id, name) values (1, 'Роман');
insert into category (id, name) values (2, 'Юмор');
insert into category (id, name) values (3, 'Повесть');
insert into category (id, name) values (4, 'Физика');
insert into category (id, name) values (5, 'Искаженное название');

insert into book_category (id, book_id,category_id) values (1,1,1);
insert into book_category (id, book_id,category_id) values (2,2,2);
insert into book_category (id, book_id,category_id) values (3,3,3);
insert into book_category (id, book_id,category_id) values (4,4,2);
insert into book_category (id, book_id,category_id) values (5,5,4);
insert into book_category (id, book_id,category_id) values (6,1,5);
insert into book_category (id, book_id,category_id) values (7,2,5);
insert into book_category (id, book_id,category_id) values (8,3,5);

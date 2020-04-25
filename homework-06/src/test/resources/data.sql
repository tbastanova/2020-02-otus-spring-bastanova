insert into author(name)
values ('author_name_01'), ('author_name_02'), ('author_name_03'), ('author_name_04'), ('author_name_05'),
       ('author_name_06'), ('author_name_07'), ('author_name_08'), ('author_name_09'), ('author_name_10'), ('not_used_11');

insert into book(name)
values ('book_01'), ('book_02'), ('book_03'), ('book_04'), ('book_05'),
       ('book_06'), ('book_07'), ('book_08'), ('book_09'), ('book_10');

insert into book_author(book_id, author_id)
values (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 4),   (2, 5),
       (3, 3),   (3, 6),   (3, 7),
       (4, 4),   (4, 8),   (4, 9),
       (5, 5),   (5, 10),  (5, 1),
       (6, 6),   (6, 2),   (6, 3),
       (7, 7),   (7, 4),   (7, 5),
       (8, 8),   (8, 6),   (8, 7),
       (9, 9),   (9, 8),   (9, 10),
       (10, 10), (10, 1),  (10, 2);

insert into category(name)
values ('category_name_01'), ('category_name_02'), ('category_name_03'), ('category_name_04'), ('category_name_05'),
       ('category_name_06'), ('category_name_07'), ('category_name_08'), ('category_name_09'), ('category_name_10'), ('not_used_11');

insert into book_category(book_id, category_id)
values (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 4),   (2, 5),
       (3, 3),   (3, 6),   (3, 7),
       (4, 4),   (4, 8),   (4, 9),
       (5, 5),   (5, 10),  (5, 1),
       (6, 6),   (6, 2),   (6, 3),
       (7, 7),   (7, 4),   (7, 5),
       (8, 8),   (8, 6),   (8, 7),
       (9, 9),   (9, 8),   (9, 10),
       (10, 10), (10, 1),  (10, 2);

insert into comment(text, book_id)
values ('comment_01', 1), ('comment_02', 1), ('comment_03', 2), ('comment_04', 2), ('comment_05', 3), ('comment_06', 4),
       ('comment_07', 5), ('comment_08', 6), ('comment_09', 7), ('comment_10', 8), ('comment_11', 9), ('comment_12', 10);
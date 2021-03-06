insert into book(name)
    values  ('Атлант затарил гречи'), ('Трое в лодке, нищета и собаки'), ('Над пропастью моржи'),
            ('Трое на четырех колесах'), ('Происхождение химических элементов');

insert into author(name)
    values  ('Айн Рэнд'), ('Джером Клапка Джером'), ('Джером Сэлинджер'),
            ('Ральф Альфер'), ('Джордж Гамов'), ('Ганс Бэт');

insert into category(name)
    values  ('Роман'), ('Юмор'), ('Повесть'),
            ('Физика'), ('Искаженное название');

insert into book_author(book_id,author_id)
values (1, 1),   (2, 2),   (3, 3),
       (4, 2),   (5, 4),   (5, 5),
       (5, 6);

insert into book_category(book_id,category_id)
values (1, 1),   (2, 2),   (3, 3),
       (4, 2),   (5, 4),   (1, 5),
       (2, 5),   (3,5);

insert into comment(text,book_id)
    values  ('Комментарий 1',1), ('Комментарий 2',1), ('Комментарий 3',2),
            ('Комментарий 4',2), ('Комментарий 5',2), ('Комментарий 6',3),
            ('Комментарий 7',4), ('Комментарий 8',5), ('Комментарий 9',5);
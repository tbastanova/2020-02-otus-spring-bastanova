insert into user(username, password)
    values ('admin','password'), ('user', '123456'), ('editor', '123456');

insert into role(name)
    values ('ADMIN'),('USER'),('EDITOR');

insert into user_role(user_id,role_id)
values (1, 1), (1, 2), (1,3), (2, 2), (3,2), (3,3);

insert into book(id,name)
    values  (1,'Атлант затарил гречи'), (2,'Трое в лодке, нищета и собаки'), (3,'Над пропастью моржи'),
            (4,'Трое на четырех колесах'), (5,'Происхождение химических элементов');

insert into author(id,name)
    values  (1,'Айн Рэнд'), (2,'Джером Клапка Джером'), (3,'Джером Сэлинджер'),
            (4,'Ральф Альфер'), (5,'Джордж Гамов'), (6,'Ганс Бэт');

insert into category(id,name)
    values  (1,'Роман'), (2,'Юмор'), (3,'Повесть'),
            (4,'Физика'), (5,'Искаженное название');

insert into book_author(book_id,author_id)
values (1, 1),   (2, 2),   (3, 3),
       (4, 2),   (5, 4),   (5, 5),
       (5, 6);

insert into book_category(book_id,category_id)
values (1, 1),   (2, 2),   (3, 3),
       (4, 2),   (5, 4),   (1, 5),
       (2, 5),   (3,5);

insert into comment(id,text,book_id)
    values  (1,'Комментарий 1',1), (2,'Комментарий 2',1), (3,'Комментарий 3',2),
            (4,'Комментарий 4',2), (5,'Комментарий 5',2), (6,'Комментарий 6',3),
            (7,'Комментарий 7',4), (8,'Комментарий 8',5), (9,'Комментарий 9',5);

INSERT INTO acl_sid (id, principal, sid) VALUES
  (1, 0, 'ROLE_ADMIN'),
  (2, 0, 'ROLE_EDITOR'),
  (3, 0, 'ROLE_USER'),
  (4, 1, 'admin'),
  (5, 1, 'editor'),
  (6, 1, 'user');

INSERT INTO acl_class (id, class) VALUES
  (1, 'ru.otus.homework13.model.Book');

INSERT INTO acl_object_identity
  (id, object_id_class, object_id_identity,
  parent_object, owner_sid, entries_inheriting)
  VALUES
  (1, 1, 1, NULL, 1, 0),
  (2, 1, 2, NULL, 1, 0),
  (3, 1, 3, NULL, 1, 0),
  (4, 1, 4, NULL, 1, 0),
  (5, 1, 5, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 2, 1, 1, 1, 1, 1, 1),
(3, 3, 1, 1, 1, 1, 1, 1),
(4, 4, 1, 1, 1, 1, 1, 1),
(5, 5, 1, 1, 1, 1, 1, 1),
(6, 1, 2, 2, 1, 1, 1, 1),
(8, 3, 2, 2, 1, 1, 1, 1),
-- (9, 4, 2, 2, 1, 1, 1, 1), -- для editor доступ на чтение от ROLE_USER
-- (10, 5, 2, 2, 1, 1, 1, 1),
(11, 1, 3, 3, 1, 1, 1, 1),
(12, 4, 3, 3, 1, 1, 1, 1),
(13, 5, 3, 3, 1, 1, 1, 1),
(14, 1, 4, 1, 2, 1, 1, 1),
(15, 2, 4, 1, 2, 1, 1, 1),
(16, 4, 4, 1, 2, 1, 1, 1),
(17, 1, 5, 2, 2, 1, 1, 1),
(18, 4, 5, 2, 2, 1, 1, 1)
;
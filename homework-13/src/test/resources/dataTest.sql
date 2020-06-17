insert into book(id,name)
values (1,'book_01'), (2,'book_02'), (3,'book_03');

insert into user(id, username, password)
    values (1,'admin','admin'), (2,'editor', 'editor'), (3,'user', 'user');

insert into role(id,name)
    values (1,'ADMIN'),(2,'EDITOR'),(3,'USER');

insert into user_role(user_id,role_id)
values (1,1),(2,2),(3,3);

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
  (3, 1, 3, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 2, 1, 1, 1, 1, 1, 1),
(3, 3, 1, 1, 1, 1, 1, 1),
(4, 1, 2, 2, 1, 1, 1, 1),
(5, 3, 2, 2, 1, 1, 1, 1),
(6, 3, 3, 3, 1, 1, 1, 1),
(7, 1, 4, 1, 2, 1, 1, 1),
(8, 2, 4, 1, 2, 1, 1, 1),
(9, 3, 4, 1, 2, 1, 1, 1),
(10, 1, 5, 2, 2, 1, 1, 1)
;
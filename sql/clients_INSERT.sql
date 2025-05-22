--DELETE FROM Clients;
INSERT INTO clients (surname, name, patron, address, email, phone)
VALUES ('Иванов', 'Иван', 'Иванович', 'адрес1', 'example1@gmail.com', '79999999999'),
       ('Петров', 'Пётр', 'Петрович', 'адрес2', 'example2@gmail.com', '74954404866'),
       ('Сидоров', 'Михаил', 'Антонович', 'адрес3', 'example3@gmail.com', '79299597970'),
       ('Гослингов', 'Владимир', 'Владимирович', 'адрес4', 'example4@gmail.com', '79262586991'),
       ('Маск', 'Илон', NULL, 'адрес5', 'example1@gmail.com', '79253586655');
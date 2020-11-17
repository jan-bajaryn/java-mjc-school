\connect giftcertificates;

INSERT INTO tag(name)
values ('Antoshka'),
       ('Vasia'),
       ('Minsk'),
       ('Vitsebsk'),
       ('Vasniatsova');

INSERT INTO gift_certificate(name, description, price, createDate, lastUpdateDate, duration)
VALUES ('First', 'First certificate', 2.3, '2020-03-13 15:55:52.611265', '2020-03-13 15:55:52.611265', 3),
       ('Second', 'Second certificate', 5.34, '2020-02-23 15:55:52.611265', '2020-03-13 15:55:52.611265', 10);

INSERT INTO gift_certificate_tag(tag_id, gift_certificate_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 2);

INSERT INTO usr(username)
VALUES ('Antuan'),
       ('Valeri'),
       ('Kabal');
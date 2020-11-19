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


INSERT INTO orders(createdate, price, user_id)
VALUES ('2020-03-13 15:55:52.611265', 23.3, 1),
       ('2020-03-13 15:55:52.611265', 21.1, 2),
       ('2020-03-13 15:55:52.611265', 500.32, 1);

INSERT INTO gift_certificate_order(gift_certificate_id, order_id, count, price_for_one, old_name)
VALUES (1, 1, 3, 10, 'Valakadabra'),
       (2, 1, 1, 12.2, 'Vakita'),
       (1, 2, 1, 21.1, 'old name first'),
       (2, 2, 1, 22.1, 'old name'),
       (1, 3, 20, 22.1, 'Valik');


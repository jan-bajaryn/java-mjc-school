INSERT INTO tag(id, name)
values (1, 'Antoshka'),
       (2, 'Vasia'),
       (3, 'Minsk'),
       (4, 'Vitsebsk'),
       (5, 'Vasniatsova');

INSERT INTO gift_certificate(id, name, description, price, createDate, lastUpdateDate, duration)
VALUES (1, 'First', 'First certificate', 2.3, '2020-09-11T14:42:21', '2020-09-11T14:42:21', 3),
       (2, 'Second', 'Second certificate', 5.34, '2020-01-12T14:42:11', '2020-01-12T14:42:11', 10);

INSERT INTO gift_certificate_tag(tag_id, gift_certificate_id)
VALUES (1, 1),
       (2, 1),
       (4, 2),
       (5, 2);


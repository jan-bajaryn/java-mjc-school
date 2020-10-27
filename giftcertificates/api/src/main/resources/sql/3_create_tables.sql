use giftcertificates;

CREATE TABLE tag
(
    id integer not null auto_increment,
    name varchar(100),
    CONSTRAINT pk_tag PRIMARY KEY (id),
    CONSTRAINT uk_tag UNIQUE (name)
);

CREATE TABLE gift_certificate
(
    id             integer not null auto_increment,
    name           varchar(255),
    description    varchar(500),
    price          decimal,
    createDate     datetime,
    lastUpdateDate datetime,
    Duration       int,
    CONSTRAINT pk_gift_certificate PRIMARY KEY (id)
);

CREATE TABLE gift_certificate_tag
(
    tag_id              integer,
    gift_certificate_id integer,
    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id),
    CONSTRAINT fk_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id)
);
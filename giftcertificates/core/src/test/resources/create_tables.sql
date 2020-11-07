CREATE TABLE tag
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    CONSTRAINT pk_tag PRIMARY KEY (id),
    CONSTRAINT uk_tag UNIQUE (name)
);

CREATE TABLE gift_certificate
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    name           varchar(255),
    description    varchar(500),
    price          decimal,
    createDate     timestamp,
    lastUpdateDate timestamp,
    duration       int,
    CONSTRAINT pk_gift_certificate PRIMARY KEY (id),
    CONSTRAINT uk_gift_certificate_name UNIQUE (name)
);

CREATE TABLE gift_certificate_tag
(
    tag_id              BIGINT,
    gift_certificate_id BIGINT,
    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id),
    CONSTRAINT fk_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    CONSTRAINT uk_gift_certificate_tag UNIQUE (tag_id,gift_certificate_id)
)
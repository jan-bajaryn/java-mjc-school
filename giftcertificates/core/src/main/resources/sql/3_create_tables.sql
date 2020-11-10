\c giftcertificates
CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(255),
    CONSTRAINT uk_tag UNIQUE (name)
);

\c giftcertificates
CREATE TABLE gift_certificate
(
    id             BIGSERIAL PRIMARY KEY,
    name           varchar(255),
    description    varchar(500),
    price          decimal,
    createDate     timestamp,
    lastUpdateDate timestamp,
    duration       int,
    CONSTRAINT uk_gift_certificate_name UNIQUE (name)
);

\c giftcertificates
CREATE TABLE gift_certificate_tag
(
    tag_id              bigint,
    gift_certificate_id bigint,
    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE,
    CONSTRAINT fk_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    CONSTRAINT uk_gift_certificate_tag UNIQUE (tag_id,gift_certificate_id)
)
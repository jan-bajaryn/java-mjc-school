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
    createdate     timestamp,
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
    CONSTRAINT uk_gift_certificate_tag UNIQUE (tag_id, gift_certificate_id)
)

\c giftcertificates
CREATE TABLE usr
(
    id       BIGSERIAL PRIMARY KEY,
    username varchar(255) NOT NULL,
    CONSTRAINT uk_usr UNIQUE (username)
)

\c giftcertificates
CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    createdate timestamp NOT NULL,
    price      decimal   NOT NULL,
    user_id    bigint,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES usr (id)
)

\c giftcertificates
CREATE TABLE gift_certificate_order
(
    gift_certificate_id bigint,
    order_id            bigint,
    count               int          NOT NULL,
    price_for_one       decimal      NOT NULL,
    old_name            varchar(255) NOT NULL,
    CONSTRAINT fk_gift_certificate_order_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    CONSTRAINT fk_fk_gift_certificate_order_order_id FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT check_count_gift_certificate_order CHECK ( count > 0 )
)

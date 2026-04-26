CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE house
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255),
    number VARCHAR(255),
    owner  BIGINT NOT NULL
);

CREATE TABLE user
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    house   BIGINT
);

CREATE TABLE transaction
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount           DECIMAL(19, 2) NOT NULL,
    date             DATE           NOT NULL,
    transaction_type VARCHAR(50)    NOT NULL,
    category_id      BIGINT         NOT NULL,
    user_id          BIGINT         NOT NULL
);

ALTER TABLE house
    ADD CONSTRAINT fk_house_owner FOREIGN KEY (owner) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT fk_user_house FOREIGN KEY (house) REFERENCES house (id) ON DELETE SET NULL;

ALTER TABLE transaction
    ADD CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES category (id),
    ADD CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES user (id);
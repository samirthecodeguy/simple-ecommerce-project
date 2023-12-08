-- liquibase formatted sql

-- changeset mrx:1701893939943-1
CREATE SEQUENCE cart_item_seq INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775806 START WITH 1;

-- changeset mrx:1701893939943-2
CREATE SEQUENCE cart_seq INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775806 START WITH 1;

-- changeset mrx:1701893939943-3
CREATE TABLE authority (name VARCHAR(50) NOT NULL, CONSTRAINT PK_AUTHORITY PRIMARY KEY (name));

-- changeset mrx:1701893939943-4
CREATE TABLE cart (id BIGINT NOT NULL, created_by VARCHAR(255) NOT NULL, created_date datetime NOT NULL, last_modified_by VARCHAR(255) NULL, last_modified_date datetime DEFAULT NULL NULL, user_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_CART PRIMARY KEY (id));

-- changeset mrx:1701893939943-5
CREATE TABLE cart_item (id BIGINT NOT NULL, cart_id BIGINT DEFAULT NULL NULL, products_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_CART_ITEM PRIMARY KEY (id));

-- changeset mrx:1701893939943-6
CREATE TABLE category (id BIGINT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT PK_CATEGORY PRIMARY KEY (id));

-- changeset mrx:1701893939943-7
CREATE TABLE customer_order (order_id BIGINT AUTO_INCREMENT NOT NULL, created_by VARCHAR(255) NOT NULL, created_date datetime NOT NULL, last_modified_by VARCHAR(255) NULL, last_modified_date datetime DEFAULT NULL NULL, canceled_date datetime DEFAULT NULL NULL, delivered_date datetime DEFAULT NULL NULL, order_date datetime NOT NULL, shipping_address VARCHAR(255) NULL, status ENUM('PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED') NOT NULL, total_amount DOUBLE NOT NULL, transaction_id VARCHAR(255) NULL, user_id BIGINT NOT NULL, CONSTRAINT PK_CUSTOMER_ORDER PRIMARY KEY (order_id));

-- changeset mrx:1701893939943-8
CREATE TABLE order_item (order_item_id BIGINT AUTO_INCREMENT NOT NULL, quantity INT NOT NULL, subtotal DOUBLE NOT NULL, unit_price DOUBLE NOT NULL, order_id BIGINT NOT NULL, product_id BIGINT NOT NULL, CONSTRAINT PK_ORDER_ITEM PRIMARY KEY (order_item_id));

-- changeset mrx:1701893939943-9
CREATE TABLE product (id BIGINT AUTO_INCREMENT NOT NULL, created_by VARCHAR(255) NOT NULL, created_date datetime NOT NULL, last_modified_by VARCHAR(255) NULL, last_modified_date datetime DEFAULT NULL NULL, barcode VARCHAR(255) NULL, cost_price DECIMAL(38, 2) NOT NULL, `description` VARCHAR(512) NOT NULL, low_stock_threshold INT NOT NULL, name VARCHAR(255) NOT NULL, picture TINYBLOB DEFAULT NULL NULL, price DECIMAL(38, 2) NOT NULL, stock_quantity INT NOT NULL, category BIGINT DEFAULT NULL NULL, CONSTRAINT PK_PRODUCT PRIMARY KEY (id));

-- changeset mrx:1701893939943-10
CREATE TABLE user (id BIGINT AUTO_INCREMENT NOT NULL, created_by VARCHAR(255) NOT NULL, created_date datetime NOT NULL, last_modified_by VARCHAR(255) NULL, last_modified_date datetime DEFAULT NULL NULL, activated BIT NOT NULL, activation_key VARCHAR(20) NULL, email VARCHAR(254) NULL, first_name VARCHAR(50) NULL, image_url VARCHAR(256) NULL, last_name VARCHAR(50) NULL, password_hash VARCHAR(60) NOT NULL, reset_date datetime DEFAULT NULL NULL, reset_key VARCHAR(20) NULL, username VARCHAR(50) NOT NULL, CONSTRAINT PK_USER PRIMARY KEY (id), UNIQUE (email), UNIQUE (username));

-- changeset mrx:1701893939943-11
CREATE TABLE user_authority (user_id BIGINT NOT NULL, authority_name VARCHAR(50) NOT NULL, CONSTRAINT PK_USER_AUTHORITY PRIMARY KEY (user_id, authority_name));

-- changeset mrx:1701893939943-12
CREATE INDEX FK1uobyhgl1wvgt1jpccia8xxs3 ON cart_item(cart_id);

-- changeset mrx:1701893939943-13
CREATE INDEX FK551losx9j75ss5d6bfsqvijna ON order_item(product_id);

-- changeset mrx:1701893939943-14
CREATE INDEX FK6ktglpl5mjosa283rvken2py5 ON user_authority(authority_name);

-- changeset mrx:1701893939943-15
CREATE INDEX FKf0hyuyamjo7t2121k1hw1psy0 ON customer_order(user_id);

-- changeset mrx:1701893939943-16
CREATE INDEX FKgv4bnmo7cbib2nh0b2rw9yvir ON order_item(order_id);

-- changeset mrx:1701893939943-17
CREATE INDEX FKh83gl2v314y0jlgvjibrmhfp2 ON cart_item(products_id);

-- changeset mrx:1701893939943-18
CREATE INDEX FKl70asp4l4w0jmbm1tqyofho4o ON cart(user_id);

-- changeset mrx:1701893939943-19
CREATE INDEX FKqx9wikktsev17ctu0kcpkrafc ON product(category);

-- changeset mrx:1701893939943-20
ALTER TABLE cart_item ADD CONSTRAINT FK1uobyhgl1wvgt1jpccia8xxs3 FOREIGN KEY (cart_id) REFERENCES cart (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-21
ALTER TABLE order_item ADD CONSTRAINT FK551losx9j75ss5d6bfsqvijna FOREIGN KEY (product_id) REFERENCES product (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-22
ALTER TABLE user_authority ADD CONSTRAINT FK6ktglpl5mjosa283rvken2py5 FOREIGN KEY (authority_name) REFERENCES authority (name) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-23
ALTER TABLE customer_order ADD CONSTRAINT FKf0hyuyamjo7t2121k1hw1psy0 FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-24
ALTER TABLE order_item ADD CONSTRAINT FKgv4bnmo7cbib2nh0b2rw9yvir FOREIGN KEY (order_id) REFERENCES customer_order (order_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-25
ALTER TABLE cart_item ADD CONSTRAINT FKh83gl2v314y0jlgvjibrmhfp2 FOREIGN KEY (products_id) REFERENCES product (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-26
ALTER TABLE cart ADD CONSTRAINT FKl70asp4l4w0jmbm1tqyofho4o FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-27
ALTER TABLE user_authority ADD CONSTRAINT FKpqlsjpkybgos9w2svcri7j8xy FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset mrx:1701893939943-28
ALTER TABLE product ADD CONSTRAINT FKqx9wikktsev17ctu0kcpkrafc FOREIGN KEY (category) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE RESTRICT;


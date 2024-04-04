CREATE TABLE product (
    product_id BIGINT,
    name VARCHAR(256),
    vendor VARCHAR(256),
    description TEXT,
    PRIMARY KEY(product_id)
);

CREATE TABLE stock (
    stock_item_id BIGINT,
    selling_price DECIMAL(32, 2),
    product_id BIGINT,
    PRIMARY KEY (stock_item_id),
    FOREIGN KEY (product_id) REFERENCES product
);
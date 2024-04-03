CREATE TABLE product (
    product_id BIGINT ,
    name VARCHAR(128),
    vendor VARCHAR(128),
    description TEXT,
    PRIMARY KEY(productId)
);

CREATE TABLE stock (
    stock_item_id BIGINT,
    selling_price DECIMAL(32, 2)
    product_id BIGINT,
    PRIMARY KEY(stock_item_id),
    FOREIGN KEY(product_id) REFERENCES product(product_id)
);
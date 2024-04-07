CREATE TABLE remove_from_stock_outbox (
    operation_id CHAR(36),
    stock_item_id BIGINT NOT NULL,
    quantity BIGINT,

    PRIMARY KEY (operation_id)
);
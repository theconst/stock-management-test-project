CREATE TABLE stock_operation (
    operation_id CHAR(36),
    processed TIMESTAMP NOT NULL,
    PRIMARY KEY (operation_id),
);
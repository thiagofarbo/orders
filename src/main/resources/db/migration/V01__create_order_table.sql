-- Sequência para a tabela 'orders'
CREATE SEQUENCE IF NOT EXISTS sq_orders
  INCREMENT 1
  START 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;

-- Tabela 'orders'
CREATE TABLE orders (
    id BIGINT NOT NULL DEFAULT NEXTVAL('sq_orders'),
    external_id VARCHAR(255) NOT NULL UNIQUE,
    total_value NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- Sequência para a tabela 'order_items'
CREATE SEQUENCE IF NOT EXISTS sq_order_items
  INCREMENT 1
  START 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;

-- Tabela 'order_items'
CREATE TABLE order_items (
    id BIGINT NOT NULL DEFAULT NEXTVAL('sq_order_items'),
    order_id BIGINT NOT NULL, -- FK para 'orders'
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

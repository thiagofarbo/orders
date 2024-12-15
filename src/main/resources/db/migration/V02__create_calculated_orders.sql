-- Sequência para a tabela 'orders'
CREATE SEQUENCE IF NOT EXISTS sq_calculated_orders
  INCREMENT 1
  START 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;

CREATE TABLE calculated_orders (
    id BIGINT NOT NULL DEFAULT NEXTVAL('sq_calculated_orders'),
    external_id VARCHAR(255) NOT NULL UNIQUE,
    order_id BIGINT NOT NULL,
    total_value NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE itens_pedidos (
  id SERIAL PRIMARY KEY,
  pedido_id UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
  produto TEXT NOT NULL,
  quantidade INTEGER NOT NULL,
  preco_unitario NUMERIC(15,2) NOT NULL
);
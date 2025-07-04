CREATE TABLE pedidos (
  id UUID PRIMARY KEY,
  data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
  cliente_id INTEGER NOT NULL REFERENCES clientes(id),
  endereco_entrega_id INTEGER NOT NULL REFERENCES enderecos(id),
  valor_total NUMERIC(15,2) NOT NULL,
  status TEXT NOT NULL DEFAULT 'CRIADO' CHECK (status IN ('CRIADO', 'PROCESSANDO', 'ENVIADO', 'ENTREGUE', 'CANCELADO'))
);

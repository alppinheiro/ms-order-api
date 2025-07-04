

CREATE TABLE enderecos (
  id SERIAL PRIMARY KEY,
  rua TEXT NOT NULL,
  numero TEXT NOT NULL,
  complemento TEXT,
  bairro TEXT NOT NULL,
  cidade TEXT NOT NULL,
  estado TEXT NOT NULL,
  cep VARCHAR(8) NOT NULL
);
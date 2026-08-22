CREATE TABLE transactions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(id),
  type VARCHAR(20) NOT NULL,
  status VARCHAR(20) NOT NULL,
  currency VARCHAR(3) NOT NULL REFERENCES currencies(code),
  idempotency_key VARCHAR(255) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT transactions_user_idempotency_key UNIQUE (user_id, idempotency_key),
  CONSTRAINT transactions_id_currency_key UNIQUE (id, currency),
  CONSTRAINT transaction_type_valid
    CHECK (type IN ('DEPOSIT', 'WITHDRAW', 'TRANSFER')),
  CONSTRAINT transaction_status_valid
    CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED'))
);

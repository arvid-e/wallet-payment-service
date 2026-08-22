CREATE TABLE accounts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id),
  account_type VARCHAR(20) NOT NULL,
  code VARCHAR(50),
  currency VARCHAR(3) NOT NULL REFERENCES currencies(code),
  balance BIGINT NOT NULL DEFAULT 0,
  version BIGINT NOT NULL DEFAULT 0,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT accounts_user_currency_key UNIQUE (user_id, currency),
  CONSTRAINT accounts_code_currency_key UNIQUE (code, currency),
  CONSTRAINT accounts_id_currency_key UNIQUE (id, currency),
  CONSTRAINT account_type_valid
    CHECK (account_type IN ('USER', 'SYSTEM')),
  CONSTRAINT account_owner_valid
    CHECK (
      (account_type = 'USER' AND user_id IS NOT NULL AND code IS NULL) OR
      (account_type = 'SYSTEM' AND user_id IS NULL AND code IS NOT NULL)
    ),
  CONSTRAINT account_balance_valid
    CHECK (account_type = 'SYSTEM' OR balance >= 0)
);

INSERT INTO accounts (id, account_type, code, currency)
VALUES ('00000000-0000-0000-0000-000000000001', 'SYSTEM', 'EXTERNAL', 'JPY');

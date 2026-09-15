CREATE TABLE ledger_entries (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  transaction_id UUID NOT NULL,
  account_id UUID NOT NULL,
  currency VARCHAR(3) NOT NULL,
  amount BIGINT NOT NULL,
  entry_type VARCHAR(10) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ledger_entries_transaction_currency_fkey
    FOREIGN KEY (transaction_id, currency) REFERENCES transactions (id, currency),
  CONSTRAINT ledger_entries_account_currency_fkey
    FOREIGN KEY (account_id, currency) REFERENCES accounts (id, currency),
  CONSTRAINT entry_type_valid
    CHECK (entry_type IN ('DEBIT', 'CREDIT')),
  CONSTRAINT amount_valid
    CHECK (amount > 0)
);

CREATE INDEX idx_ledger_entries_transaction_id ON ledger_entries (transaction_id);
CREATE INDEX idx_ledger_entries_account_id ON ledger_entries (account_id);

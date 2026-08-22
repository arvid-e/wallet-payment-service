CREATE TABLE ledger_entries (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  transaction_id UUID NOT NULL REFERENCES transactions(id),
  account_id UUID NOT NULL REFERENCES accounts(id),
  amount BIGINT NOT NULL,
  entry_type VARCHAR(10) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT entry_type_valid
    CHECK (entry_type IN ('DEBIT', 'CREDIT')),
  CONSTRAINT amount_valid
    CHECK (amount > 0)
);

CREATE INDEX idx_ledger_entries_transaction_id ON ledger_entries (transaction_id);
CREATE INDEX idx_ledger_entries_account_id ON ledger_entries (account_id);

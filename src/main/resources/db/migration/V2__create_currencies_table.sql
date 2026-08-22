CREATE TABLE currencies (
  code VARCHAR(3) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  exponent SMALLINT NOT NULL,
  CONSTRAINT currency_code_format
    CHECK (code ~ '^[A-Z]{3}$'),
  CONSTRAINT currency_exponent_valid
    CHECK (exponent BETWEEN 0 AND 4)
);

INSERT INTO currencies (code, name, exponent)
VALUES ('JPY', 'Japanese Yen', 0);

CREATE TABLE account (
  number VARCHAR(10) NOT NULL PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  suid VARCHAR(11) NOT NULL,
  CONSTRAINT number unique(number)
);

CREATE TABLE transfer (
  transaction_id VARCHAR(50) NOT NULL PRIMARY KEY,
  instant TIMESTAMP NOT NULL,
  source_account_number VARCHAR(10) NOT NULL,
  destination_account_number VARCHAR(10) NOT NULL,
  amount NUMBER NOT NULL,
  CONSTRAINT FK_source_account_number FOREIGN KEY(source_account_number) REFERENCES account(number),
  CONSTRAINT FK_destination_account_number FOREIGN KEY(destination_account_number) REFERENCES account(number)
);

CREATE TABLE deposit (
  transaction_id VARCHAR(50) NOT NULL PRIMARY KEY,
  instant TIMESTAMP NOT NULL,
  account_number VARCHAR(10) NOT NULL,
  amount NUMBER NOT NULL,
  CONSTRAINT FK_account_number FOREIGN KEY(account_number) REFERENCES account(number),
);
CREATE TABLE IF NOT EXISTS account (
  id            UUID PRIMARY KEY,
  balance       DECIMAL(18,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS stored_domain_event (
  id     UUID PRIMARY KEY,
  content   VARCHAR2(4096)    NOT NULL,
  sent   BOOLEAN    NOT NULL,
  event_timestamp   DATETIME   NOT NULL,
  event_type VARCHAR2(128) NOT NULL
);
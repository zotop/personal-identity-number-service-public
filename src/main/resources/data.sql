DROP TABLE IF EXISTS personal_id;

CREATE TABLE IF NOT EXISTS personal_id (
  id VARCHAR(13) NOT NULL PRIMARY KEY,
  valid BOOLEAN
);
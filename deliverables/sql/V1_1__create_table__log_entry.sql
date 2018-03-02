CREATE TABLE IF NOT EXISTS log_entry (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  date       TIMESTAMP    NOT NULL,
  ipv4       VARCHAR(16)  NOT NULL,
  request    VARCHAR(21)  NOT NULL,
  status     INT          NOT NULL,
  user_agent VARCHAR(256) NOT NULL
)
CREATE TABLE IF NOT EXISTS log_entry (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  date       TIMESTAMP    NOT NULL,
  ipv4       VARCHAR(15)  NOT NULL,
  request    VARCHAR(20)  NOT NULL,
  status     INT          NOT NULL,
  user_agent VARCHAR(256) NOT NULL
)
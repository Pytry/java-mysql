CREATE TABLE IF NOT EXISTS blocked_ipv4 (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  ipv4   VARCHAR(16)  NOT NULL,
  reason VARCHAR(255) NOT NULL
)

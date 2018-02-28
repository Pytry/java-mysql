CREATE DATABASE IF NOT EXISTS logwatch_db;

CREATE USER IF NOT EXISTS  'logwatch_user'@'localhost'
  IDENTIFIED BY 'ChangeThisInYourSettingsXmlAndApplicationProperties';

GRANT ALL ON logwatch_db.* TO 'logwatch_user'@'localhost';
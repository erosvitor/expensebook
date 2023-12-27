CREATE DATABASE expensebook CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;

USE expensebook;

CREATE TABLE expenses (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  value DECIMAL(11,2) NOT NULL,
  paid_at DATE NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE utf8mb3_general_ci;
